package utils;

import java.util.concurrent.TimeUnit;

/**
 * Created by zhudf on 2016/10/10.
 * 保存变量，变量可以修改来控制程序执行内容
 */

public class BuildConfig {
    /**
     *  single instance
     */
    public static BuildConfig buildConfig = null;

    /**
     *   current model
     */
    private boolean isDebug = false;

    /**
     * SSL certificate file name
     */
    private String sslCerFileName = "";

    /**
     * Net cache file name
     */
    private String netCacheFileName = "";

    /**
     * Net cache file max size
     **/
    private long netCacheMaxSize = 10 * 1024 * 1024;

    /**
     * Net timeout time
     */
    private long netExpirationTime = 5;

    /**
     * Net timeout unit
     **/
    private TimeUnit netExpirationTimeUnit = TimeUnit.SECONDS;

    /**
     * Api host ; eg. http(s)://www.xxx.com
     **/
    private String apiHost = "";

    /**
     * retrofit net service ; Retrofit 调用接口的类，放在DataConnector模块实现
     **/
    private String netServiceClass = "";

    private BuildConfig(){
    }

    /**
     * 获得配置文件的对象
     * @return 配置文件的对象
     */
    public static BuildConfig getInstance(){
        if(buildConfig == null){
            buildConfig = new BuildConfig();
        }
        return buildConfig;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public void setDebug(boolean debug) {
        isDebug = debug;
    }

    public String getSslCerFileName() {
        return sslCerFileName;
    }

    public void setSslCerFileName(String sslCerFileName) {
        this.sslCerFileName = sslCerFileName;
    }

    public String getNetCacheFileName() {
        return netCacheFileName;
    }

    public void setNetCacheFileName(String netCacheFileName) {
        this.netCacheFileName = netCacheFileName;
    }

    public long getNetCacheMaxSize() {
        return netCacheMaxSize;
    }

    public void setNetCacheMaxSize(long netCacheMaxSize) {
        this.netCacheMaxSize = netCacheMaxSize;
    }

    public long getNetExpirationTime() {
        return netExpirationTime;
    }

    public void setNetExpirationTime(long netExpirationTime) {
        this.netExpirationTime = netExpirationTime;
    }

    public TimeUnit getNetExpirationTimeUnit() {
        return netExpirationTimeUnit;
    }

    public void setNetExpirationTimeUnit(TimeUnit netExpirationTimeUnit) {
        this.netExpirationTimeUnit = netExpirationTimeUnit;
    }

    public String getApiHost() {
        return apiHost;
    }

    public void setApiHost(String apiHost) {
        this.apiHost = apiHost;
    }

    public String getNetServiceClass() {
        return netServiceClass;
    }

    public void setNetServiceClass(String netServiceClass) {
        this.netServiceClass = netServiceClass;
    }
}
