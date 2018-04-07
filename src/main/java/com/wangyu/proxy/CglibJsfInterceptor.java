package com.wangyu.proxy;

import com.wangyu.proxy.util.RpcException;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by wangyu21 on 2017/3/29.
 */
public class CglibJsfInterceptor<T> implements MethodInterceptor{

    public static final Logger LOGGER = LoggerFactory.getLogger(CglibJsfInterceptor.class);
    protected T jsfService;

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        try{
            LOGGER.error("远程调用!cglib 动态代理 invoke interface" + jsfService.getClass().getName());
            Object object = methodProxy.invoke(jsfService, objects);
            //Object object = methodProxy.invokeSuper(o, objects);
            LOGGER.error("远程调用!cglib 动态代理 invoke interface" + jsfService.getClass().getName() +"jie kou diaoyong jieshu ");
            /*LOGGER.error("rpc result :" + JsonUtils.toJsonString(object));*/
            T temp = (T)object;
            /*LOGGER.error("rpc result convert:" + JsonUtils.toJsonString(temp));*/
            return temp;
        }catch (RpcException e){
            LOGGER.error("远程调用失败！cglib 动态代理 interface" + jsfService.getClass(),e);
        } catch (InvocationTargetException e) {
            LOGGER.error("反射失败！cglib 动态代理 interface" + jsfService.getClass(),e);
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            LOGGER.error("未知失败！cglib 动态代理 interface" + jsfService.getClass(),e);
            e.printStackTrace();
        } catch (Exception e) {
            LOGGER.error("未知失败！cglib 动态代理 interface" + jsfService.getClass(),e);
            e.printStackTrace();
        }
        return null;
    }

    public void setObject(T temp){
        jsfService = temp;
    }
}
