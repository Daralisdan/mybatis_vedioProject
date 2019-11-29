package com.daralisdan.dao;

import com.daralisdan.bean.Department;

public interface DepartMapper {

  public Department getDeptById(Integer id);


  // 查询部门的时候，将部门对应的所有员工信息也查询出来
  public Department getDeptByIdPlus(Integer id);

  // collection 分步查询 查询部门的时候，将部门对应的所有员工信息也查询出来
  public Department getDeptByIdStep(Integer id);

}
