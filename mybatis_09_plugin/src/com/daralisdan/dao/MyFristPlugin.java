package com.daralisdan.dao;

import java.util.Properties;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

// 拦截 method拦截哪个方法
@Intercepts(value = {@Signature(args = {java.sql.Statement.class}, method = "parameterize",
    type = StatementHandler.class)})
public class MyFristPlugin implements Interceptor {

  @Override
  public Object intercept(Invocation invocation) throws Throwable {
    return null;
    // TODO %CodeTemplates.methodstub.tododesc return null;
  }

  @Override
  public Object plugin(Object target) {
    return null;
    // TODO %CodeTemplates.methodstub.tododesc return null;
  }

  @Override
  public void setProperties(Properties properties) {
    // TODO %CodeTemplates.methodstub.tododesc
  }

}
