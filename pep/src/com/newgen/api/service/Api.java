package com.newgen.api.service;

import com.newgen.callClient.CallHandler;

public class Api {

    public static String execute (String request){
        return CallHandler.executeIntegrationCall(request);
    }

}
