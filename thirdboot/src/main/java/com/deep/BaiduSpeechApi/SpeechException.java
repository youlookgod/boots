package com.deep.BaiduSpeechApi;

public class SpeechException extends RuntimeException {
    private SpeechExceptionInterface speechExceptionInterface;
    public SpeechException(SpeechExceptionInterface speechExceptionInterface){
        super();
        this.speechExceptionInterface = speechExceptionInterface;
    }

    public SpeechExceptionInterface getSpeechExceptionInterface() {
        return speechExceptionInterface;
    }

    public void setSpeechExceptionInterface(SpeechExceptionInterface speechExceptionInterface) {
        this.speechExceptionInterface = speechExceptionInterface;
    }
}
