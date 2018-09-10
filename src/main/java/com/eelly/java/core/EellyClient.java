package com.eelly.java.core;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Util;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import java.io.IOException;

/**
 * @author hehui<runphp @ qq.com>
 */
public class EellyClient {

    private static OkHttpClient client = new OkHttpClient()
            .newBuilder()
            .addInterceptor((new HttpLoggingInterceptor()).setLevel(HttpLoggingInterceptor.Level.BODY))
            .build();

    private static JsonParser jsonParser = new JsonParser();

    private static Gson gson = new Gson();
    private static OAuthAccessTokenResponse accessTokenResponse;
    private static long expireTime;

    public static OAuthAccessTokenResponse getAccessTokenResponse() {
        return accessTokenResponse;
    }

    public static void setAccessTokenResponse(OAuthAccessTokenResponse accessTokenResponse) {
        EellyClient.accessTokenResponse = accessTokenResponse;
    }

    public static Gson gson() {
        return gson;
    }

    // 密码登录
    public static void createTokenWithPasswod(String username, String password) throws OAuthSystemException, OAuthProblemException {

        OAuthClientRequest request = OAuthClientRequest
                .tokenLocation(ApiOptions.OAUTH_SERVER_TOKEN_URL)
                .setGrantType(GrantType.PASSWORD)
                .setClientId(ApiOptions.CLIENT_ID)
                .setClientSecret(ApiOptions.CLIENT_SECRET)
                .setUsername(username)
                .setPassword(password)
                .buildBodyMessage();

        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
        accessTokenResponse = oAuthClient.accessToken(request);
        expireTime = System.currentTimeMillis()  + accessTokenResponse.getExpiresIn() * 1000;
    }

    // token 刷新
    public static void refreshToken(String refreshToken) throws OAuthSystemException, OAuthProblemException {

        OAuthClientRequest request = OAuthClientRequest
                .tokenLocation(ApiOptions.OAUTH_SERVER_TOKEN_URL)
                .setGrantType(GrantType.REFRESH_TOKEN)
                .setClientId(ApiOptions.CLIENT_ID)
                .setClientSecret(ApiOptions.CLIENT_SECRET)
                .setRefreshToken(refreshToken)
                .buildBodyMessage();

        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
        accessTokenResponse = oAuthClient.accessToken(request);
        expireTime = System.currentTimeMillis() / 1000 + accessTokenResponse.getExpiresIn() - 10;
    }

    public static JsonObject post(String url) {

        if (System.currentTimeMillis() > expireTime) {
            try {
                refreshToken(accessTokenResponse.getRefreshToken());
            } catch (OAuthSystemException e) {
                e.printStackTrace();
            } catch (OAuthProblemException e) {
                e.printStackTrace();
            }
        }
        MultipartBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addPart(Util.EMPTY_REQUEST)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .header(OAuth.HeaderType.AUTHORIZATION, accessTokenResponse.getAccessToken())
                .build();
        try {
            Response response = client.newCall(request).execute();
            return jsonParser.parse(response.body().string()).getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JsonObject post(String url, ParamWrap... params) {
        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        int i = 0;
        for (ParamWrap paramWrap : params) {
            paramWrap.addPartTo(bodyBuilder, String.valueOf(i++));
        }
        Request request = new Request.Builder()
                .url(url)
                .post(bodyBuilder.build())
                .header(OAuth.HeaderType.AUTHORIZATION, accessTokenResponse.getAccessToken())
                .build();
        try {
            Response response = client.newCall(request).execute();
            return jsonParser.parse(response.body().string()).getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
