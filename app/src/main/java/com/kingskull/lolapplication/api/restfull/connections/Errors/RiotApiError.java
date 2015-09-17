package com.kingskull.lolapplication.api.restfull.connections.Errors;

/**
 * Created by Usuario on 07/09/2015.
 */
public class RiotApiError {

    private String message;

    public RiotApiError(String message){
        this.message = message;
    }

    public RiotApiError(){}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
