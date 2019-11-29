package com.daralisdan.dao;


import java.util.List;

import com.daralisdan.bean.Employee;

public interface EmployeeMapper {


  // 查询 单个参数
  public Employee getEmpId(Integer id);

  //查询所有
  public List<Employee> getEmps();
}
