package com.wangyu.proxy;

import com.wangyu.proxy.util.ReflectionUtils;
import net.sf.cglib.proxy.Enhancer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;

/**
 * Created by wangyu21 on 2017/3/14.
 */
public abstract class ServiceProxy<T> implements FactoryBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceProxy.class);
    protected Class<T> entityClass;

    public ServiceProxy() {
        this.entityClass = ReflectionUtils.getSuperClassGenricType(getClass());
    }

    public T getObject() throws Exception {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(entityClass);
        CglibJsfInterceptor jsfInterceptor = new CglibJsfInterceptor<T>();
        jsfInterceptor.setObject(getService());
        enhancer.setCallback(jsfInterceptor);//gerInterceptor());
        return (T)enhancer.create();
    }

    public Class getObjectType() {
        return this.entityClass;
    }

    public boolean isSingleton() {
        return false;
    }

    public abstract T getService();

    //public abstract MethodInterceptor gerInterceptor();
}
