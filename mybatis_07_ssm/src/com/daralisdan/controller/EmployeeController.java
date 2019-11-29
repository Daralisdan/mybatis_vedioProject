package com.daralisdan.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.daralisdan.bean.Employee;
import com.daralisdan.service.EmployeeService;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@RequestMapping("/getEmps")
	public String emps(Map<String, Object> map) {

		// 查询员工
		List<Employee> list = employeeService.getEmps();
		map.put("allEmp", list);
		return "list";

	}
}
