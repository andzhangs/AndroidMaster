package com.android.jetpack.lifecycle;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;


public class ActivityLifeObserver implements LifecycleObserver {

    private static final String TAG = "MActivityLifeObserver";
    
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void OnCreateEvent(){
        if (BuildConfig.DEBUG) {
            Log.i(TAG, "OnCreateEvent: "+Thread.currentThread());
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void OnStartEvent(){
        if (BuildConfig.DEBUG) {
            Log.i(TAG, "OnStartEvent: "+Thread.currentThread());
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void OnResumeEvent(){
        if (BuildConfig.DEBUG) {
            Log.i(TAG, "OnResumeEvent: "+Thread.currentThread());
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void OnPauseEvent(){
        if (BuildConfig.DEBUG) {
            Log.i(TAG, "OnPauseEvent: "+Thread.currentThread());
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void OnStopEvent(){
        if (BuildConfig.DEBUG) {
            Log.i(TAG, "OnStopEvent: "+Thread.currentThread());
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void OnDestroyEvent(){
        if (BuildConfig.DEBUG) {
            Log.i(TAG, "OnDestroyEvent: "+Thread.currentThread());
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    public void OnAnyEvent(){
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "OnAnyEvent: "+Thread.currentThread());
        }
    }

}
