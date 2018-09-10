package com.eelly.api.example;

import com.eelly.java.core.ApiOptions;
import com.eelly.java.core.EellyClient;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Benchmark {
    final private String gateWay = ApiOptions.GATEWAY_MAP.get("example");

    public String helloWorld() {
        String url = gateWay + "/example/benchmark/helloWorld";

        JsonObject jsonObject = EellyClient.post(url);
        JsonElement jsonElement = jsonObject.getAsJsonObject().get("data");

        String string = EellyClient.gson().fromJson(jsonElement, String.class);


        return string;
    }
}
