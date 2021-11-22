package com.newgen.api.generateXml;

public class CallClientRequestHandler {

    private final String processName;
    private final String ngMethod;
    private final String wiName;
    private final String callType;
    private final String endpoint;

    public CallClientRequestHandler(String processName, String ngMethod, String wiName, String callType, String endpoint) {
        this.processName = processName;
        this.ngMethod = ngMethod;
        this.wiName = wiName;
        this.callType = callType;
        this.endpoint = endpoint;
    }

    public String getCallClientRequest(String request){
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        stringBuilder.append("<message>");
        stringBuilder.append("<ngProcess>").append(processName).append("</ngProcess>");
        stringBuilder.append("<ngMethod>").append(ngMethod).append("</ngMethod>");
        stringBuilder.append("<ngOFId>").append(wiName).append("</ngOFId>");
        stringBuilder.append("<callType>").append(callType).append("</callType>");
        stringBuilder.append("<EndPointUrl>").append(endpoint).append("</EndPointUrl>");
        stringBuilder.append("<ngXmlRequest>").append(request).append("</ngXmlRequest>");
        stringBuilder.append("</message>");


        return stringBuilder.toString();
    }
}
