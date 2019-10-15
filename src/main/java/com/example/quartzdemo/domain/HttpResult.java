package com.example.quartzdemo.domain;

public class HttpResult {

    private int resultCode;

    private String resuktBody;


    public HttpResult(int resultCode, String resuktBody) {
        this.resultCode = resultCode;
        this.resuktBody = resuktBody;
    }



    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    public String getResuktBody() {
        return resuktBody;
    }

    public void setResuktBody(String resuktBody) {
        this.resuktBody = resuktBody;
    }
}
