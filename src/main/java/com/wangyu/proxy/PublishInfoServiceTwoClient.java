package com.wangyu.proxy;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by wangyu21 on 2017/3/29.
 */
@Service("publishInfoServiceTwoClient")
public class PublishInfoServiceTwoClient extends ServiceProxy<Object>{

    @Resource(name="publishInfoRpc")
    private Object jsfService;


    @Override
    public Object getService() {
        return jsfService;
    }

}
