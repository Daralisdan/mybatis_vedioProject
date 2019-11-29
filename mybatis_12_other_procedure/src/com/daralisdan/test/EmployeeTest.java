package com.daralisdan.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.UUID;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import com.daralisdan.bean.Employee;
import com.daralisdan.dao.EmployeeMapper;

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

      // 3.手动提交数据
      openSession.commit();
    } finally {
      openSession.close();
    }

  }

  /**
   * 
   * Title：batchAddEmp <br>
   * Description：批量保存操作
   * author：yaodan  <br>
   * date：2019年11月14日 下午11:45:03 <br>
   * @throws IOException <br>
   */
  @Test
  public void batchAddEmp() throws IOException {
    SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
    // 可以执行批量操作的sqlSession
    SqlSession openSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
    // 获取开始的时间
    long start = System.currentTimeMillis();
    try {
      EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
      for (int i = 0; i < 10; i++) {
        // UUID.randomUUID().toString().substring(0, 5) 表示五个字符长度的名字
        mapper.batchAddEmp(
            new Employee(UUID.randomUUID().toString().substring(0, 5), "F", "Tom@qq.com"));
      }
      openSession.commit();
      // 获取结束的时间
      long end = System.currentTimeMillis();
      // 时长525
      System.out.println("执行的时长：" + (end - start));
    } finally {
      openSession.close();
    }
  }

}
