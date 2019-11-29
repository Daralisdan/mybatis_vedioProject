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
import com.daralisdan.dao.EmployeeMapperDynamicSql;

public class EmployeeMapperDynamicSqlTest {


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
   * Title：testDynamicSql <br>
   *测试动态sql if判断
   * author：yaodan  <br>
   * date：2019年9月20日 下午4:55:34 <br> <br>
   * @throws IOException 
   */
  @Test
  public void testDynamicSql() throws IOException {
    SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
    SqlSession openSession = sqlSessionFactory.openSession();
    try {
      EmployeeMapperDynamicSql mapper = openSession.getMapper(EmployeeMapperDynamicSql.class);
      /*
       * 查询的时候如果某些条件没带可能sql拼装问题 解决办法: 1.给where 后面加上1=1，以后的条件都加上and xxx 2.mybatis使用where
       * 标签来将所有的查询条件包括在内,mybatis就会将where标签中拼装的sql,多出来的and或者or去掉， 但是如果把and写在sql语句之后就不好使了
       * where只会去掉第一个多出来的or或者and
       */

      // 测试if/where
      // Employee employee = new Employee(null, "%e%", null, null);
      // List<Employee> emps = mapper.getEmpsByConditionIf(employee);
      // for (Employee emp : emps) {
      // System.out.println(emp);
      //
      // }

      // 测试trim
      // Employee employee = new Employee(null, "%e%", null, null);
      // List<Employee> emps = mapper.getEmpsByConditionTrim(employee);
      // for (Employee emp : emps) {
      // System.out.println(emp);
      //
      // }

      // 测试choose
      // Employee employee = new Employee(null, "%e%", null, null);
      // List<Employee> emps = mapper.getEmpsByConditionChoose(employee);
      // for (Employee emp : emps) {
      // System.out.println(emp);
      // }

      // // 测试set 更新
      // Employee employee = new Employee(1, "admin", null, null);
      // mapper.updateEmp(employee);
      // openSession.commit();
      // System.out.println("更新成功 last_name:" + employee.getLastName());

      // 查询员工id在给定的集合中
      // Employee employee = new Employee(1, "admin", null, null);

      // asList(1, 2, 3, 4)表示传的值,查出几条数据
      //List<Employee> list = mapper.getEmpByConditionForeach(Arrays.asList(1, 2));
       List<Employee> list = mapper.getEmpByConditionForeach(Arrays.asList(1, 2, 3, 4,5,6));
      for (Employee emp : list) {
        System.out.println(emp);
      }
    } finally {
      openSession.close();
    }
  }

  @Test
  public void TestBatchSave() throws IOException {
    SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
    SqlSession openSession = sqlSessionFactory.openSession();
    try {
      EmployeeMapperDynamicSql mapper = openSession.getMapper(EmployeeMapperDynamicSql.class);
      List<Employee> list = new ArrayList<>();
      list.add(new Employee(null, "sminth",  "0","sminth@163",new Department(1)));
      list.add(new Employee(null, "Join",  "1","Join@163",new Department(1)));
      mapper.addEmps(list);
      // 手动提交
      openSession.commit();
      System.out.println("添加成功" + list);
    } finally {
      openSession.close();
    }
  }
}
