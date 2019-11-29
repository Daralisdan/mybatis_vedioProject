package com.daralisdan.service;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.daralisdan.bean.Employee;
import com.daralisdan.dao.EmployeeMapper;

@Service
public class EmployeeService {

  @Autowired
  private EmployeeMapper employeeMapper;

  /**
   * 执行批量操作的方法
   */
  @Autowired
  private SqlSession sqlSession;

  public List<Employee> getEmps() {
    // 执行批量操作的 方法
    // EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
    return employeeMapper.getEmps();
  }

}
