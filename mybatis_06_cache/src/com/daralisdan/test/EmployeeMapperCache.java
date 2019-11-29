package com.daralisdan.test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import com.daralisdan.bean.Department;
import com.daralisdan.bean.Employee;
import com.daralisdan.dao.EmployeeMapper;
import com.daralisdan.dao.EmployeeMapperDynamicSql;

public class EmployeeMapperCache {


  // 提取公共的方法
  public SqlSessionFactory getSqlSessionFactory() throws IOException {
    // 获取配置文件的位置信息
    String resource = "mybatis-conf.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    // 创建SQLSessionFactory
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    return sqlSessionFactory;
  }

  /**
   * 
      * 两级缓存
      *  一级缓存（本地缓存）：SQLSession级别缓存，以及缓存是一直开启的（新的SQLSession就是新的一级缓存，两个缓存之间不共用），
      *  可以看成是sqlsession级别的一个map：当前sqlsession查出的数据都放在map中，下次查询时，还是当前会话，就先看map中有没有相同数据，有就直接拿，没有就从数据库中拿
      *  与数据库同一次会话期间查询到的数据放在本地缓存中
      *                     以后如果需要获取相同的数据，直接从缓存中拿，没必要再去查询数据库
   * 
   * ==============================================================================================
      * 二级缓存（全局缓存）：基于namespace级别的缓存，一个namespace对应一个二级缓存
      *                       工作机制：
   *                1.一个会话，查询一条数据，这个会话就会被放在当前会话的一级缓存中
   *                2.如果会话关闭，一级缓存中的数据会被保存到二级缓存，新的会话查询信息就可以参照二级缓存
   *                3.sqlsession=======employeeMapper=========》Employee
   *                                    DepartmentMapper=======》Department
      *                   不同的namespace查出的数据会放在自己对应的缓存中（map）
   *                       
      *     使用步骤：
   *        1)：开启全局二级缓存配置， 在全局配置文件中 <setting name="cacheEnabled" value="true" />
   *        2):在Employeemapper.xml中配置使用二级缓存 只需要添加该标签，其他设置为默认 <cache></cache>
   *        3):pojo(javaBean)需要实现序列化接口          
   *                       
      *     效果：       数据会从二级缓存中获取，
      *     
      *  注意：二级缓存的使用，必须关闭第一个sqlsession会话之后(查出的数据默认在一级缓存中，只有会话关闭或者提交以后才会转移到二级缓存中)
    * ================================================================================================                      
   *
   * 与缓存有关的设置与属性：
   *    1.在全局配置中的属性，在setting标签中的属性：cacheEnabled=true开启缓存，false：关闭缓存（首先关闭二级缓存，一级缓存一直开启的，可用的）
   *    2.在映射文件配置中，每个select标签中都有的属性：useCache="true"；false：不使用的缓存（一级缓存一直使用，二级缓存不使用）
   *    3. 每个增删改标签：flushCache="true"：清空缓存，增删改执行完之后就会清楚缓存，
   *    增删改，默认清楚缓存（一级缓存，二级缓存都会被清空），查询标签中默认是false，不清除缓存
   *    4. sqlsession.clearCache:清除一级缓存，只是清除当前session的一级缓存,二级缓存不影响
   *    5. 5.localCacheScope:本地缓存作用域（一级缓存session），当前会话的所有数据保存在会话缓存中 statement：可以禁用一级缓存
   * @throws IOException 
   */
  @Test
  public void testFirstLevelCache() throws IOException {
    SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
    SqlSession openSession = sqlSessionFactory.openSession();
    try {
      EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
      Employee emp01 = mapper.getEmpId(1);
      System.out.println(emp01);
      /*
       * // 再次使用查询相同数据时,两次请求，只发送了一次sql语句， 第二次获取的是与第一次相同的数据，就不需要在数据库拿取数据，直接在本地缓存中获取
       * 
       */
      Employee emp02 = mapper.getEmpId(1);
      System.out.println(emp02);
      // 两次拿取的数据是相同的
      System.out.println(emp01 == emp02);

    } finally {
      openSession.close();
    }
  }

  /**
   * 
   * Title：testFirstLevelCache02 <br>
   * 一级缓存失效的情况（没有使用到一级缓存的情况，效果就是还需要再向数据库发出请求）：
   *  可以看成是sqlsession级别的一个map：当前sqlsession查出的数据都放在map中，下次查询时，还是当前会话，就先看map中有没有相同数据，有就直接拿，没有就从数据库中拿
   * 
   * 1.SQLSession不同
   * 2.sqlsession相同时，查询条件不同时 （当前一级缓存中还没有这个数据）
   * 3.sqlsession相同时，查询期间执行了增删改查（两次查询期间夹杂了数据添加，在增删改的过程中，可能会改变当前数据，所有需要在从数据库重新查询）
   * 4.sqlsession相同时，手动清除了以及缓存（缓存清空）
   * 
   * author：yaodan  <br>
   * date：2019年9月22日 下午4:32:32 <br>
   * @throws IOException <br>
   */

  @Test
  public void testFirstLevelCache02() throws IOException {
    SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
    SqlSession openSession = sqlSessionFactory.openSession();
    try {
      // 同一SQLSession的以及缓存情况（有效状态）
      // EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
      // Employee emp01 = mapper.getEmpId(1);
      // System.out.println(emp01);
      // /*
      // * // 再次使用查询相同数据时,两次请求，只发送了一次sql语句， 第二次获取的是与第一次相同的数据，就不需要在数据库拿取数据，直接在本地缓存中获取
      // *
      // */
      // Employee emp02 = mapper.getEmpId(1);
      // System.out.println(emp02);
      // // 两次拿取的数据是相同的
      // System.out.println(emp01 == emp02);
      // ===============================================================================================

      // 1.一级缓存失效的情况：不同SQLSession的情况
      // EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
      // Employee emp01 = mapper.getEmpId(1);
      // System.out.println(emp01);
      //
      // SqlSession openSession02 = sqlSessionFactory.openSession();
      // EmployeeMapper mapper02 = openSession02.getMapper(EmployeeMapper.class);
      // Employee emp02 = mapper02.getEmpId(1);
      // System.out.println(emp02);
      // // 不同SQLSession的情况，一级缓存失效，两次拿取的数据都是从数据库拿取
      // System.out.println(emp01 == emp02);
      // openSession02.close();

      // ===============================================================================================

      // 2.sqlsession相同时，查询条件不同时
      // EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
      // Employee emp01 = mapper.getEmpId(1);
      // System.out.println(emp01);
      //
      // Employee emp02 = mapper.getEmpId(3);
      // System.out.println(emp02);
      // // 不同SQLSession的情况，一级缓存失效，查询条件不同，SQLSession中没有相同的数据，所有需要向数据库再次发送请求，所有数据不同
      // System.out.println(emp01 == emp02);

      // ===============================================================================================
      //
      // // 3.sqlsession相同时，查询期间执行了增删改查
      // EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
      // Employee emp01 = mapper.getEmpId(1);
      // System.out.println(emp01);
      //
      // // 在两次查询期间执行增删改查
      // mapper.addEmp(new Employee(null, "testCache", "1", "cache@163"));
      // System.out.println("数据添加成功");
      // Employee emp02 = mapper.getEmpId(1);
      // System.out.println(emp02);
      // // 不同SQLSession的情况，一级缓存失效，两次查询期间夹杂了数据添加，在增删改的过程中，可能会改变当前数据，所有需要在从数据库重新查询
      // System.out.println(emp01 == emp02);

      // ===============================================================================================

      // 4.sqlsession相同时，手动清除了以及缓存（缓存清空）
      EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
      Employee emp01 = mapper.getEmpId(1);
      System.out.println(emp01);
      // 清空一级缓存
      openSession.clearCache();

      Employee emp02 = mapper.getEmpId(1);
      System.out.println(emp02);
      // 不同SQLSession的情况，一级缓存失效，清空缓存之后sqlsession中没有相同数据
      System.out.println(emp01 == emp02);
    } finally {
      openSession.close();
    }
  }

  // ============================================================================================================
  /**
   * 
   * Title：testSecondLevelCache <br>
  * 二级缓存
   * author：yaodan  <br>
   * date：2019年9月22日 下午7:39:24 <br> <br>
   * @throws IOException 
   */
  @Test
  public void testSecondLevelCache() throws IOException {
    SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
    // 两个sqlsession，两次会话
    SqlSession openSession01 = sqlSessionFactory.openSession();
    SqlSession openSession02 = sqlSessionFactory.openSession();
    try {
      EmployeeMapper mapper01 = openSession01.getMapper(EmployeeMapper.class);
      EmployeeMapper mapper02 = openSession02.getMapper(EmployeeMapper.class);
      Employee emp01 = mapper01.getEmpId(1);
      System.out.println(emp01);
      openSession01.close();

      // 第二次查询是从二级缓存中拿到的数据，并没有发送新的sql(虽然一级缓存关了，查出的数据会在对应EmployeeMapper的二级缓存中)
      Employee emp02 = mapper02.getEmpId(1);
      System.out.println(emp02);
      openSession02.close();
    } finally {
    }
  }

}
