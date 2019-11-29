package com.daralisdan.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.daralisdan.bean.Employee;
import com.daralisdan.dao.EmployeeMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class EmployeeTest {
	@Resource
	EmployeeMapper empMapper;

	/**
	 * 
	 * Title：Test02 <br>
	 * spring的单元测试Junit
	 * date：2019年9月15日 下午7:23:54 <br>
	 * 
	 * @throws IOException <br>
	 */
	@Test
	public void Test02() {
		List<Employee> emps = empMapper.getEmps();
		for (Employee emp : emps) {
			System.out.println(emp);
		}

	}

}
