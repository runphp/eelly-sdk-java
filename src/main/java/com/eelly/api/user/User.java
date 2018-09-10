package com.eelly.api.user;

import com.eelly.api.user.dto.UserDto;
import com.eelly.java.core.ApiOptions;
import com.eelly.java.core.EellyClient;
import com.eelly.java.core.ParamWrap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class User {
    final private static String gateWay = ApiOptions.GATEWAY_MAP.get("example");

    public UserDto getInfo() {
        String url = gateWay + "/user/user/getInfo";

        JsonObject jsonObject = EellyClient.post(url);
        JsonElement jsonElement = jsonObject.getAsJsonObject().get("data");

        UserDto userDto = EellyClient.gson().fromJson(jsonElement, UserDto.class);

        return userDto;
    }


    public UserDto getUserByPassword(String username, String password) {


        String url = gateWay + "/user/user/getUserByPassword";
        JsonObject returnJsonObject = EellyClient.post(url, new ParamWrap(username), new ParamWrap(password));
        JsonElement jsonElement = returnJsonObject.getAsJsonObject().get("data");

        UserDto userDto = EellyClient.gson().fromJson(jsonElement, UserDto.class);


        return userDto;
    }
}
