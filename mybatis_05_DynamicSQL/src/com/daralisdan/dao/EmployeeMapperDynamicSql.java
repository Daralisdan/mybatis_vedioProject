package com.daralisdan.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.daralisdan.bean.Employee;

public interface EmployeeMapperDynamicSql {


  // 查询员工，要求：携带了哪个字段查询条件就带上哪个这个字段的值
  public List<Employee> getEmpsByConditionIf(Employee employee);

  // trim
  public List<Employee> getEmpsByConditionTrim(Employee employee);

  // choose 带有break的switch，带有哪个属性的值就用哪个查询数据，只会进入其中一个
  public List<Employee> getEmpsByConditionChoose(Employee employee);

  // set 更新
  public void updateEmp(Employee employee);

  // 查询员工id在给定的集合中
  public List<Employee> getEmpByConditionForeach(@Param("ids") List<Integer> ids);

  // 批量保存
  public void addEmps(@Param("emps") List<Employee> emps);

}
