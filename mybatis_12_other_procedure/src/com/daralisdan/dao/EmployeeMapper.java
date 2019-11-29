package com.daralisdan.dao;

import com.daralisdan.bean.Employee;
import com.daralisdan.bean.Page;

public interface EmployeeMapper {



  // 增加
  public void addEmp(Employee employee);


  // 删除
  public void deleteEmpById(Integer id);

  // 批量保存操作 batch
  public Long batchAddEmp(Employee employee);

  // mybatis 调用存储过程
  public void getPageByProcedure(Page page);

}
