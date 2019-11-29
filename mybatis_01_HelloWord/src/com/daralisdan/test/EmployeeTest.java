package com.daralisdan.test;

import java.io.IOException;
import java.io.InputStream;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import com.daralisdan.bean.Employee;

public class EmployeeTest {

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
    // mybatis全局配置文件的位置
    String resource = "mybatis-conf.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    // 1.创建SqlSessionFactory对象
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
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

}
