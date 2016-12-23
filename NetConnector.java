package Net;

import Net.baseclass.BaseNetEntity;
import rx.Observable;
import utils.RetrofitHelper;

/**
 * Created by zhudf on 2016/10/11.
 * 调用网络请求时，只通过调用该类实现
 */

public class NetConnector {
    public static Observable<BaseNetEntity> requestData(String method, Object... paramArray) {
        //TODO 判断网络是否连接
        return RetrofitHelper.getNetService().requestViaRetrofit(method, paramArray);
    }
}
