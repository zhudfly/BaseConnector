package com.connector.zhudf.netmodule.net.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.connector.zhudf.netmodule.net.baseclass.BaseNetEntity;

/**
 * Created by zhangbiao on 2016/4/15.
 */
public class TokenEntity extends BaseNetEntity implements Parcelable {

    /**
     * access_token : MjhlYzhjZjYtNWRhMC00YTVhLTgyYjctYTA2Yjg4ZTRmYTcx
     * token_type : Bearer
     * expires_in : 3600
     * refresh_token : ZGMxMDczMmItYzYxNS00OWJiLTkzZmItNmU3Y2VkNjk0NmZi
     */

    private String access_token;
    private String token_type;
    private int expires_in;
    private String refresh_token;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.access_token);
        dest.writeString(this.token_type);
        dest.writeInt(this.expires_in);
        dest.writeString(this.refresh_token);
    }

    public TokenEntity() {
    }

    protected TokenEntity(Parcel in) {
        this.access_token = in.readString();
        this.token_type = in.readString();
        this.expires_in = in.readInt();
        this.refresh_token = in.readString();
    }

    public static final Creator<TokenEntity> CREATOR = new Creator<TokenEntity>() {
        @Override
        public TokenEntity createFromParcel(Parcel source) {
            return new TokenEntity(source);
        }

        @Override
        public TokenEntity[] newArray(int size) {
            return new TokenEntity[size];
        }
    };
}
