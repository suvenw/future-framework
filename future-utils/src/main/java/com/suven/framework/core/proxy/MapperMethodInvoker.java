package com.suven.framework.core.proxy;

import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.Method;

interface MapperMethodInvoker {
    Object invoke(Object proxy, Method method, Object[] args, SqlSession sqlSession) throws Throwable;
  }