package com.eelly.java.core;

import okhttp3.MultipartBody;

public class ParamWrap {
    private Object value;

    public ParamWrap(Integer value) {
        this.value = value;
    }

    public ParamWrap(String value) {
        this.value = value;
    }

    public void addPartTo(MultipartBody.Builder bodyBuilder, String prefix) {
        String type = value.getClass().getName();
        switch (type) {
            case "java.lang.Integer":
            case "java.lang.String":
                bodyBuilder.addFormDataPart(prefix, value.toString());
                break;
        }
    }
}
