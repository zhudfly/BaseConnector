package com.connector.zhudf.netmodule.net.baseclass;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by zhudf on 2016/12/16.
 */

public class BaseNetConnector {

    private CompositeSubscription compositeSubscription;

    public BaseNetConnector(){
        this.compositeSubscription = new CompositeSubscription();
    }

    public void unSubScribe(){
        if(compositeSubscription != null && compositeSubscription.isUnsubscribed()){
            compositeSubscription.unsubscribe();
            compositeSubscription.clear();
        }
    }

    public CompositeSubscription getCompositeSubscription() {
        if(this.compositeSubscription == null){
            this.compositeSubscription = new CompositeSubscription();
        }
        return compositeSubscription;
    }
}
