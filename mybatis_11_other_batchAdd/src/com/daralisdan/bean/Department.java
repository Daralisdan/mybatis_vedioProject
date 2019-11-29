package com.daralisdan.bean;

import java.util.List;

public class Department {
  private Integer id;
  private String deptName;

  // 一个部门下的所有员工
  private List<Employee> emps;

  
  public Department() {
    super(); // TODO %CodeTemplates.constructorstub.tododesc
  }

  public Department(Integer id) {
    super();
    this.id = id;
  }

  public List<Employee> getEmps() {
    return emps;
  }

  public void setEmps(List<Employee> emps) {
    this.emps = emps;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getDeptName() {
    return deptName;
  }

  public void setDeptName(String deptName) {
    this.deptName = deptName;
  }

  @Override
  public String toString() {
    return "Depertment [id=" + id + ", deptName=" + deptName + "]";
  }

}
