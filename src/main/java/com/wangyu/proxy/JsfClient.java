package com.wangyu.proxy;


import com.wangyu.proxy.util.ReflectionUtils;
import com.wangyu.proxy.util.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by wangyu21 on 2017/2/23.
 * 业务模块继承此类
 */
public class JsfClient<T> implements InvocationHandler , FactoryBean, InitializingBean {

    public static final Logger LOGGER = LoggerFactory.getLogger(JsfClient.class);

    protected Class<T> entityClass;
    protected T jsfService;
    //动态代理对象
    //private T service;

    public Object invoke(Object proxy, Method method, Object[] args) {
        try{
            LOGGER.error("远程调用!interface" + jsfService.getClass().getName());
            return method.invoke(jsfService, args);
        }/*catch (RpcException e){
            LOGGER.error("远程调用失败！interface" + jsfService.getClass(),e);
        } */catch (InvocationTargetException e) {
            LOGGER.error("反射失败！interface" + jsfService.getClass(),e);
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            LOGGER.error("未知失败！interface" + jsfService.getClass(),e);
            e.printStackTrace();
        }
        return null;
    }

    public Object getObject() throws Exception {
        return (T)Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), jsfService.getClass().getInterfaces(), this);
    }

    public Class getObjectType() {
        return this.entityClass;
    }

    public boolean isSingleton() {
        return true;
    }

    public void afterPropertiesSet() throws Exception {
        this.entityClass = ReflectionUtils.getSuperClassGenricType(getClass());
        LOGGER.error(" test :"+this.entityClass.getSimpleName());
        String name = this.entityClass.getSimpleName();
        String temp = name.substring(0, 1).toLowerCase() + name.substring(1);
        LOGGER.error(" test :"+temp);
        jsfService = (T)SpringContextUtils.getBean(temp);
    }
}
