package com.daralisdan.dao;


import java.util.List;
import com.daralisdan.bean.Employee;

public interface EmployeeMapperPlus {


  // 查询 单个参数
  public Employee getEmpId(Integer id);

  
  //查询员工带有部门
  public Employee getEmpIdDept(Integer id);
  
  //association 分步查询
  public Employee getEmpAndDeptStep(Integer id);

  //根据部门id,查询所有员工
  public List<Employee>  getEmpByDeptId(Integer DeptId);
}
