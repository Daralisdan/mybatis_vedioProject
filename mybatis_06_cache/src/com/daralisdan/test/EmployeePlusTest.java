package com.daralisdan.test;

import java.io.IOException;
import java.io.InputStream;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import com.daralisdan.bean.Department;
import com.daralisdan.bean.Employee;
import com.daralisdan.dao.DepartMapper;
import com.daralisdan.dao.EmployeeMapperPlus;

public class EmployeePlusTest {

  // 提取公共的方法
  public SqlSessionFactory getSqlSessionFactory() throws IOException {
    // 获取配置文件的位置信息
    String resource = "mybatis-conf.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    // 创建SQLSessionFactory
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    return sqlSessionFactory;

  }

  @Test
  public void test() throws IOException {
    SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
    // 获取SQLSession对象
    SqlSession openSession = sqlSessionFactory.openSession();
    try {
      EmployeeMapperPlus mapper = openSession.getMapper(EmployeeMapperPlus.class);
      Employee id = mapper.getEmpId(1);
      System.out.println(id);
    } finally {
      openSession.close();
    }
  }

  /**
   * 
   * Title：test02 <br>
   * Description：查询员工带有部门 <br>
   * author：yaodan  <br>
   * date：2019年9月17日 下午11:11:41 <br>
   * @throws IOException <br>
   */
  @Test
  public void test02() throws IOException {
    SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
    SqlSession openSession = sqlSessionFactory.openSession();
    try {
      EmployeeMapperPlus mapper = openSession.getMapper(EmployeeMapperPlus.class);
      Employee empIdDept = mapper.getEmpIdDept(1);
      System.out.println(empIdDept);
      System.out.println(empIdDept.getDept());

     
         } finally {
      openSession.close();
    }
  }

  /**
   * 
   * Title：test03 <br>
   * Description：查询部门 <br>
   * author：yaodan  <br>
   * date：2019年9月17日 下午11:11:18 <br>
   * @throws IOException <br>
   */
  @Test
  public void test03() throws IOException {
    SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
    SqlSession openSession = sqlSessionFactory.openSession();
    try {
      DepartMapper mapper = openSession.getMapper(DepartMapper.class);
      Department deptById = mapper.getDeptById(1);
      System.out.println(deptById);
    } finally {
      openSession.close();
    }
  }

  /**
   * 
   * Title：Test04 <br>
   *  使用association 分步查询
   * author：yaodan  <br>
   * date：2019年9月18日 上午12:21:59 <br>
   * @throws IOException <br>
   */
  @Test
  public void Test04() throws IOException {
    SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
    SqlSession openSession = sqlSessionFactory.openSession();
    try {
//      EmployeeMapperPlus mapper = openSession.getMapper(EmployeeMapperPlus.class);
//      Employee empAndDeptStep = mapper.getEmpAndDeptStep(1);
//      // System.out.println(empAndDeptStep);
//      System.out.println(empAndDeptStep.getLastName());
//      System.out.println(empAndDeptStep.getDept());
      
      // 鉴别器分步查询
      EmployeeMapperPlus mapper = openSession.getMapper(EmployeeMapperPlus.class);
      Employee empAndDeptStep = mapper.getEmpAndDeptStep(1);
      // System.out.println(empAndDeptStep);
      System.out.println(empAndDeptStep.getLastName());
      System.out.println(empAndDeptStep.getDept());
    } finally {
      openSession.close();
    }

  }

  // 查询部门的时候，将部门对应的所有员工信息也查询出来
  @Test
  public void Test05() throws IOException {
    SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
    SqlSession openSession = sqlSessionFactory.openSession();
    try {
      DepartMapper mapper = openSession.getMapper(DepartMapper.class);
      // Department deptByIdPlus = mapper.getDeptByIdPlus(1);
      // System.out.println(deptByIdPlus);
      // System.out.println(deptByIdPlus.getEmps());

      // collection 分步查询
      Department deptByIdStep = mapper.getDeptByIdStep(1);
      System.out.println(deptByIdStep);
      System.out.println(deptByIdStep.getEmps());
      System.out.println(deptByIdStep.getDeptName());

    } finally {
      openSession.close();
    }
  }

}
