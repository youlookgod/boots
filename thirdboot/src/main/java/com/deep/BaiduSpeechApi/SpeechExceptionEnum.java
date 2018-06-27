package com.deep.BaiduSpeechApi;

public enum SpeechExceptionEnum implements SpeechExceptionInterface {
    FILE_NOT_EXIST("00001","文件不存在"),
    IS_NOT_FILE("00002","不是有效文件"),
    IS_NOT_DIRECTORY("00003","不是有效目录"),
    TEXT_TO_LONG("00004","字符串过长");



    private String errorCode;
    private String errorMessage;

    SpeechExceptionEnum(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
    @Override
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
