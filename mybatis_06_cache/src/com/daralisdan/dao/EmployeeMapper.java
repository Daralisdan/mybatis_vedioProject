package com.daralisdan.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import com.daralisdan.bean.Employee;

public interface EmployeeMapper {


  // 查询 单个参数
  public Employee getEmpId(Integer id);

  // 增加
  public void addEmp(Employee employee);

  // 修改
  // public void updateEmp(Employee employee);
  public boolean updateEmp(Employee employee);

  // 删除
  public void deleteEmpById(Integer id);

  // 多个参数时
  // public Employee getEmpIdAndLastName(Integer id, String lastName);
  // @Param("lastName")该注解指定map封装中key的值
  public Employee getEmpIdAndLastName(@Param("id") Integer id, @Param("lastName") String lastName);

  // 多个参数不是业务模型中的数据,可以传入map
  public Employee getEmpByMap(Map<String, Object> map);

  // 返回的是集合类型 查询
  public List<Employee> getEmpByLastNameLike(String lastName);

  // 返回一条记录的map,key就是列名，值就是对应的值
  public Map<String, Object> getEmpByIdReturnMap(Integer id);

  // 多条记录封装一个map, Map<String, Employee> 键是这条记录的主键，值是封装后的javaBean
  // 告诉mybatis封装这个map时使用哪个属性作为主键（map的key） 
  //@MapKey("id")//获取到的id作为主键key
  @MapKey("lastName") //获取到的名字作为主键key
  public Map<String, Employee> getEmpByLastNameLikeReturnMap(String lastName);
}
