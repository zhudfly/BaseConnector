package com.connector.zhudf.netmodule.net.entity;

import com.connector.zhudf.netmodule.net.baseclass.BaseNetEntity;

/**
 * Created by zhudf on 2016/12/16.
 */

public class BootAdvEntity extends BaseNetEntity {

    /**
     * imageUrl : http://share.qichacha.com/app-v9/app-luncher/9.2.2.html
     * redirctUrl : http://share.qichacha.com/app-new/ad/newapp.html
     * stopTime : 3
     */

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private String imageUrl;
        private String redirctUrl;
        private int stopTime;
        private int isWeb;

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getRedirctUrl() {
            return redirctUrl;
        }

        public void setRedirctUrl(String redirctUrl) {
            this.redirctUrl = redirctUrl;
        }

        public int getStopTime() {
            return stopTime;
        }

        public void setStopTime(int stopTime) {
            this.stopTime = stopTime;
        }

        public int getIsWeb() {
            return isWeb;
        }

        public void setIsWeb(int isWeb) {
            this.isWeb = isWeb;
        }
    }
}
