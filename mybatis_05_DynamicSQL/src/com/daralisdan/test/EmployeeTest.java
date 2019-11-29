package com.daralisdan.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import com.daralisdan.bean.Employee;
import com.daralisdan.dao.EmployeeMapper;
import com.daralisdan.dao.EmployeeMapperAnnotation;

public class EmployeeTest {

  public SqlSessionFactory getSqlSessionFactory() throws IOException {
    // mybatis全局配置文件的位置
    String resource = "mybatis-conf.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    // 1.创建SqlSessionFactory对象
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    return sqlSessionFactory;
  }



  /**
   * 
   * Title：test <br>
   * 1.根据xml配置文件创建 SqlSessionFactory对象<br>
   * xml中有数据源运行环境的配置信息
   * 2.sql映射文件中配置了每一个sql,以及sql的封装规则
   * 3.sql映射文件注册在全局配置文件中
   * 4.写代码：1）：根据配置文件得到SqlSessionFactory
   *           2）：使用SqlSessionFactory工厂：获取到SqlSession对象，然后使用该对象来进行增删改查
   *          3):一个SQLSession就代表一次与数据库会话，用完要关闭
   *          4）：使用sql唯一标识告诉mybatis执行哪一个sql，sql都是保存在sql映射文件中
   * author：yaodan  <br>
   * date：2019年9月14日 下午11:09:36 <br>
   * @throws IOException <br>
   */
  @Test
  public void test() throws IOException {
    SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
    // 2.获取SQLSession实例，能直接执行已经映射的sql语句
    SqlSession openSession = sqlSessionFactory.openSession();
    /*
     * selectOne的参数： sql的唯一标识：statement Unique identifier matching the statement to use. parameter A
     * 执行sql需要用的参数：parameter object to pass to the statement.
     **/
    try {
      Employee employee = openSession.selectOne("com.daralisdan.EmployeeMapper.selectById", 1);
      System.out.println(employee);
    } finally {
      openSession.close();
    }
  }

  /**
   * 
   * Title：Test02 <br>
   * 映射文件版
   * author：yaodan  <br>
   * date：2019年9月15日 下午7:23:54 <br>
   * @throws IOException <br>
   */
  @Test
  public void Test02() throws IOException {
    // 1.获取sqlSessionFactory对象
    SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
    // 2.获取sqlsession对象
    SqlSession openSession = sqlSessionFactory.openSession();
    try {
      // 获取接口的实现类对象
      // 会为接口自动创建一个代理对象，代理对象去执行增删改查方法
      EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
      Employee empId = mapper.getEmpId(1);
      System.out.println(empId);
      // 接口类型
      System.out.println(mapper.getClass());
    } finally {
      openSession.close();
    }
  }

  /**
   * 
   * Title：test03 <br>
   * 没有sql映射文件，注解版本
   * author：yaodan  <br>
   * date：2019年9月15日 下午7:24:51 <br> <br>
   * @throws IOException 
   */
  @Test
  public void test03() throws IOException {
    // 1.获取SQLSessionfactory对象
    SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
    // 2.获取SQLSession对象
    SqlSession openSession = sqlSessionFactory.openSession();
    try {
      // 获取接口的实现类对象（绑定接口）
      EmployeeMapperAnnotation mapper = openSession.getMapper(EmployeeMapperAnnotation.class);
      Employee empId = mapper.getEmpId(1);
      System.out.println(empId);
    } finally {
      openSession.close();
    }

  }

  /**
   * 
   * Title：test04 <br>
      * 测试增删改<br>
      *1.返回值 mybatis允许增删改直接定义以下类型返回值：Integer,Long,Boolean，void
   * 2.手动提交数据
   *   sqlSessionFactory.openSession();  ===>需要手动提交
   *   sqlSessionFactory.openSession(true); ====>自动提交
   * author：yaodan  <br>
   * date：2019年9月15日 下午8:12:23 <br> <br>
   * @throws IOException 
   */
  @Test
  public void test04() throws IOException {
    SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
    // 2.获取到SqlSession不会自动提交数据
    SqlSession openSession = sqlSessionFactory.openSession();
    try {
      EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
      // 增加
      Employee employee = new Employee(null, "jerry", "1", "jerry@163");
      mapper.addEmp(employee);
      System.out.println(employee.getId());

      // 修改 void
      // Employee employee = new Employee(1, "jerry", "1", "jerry@163");
      // mapper.updateEmp(employee);

      // 修改 boolean
      // Employee employee = new Employee(1, "tom", "0", "tom@163");
      // boolean updateEmp = mapper.updateEmp(employee);
      // System.out.println(updateEmp);

      // 删除
      // mapper.deleteEmpById(2);

      // 3.手动提交数据
      openSession.commit();
    } finally {
      openSession.close();
    }

  }



  /**
  * 
  * Title：test05 <br>
    * 传入多个参数时查询 <br>
  * author：yaodan  <br>
  * date：2019年9月15日 下午9:15:39 <br>
  * @throws IOException <br>
  */

  @Test
  public void test05() throws IOException {
    SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
    // 2.获取到SqlSession不会自动提交数据
    SqlSession openSession = sqlSessionFactory.openSession();
    try {
      EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
      // Employee empIdAndLastName = mapper.getEmpIdAndLastName(1, "tom");
      // System.out.println(empIdAndLastName);

      // 多个参数不是业务模型中的数据,可以传入map
      // Map<String,Object> map = new HashMap<>();
      // map.put("id",1);
      // map.put("lastName", "tom");
      // Employee empByMap = mapper.getEmpByMap(map);
      // System.out.println(empByMap);

      // 返回的是集合类型 查询
      // List<Employee> like = mapper.getEmpByLastNameLike("%e%");
      // for (Employee employee : like) {
      // System.out.println(employee);
      // }

      // 返回一条记录的map,key就是列名，值就是对应的值
      // Map<String, Object> map = mapper.getEmpByIdReturnMap(1);
      // System.out.println(map);

      // 多条记录封装一个map, Map<String, Employee> 键是这条记录的主键，值是封装后的javaBean

      Map<String, Employee> map = mapper.getEmpByLastNameLikeReturnMap("%e%");
      System.out.println(map);



    } finally {
      openSession.close();
    }

  }



}
