package io.dcloud.livedatademo;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wade
 * @Description:(用一句话描述)
 * @date 2023/6/15 21:53
 */
public class MnLiveData<T> {
    //对象持有者
    private  T mData=null;
    //所有的观察者的容器
    private List<ObserverWrapper> mObservers=new ArrayList<>();
    //定义一个版本对象
    private int mVersion=-1;

    public void postValue(T value){
        mData=value;
        mVersion++;
        //遍历所有的观察者，然后回调
        dispatcheringValue();
    }

    private void dispatcheringValue() {
        for (ObserverWrapper mObserver:mObservers) {
            toChange(mObserver);
        }
    }

    public void toChange(ObserverWrapper mObserver) {
        //判断这个观察者绑定的组件是否可见
        if(mObserver.lifecycle.getCurrentState()!=Lifecycle.State.RESUMED){
            return;
        }
        if(mObserver.mLastVersion>=mVersion){
            return;
        }
        mObserver.mLastVersion=mVersion;
        mObserver.observer.onChanged(this.mData);
    }


    /**
     * 观察者类的封装类
     */
    private  class ObserverWrapper {
        //观察者
        Observer<T> observer;
        //持有组件的生命周期的对象
        Lifecycle lifecycle;
        //绑定生命周期的回调接口
        MyLifecycleBound myLifecycleBound;
        //观察者版本号
        int mLastVersion=-1;
        boolean isViscosity;

    }

    /**
     * 注册观察者
     */
    public void observer(LifecycleOwner owner,Observer<T> observer,boolean isViscosity){
        if(owner.getLifecycle().getCurrentState()==Lifecycle.State.DESTROYED){
            return;
        }
        ObserverWrapper observerWrapper=new ObserverWrapper();
        observerWrapper.isViscosity=isViscosity;
        observerWrapper.observer=observer;
        observerWrapper.lifecycle=owner.getLifecycle();
        observerWrapper.myLifecycleBound=new MyLifecycleBound();
        mObservers.add(observerWrapper);
        //不需要粘性事件
        if(!observerWrapper.isViscosity){
            observerWrapper.mLastVersion=mVersion;
        }
        owner.getLifecycle().addObserver(observerWrapper.myLifecycleBound);
    }


    class MyLifecycleBound implements LifecycleEventObserver{

        @Override
        public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
            Log.e("livedataTest",source.getLifecycle().getCurrentState().toString());
            //当组件生命周期结束的时候
            if(source.getLifecycle().getCurrentState()!= Lifecycle.State.DESTROYED){
                if(mData!=null){
                    dispatcheringValue();
                }
            }
        }
    }
}
