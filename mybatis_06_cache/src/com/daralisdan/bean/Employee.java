package com.daralisdan.bean;

import java.io.Serializable;
import org.apache.ibatis.type.Alias;

// @Alias("emp")
public class Employee implements Serializable {
  
  /**   
   * serialVersionUID：TODO(用一句话描述这个变量表示什么) <br> 
   * @since v1.0 <br>
   */   
  
  private static final long serialVersionUID = 1L;
  
  private Integer id;
  private String lastName;
  private String gender;
  private String email;
  
  //一个部门对应一个员工
  private Department dept;

  public Employee() {
    super(); // TODO %CodeTemplates.constructorstub.tododesc
  }
 

  public Employee(Integer id, String lastName, String gender, String email) {
    super();
    this.id = id;
    this.lastName = lastName;
    this.gender = gender;
    this.email = email;
  }


  public Employee(Integer id, String lastName, String gender, String email, Department dept) {
    super();
    this.id = id;
    this.lastName = lastName;
    this.gender = gender;
    this.email = email;
    this.dept = dept;
  }


  public Department getDept() {
    return dept;
  }

  public void setDept(Department dept) {
    this.dept = dept;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String toString() {
    return "Employee [id=" + id + ", lastName=" + lastName + ", gender=" + gender + ", email="
        + email + "]";
  }



}
