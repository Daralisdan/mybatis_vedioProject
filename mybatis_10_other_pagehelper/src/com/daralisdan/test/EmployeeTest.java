package com.daralisdan.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import com.daralisdan.bean.Employee;
import com.daralisdan.bean.EmployeeExample;
import com.daralisdan.bean.EmployeeExample.Criteria;
import com.daralisdan.dao.EmployeeMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
// import com.daralisdan.bean.Employee;
// import com.daralisdan.dao.EmployeeMapper;

public class EmployeeTest {

  public SqlSessionFactory getSqlSessionFactory() throws IOException {
    // mybatis全局配置文件的位置
    String resource = "mybatis-conf.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    // 1.创建SqlSessionFactory对象
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    return sqlSessionFactory;
  }

  @Test
  public void test() throws Exception {
    List<String> warnings = new ArrayList<String>();
    boolean overwrite = true;
    // 配置文件的位置
    File configFile = new File("mbg.xml");
    ConfigurationParser cp = new ConfigurationParser(warnings);
    Configuration config = cp.parseConfiguration(configFile);
    DefaultShellCallback callback = new DefaultShellCallback(overwrite);
    MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
    myBatisGenerator.generate(null);
  }

  /**
  * 1.获取sqlSessionFactory对象：
  *   解析文件的每一个信息保存在configuration中，返回包含Configuration的defaultSQLSession
  *    注意：【MappedStatement】,代表一个增删改查的详细信息
  *    
  *2.获取SQLSession对象
  *     返回一个defaultSQLSession对象，包含Executor和Configuration，这一步会创建Executor对象
  *     
  *3.获取接口的代理对象，(MapperProxy)
  *     getMapper,使用MapperProxyFactory创建一个MapperProxy的代理对象
  *     代理对象里面包含了，defaultSQLSession（Executor）
  *     
  *4.执行增删改查方法
  *
  *总结：
  * 1.根据配置文件（全局，sql映射）初始化出Configuration对象，
  * 2.创建一个defaultSQLSession对象，它里面包含Configuration 以及Executor(根据全局配置文件中的defaultExecutorType 创建出对应的Executor)
  * 3.defaultSQLSession.getMapper中拿到mapper接口对应的动态代理对象（MapperProxy）
  * 4.MapperProxy里面有（defaultSQLSession）；
  * 5.执行增删改查方法：
      *  1）：调用defaultSQLSession的增删改查（Executor）
      *  2）：会创建一个StatementHandler对象。（同时也会创建出一个ParameterHandler
      *  和ResultSetHandler）
      *  3）：调用StatementHandler预编译参数以及设置参数值；
      *       使用ParameterHandler给sql设置参数
      *  4）：设置好之后在调用StatementHandler的增删改查方法
      *  5）：最后使用ResultSetHandler封装结果
  * 注意：
  * 四大对象每个创建的时候都有一个interceptorChain.pluginAll(ParameterHandler)
  * 
  * Title：testMybatis3 <br>
  * Description：TODO(这里用一句话描述这个方法的作用) <br>
  * author：yaodan  <br>
  * date：2019年10月24日 下午11:59:17 <br>
  * @throws IOException <br>
  */



  // mybatis3Simple 简单版的crud测试
  // @Test
  // public void TestSimple() throws Exception {
  // // 1.获取sqlsession
  // SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
  // // 2.获取sqlsession对象
  // SqlSession openSession = sqlSessionFactory.openSession();
  // try {
  // // 获取mapper
  // EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
  // List<Employee> list = mapper.selectAll();
  // for (Employee employee : list) {
  // System.out.println(employee.getId());
  // }
  // } finally {
  // openSession.close();
  // }
  // }
  // mybatis3 豪华版的crud测试 ctrl+shift+o 自动导包
  @Test
  public void testMybatis3() throws IOException {
    // // 1.获取sqlsession
    SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
    // 2.获取sqlsession对象
    SqlSession openSession = sqlSessionFactory.openSession();
    try {
      // 获取mapper
      EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
      // xxxExample就是封装查询条件的
      // 3.查询所有
      // List<Employee> emps = mapper.selectByExample(null);
      // for (Employee employee : emps) {
      // System.out.println(employee.getId());
      // }

      // 3.2查询员工名字带有k字母的，和员工性别带有m的
      // 封装员工查询条件的example
      EmployeeExample example = new EmployeeExample();
      // ====================================================================
      // 创建一个Critical，这个Critical就是拼装查询条件的
      Criteria criteria = example.createCriteria();
      // and 条件的拼装
      criteria.andLastNameLike("%k%");
      criteria.andGenderEqualTo("m");
      // =============================================================
      // 创建一个Critical，这个Critical就是拼装查询条件的
      Criteria criteria2 = example.createCriteria();
      // or条件的拼装
      criteria.andLastNameLike("%k%");
      example.or(criteria2);

      List<Employee> list = mapper.selectByExample(null);
      for (Employee employee : list) {
        System.out.println(employee.getId());
      }

    } finally {
      openSession.close();
    }
  }

  /**
   * 
   * Title：pluginTest <br>
   * Description：
   * 插件原理：
   * 在四大对象创建的时候
   * 1.每个创建出来的对象不是直接返回的，而是 interceptorChain.pluginAll(parameterHandler);
   * 2.获取到所有的interceptor(拦截器)，（组件需要实现的接口）
     *  调用interceptor.plugin(target);返回target包装后的对象
   * 3.插件机制，我们可以使用插件为为目标对象创建一个代理对象，AOP（面向切面）
     * 我们的插件可以为四大对象创建出代理对象
     * 代理对象就可以拦截四大对象的每一个执行
   * 
   * 
   * 
   * author：yaodan  <br>
   * date：2019年10月25日 下午8:34:50 <br> <br>
   */


  /**
   * 分页插件的测试
   * Title：pluginTest <br>
   * Description：TODO(这里用一句话描述这个方法的作用) <br>
   * author：yaodan  <br>
   * date：2019年10月27日 下午6:42:43 <br> <br>
   * @throws IOException 
   */
  @Test
  public void pluginTest() throws IOException {
    // // 1.获取sqlsession
    SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
    // 2.获取sqlsession对象
    SqlSession openSession = sqlSessionFactory.openSession();
    try {
      // 获取mapper
      EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
      // 调用分页插件的第二种方法，Mapper接口方式的调用，推荐这种使用方式。
      // startPage(2, 5):参数一表示当前是第几页。参数二表示当前页显示几条数据
      Page<Object> page = PageHelper.startPage(2, 5);


      List<Employee> emps = mapper.getEmps();

      // //用PageInfo对结果进行包装， 可以使用pageInfo 也可以使用PageHelper ，效果是一样的

//      // 还可以传两个参数，第一个参数传封装的结果，第二个参数时导航页
//      PageInfo<Employee> pageInfo = new PageInfo<>(emps, 5);

      // PageInfo page = new PageInfo(list);

      // 调用mapper的方法
      for (Employee employee : emps) {
        System.out.println(employee);
      }
      System.out.println("当前页码：" + page.getPageNum());
      System.out.println("总记录数：" + page.getTotal());
      System.out.println("每页的记录数：" + page.getPageSize());
      System.out.println("总页码：" + page.getPages());
      
      // System.out.println("连续显示的页码：");
      // int[] nums = pageInfo.getNavigatepageNums();
      // for (int i = 0; i < nums.length; i++) {
      // System.out.println("连续显示的页码："+nums[i]);
      // }
      
    } finally {
      openSession.close();
    }


  }
}
