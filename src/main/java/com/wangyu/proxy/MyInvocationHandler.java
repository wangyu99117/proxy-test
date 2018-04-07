package com.wangyu.proxy;

import com.wangyu.proxy.util.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by wangyu21 on 2017/2/23.
 */
public class MyInvocationHandler implements InvocationHandler {

    public static final Logger LOGGER = LoggerFactory.getLogger(MyInvocationHandler.class);

    protected Object jsfService;
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try{
            LOGGER.error("远程调用!interface" + jsfService.getClass().getName());
            return method.invoke(jsfService, args);
        }catch (RpcException e){
            LOGGER.error("远程调用失败！interface" + jsfService.getClass(),e);
        } catch (InvocationTargetException e) {
            LOGGER.error("反射失败！interface" + jsfService.getClass(),e);
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            LOGGER.error("未知失败！interface" + jsfService.getClass(),e);
            e.printStackTrace();
        }
        return null;
    }

    public void setObject(PublishInfoServiceStub temp){
        jsfService = temp;
    }
}
