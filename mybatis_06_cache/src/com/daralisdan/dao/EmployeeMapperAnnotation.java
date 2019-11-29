package com.daralisdan.dao;

import org.apache.ibatis.annotations.Select;
import com.daralisdan.bean.Employee;

/**
 * sql基于注解版的，没有映射文件
 * @author yaodan  <br>
 * date 2019年9月15日 下午7:19:41 <br>
 */
public interface EmployeeMapperAnnotation {
 
   @Select("select * from tbl_employee where id=#{id}") 
  public Employee getEmpId(Integer id);
}
