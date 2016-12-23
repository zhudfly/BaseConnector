package com.connector.zhudf.basemodule.utils;

import android.content.Context;
import android.os.Build;

import com.ta.utdid2.device.UTDevice;

/**
 * Created by zhudf on 2016/12/22.
 */

public class DeviceInfoUtil {

    /**
     * Mac Address
     */
    public static String getMacAddress(){
        return "";
    }

    /**
     * IP Address
     */
    public static String getIPAddress(){
        return "";
    }

    /**
     * System Version Code
     */
    public static int getSystemVersionCode(){
        return Build.VERSION.SDK_INT;
    }


    /**
     * System Version Release
     */
    public static String getSystemVersionRelease(){
        return Build.VERSION.RELEASE;
    }

    /**
     * Device Id : use third-lib to create deviceId
     */
    public static String getDeviceId(Context context){
        return UTDevice.getUtdid(context);
    }
}
