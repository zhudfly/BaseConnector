package com.connector.zhudf.utilsmodule.utils;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;


/**
 * Created by zhudf on 2016/12/23.
 */

public class AppInfoUtil {

    public static String getAppName() {
        String appName = "";
        try {
            ApplicationInfo applicationInfo = BaseConfig.getInstance().getContext()
                    .getPackageManager()
                    .getApplicationInfo(getPackageName(), 0);
            appName = String.valueOf(BaseConfig.getInstance().getContext()
                    .getPackageManager().getApplicationLabel(applicationInfo));

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return appName;
    }

    public static String getPackageName() {
        return BaseConfig.getInstance().getContext().getPackageName();
    }

    public static String getLastPackageName() {
        String packageName = getPackageName();
        String lastName = "";
        if (!TextUtils.isEmpty(packageName)) {
            lastName = packageName.substring(packageName.lastIndexOf(".") + 1,packageName.length());
        }
        return lastName;
    }

    public static int getVersionCode() {
        int versionCode = 0;
        try {
            versionCode = BaseConfig.getInstance().getContext()
                    .getPackageManager()
                    .getPackageInfo(getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionCode;
    }

    public static String getVersionName() {
        String versionName = "";
        try {
            versionName = BaseConfig.getInstance().getContext()
                    .getPackageManager()
                    .getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionName;
    }

}
