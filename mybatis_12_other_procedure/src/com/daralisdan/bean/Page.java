package com.daralisdan.bean;

import java.util.List;

/**
 * 封装分页数据
 * Description：TODO(这里用一句话描述这个类的作用) <br>
 * @author yaodan  <br>
 * date 2019年11月15日 下午10:45:57 <br>
 */
public class Page {
  private Integer start;
  private Integer end;
  private Integer count;
  private List<Employee> emps;

  public Integer getStart() {
    return start;
  }

  public void setStart(Integer start) {
    this.start = start;
  }

  public Integer getEnd() {
    return end;
  }

  public void setEnd(Integer end) {
    this.end = end;
  }

  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  public List<Employee> getEmps() {
    return emps;
  }

  public void setEmps(List<Employee> emps) {
    this.emps = emps;
  }

}
