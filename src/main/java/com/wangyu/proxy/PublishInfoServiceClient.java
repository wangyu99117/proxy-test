package com.wangyu.proxy;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Proxy;

/**
 * Created by wangyu21 on 2017/2/23.
 */
@Service("publishInfoServiceClientTest")
public class PublishInfoServiceClient implements FactoryBean {

    @Resource
    private Object publishInfoServiceStub;


    public Object getObject() throws Exception {
        MyInvocationHandler myInvocationHandler = new MyInvocationHandler();
        myInvocationHandler.setObject(publishInfoServiceStub);
        return (Object)Proxy.newProxyInstance(this.getClass().getClassLoader() , publishInfoServiceStub.getClass().getInterfaces(), myInvocationHandler);
    }


    public Class<?> getObjectType() {
        return Object.class;
    }


    public boolean isSingleton() {
        return true;
    }
}
