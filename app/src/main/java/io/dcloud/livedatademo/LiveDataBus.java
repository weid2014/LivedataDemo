package io.dcloud.livedatademo;

import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wade
 * @Description:(用一句话描述)
 * @date 2023/6/15 21:05
 */
public class LiveDataBus {
    private static LiveDataBus liveDataBus=new LiveDataBus();
    //容器
    private Map<String, MutableLiveData<Object>> map;

    private LiveDataBus(){
        map=new HashMap<>();
    }

    public static LiveDataBus getInstance(){
        return liveDataBus;
    }

    /**
     * 集成了两个功能  创建管道和取管道
     * @param key
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> MutableLiveData<T> with(String key,Class<T> tClass){
        if(!map.containsKey(key)){
            map.put(key,new MutableLiveData<Object>());
        }
        //在map中已经包含这个key和这个key对应的value'
        return (MutableLiveData<T>) map.get(key);
    }
}
