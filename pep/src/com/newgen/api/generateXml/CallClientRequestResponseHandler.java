package com.newgen.api.generateXml;

import com.newgen.util.LogGenerator;
import com.newgen.util.Shared;
import com.newgen.util.XmlParser;
import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CallClientRequestResponseHandler {
    private final Logger logger = LogGenerator.getLoggerInstance(CallClientRequestResponseHandler.class);

    private  String processName;
    private  String ngMethod;
    private  String wiName;
    private  String callType;

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public void setNgMethod(String ngMethod) {
        this.ngMethod = ngMethod;
    }

    public void setWiName(String wiName) {
        this.wiName = wiName;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    private  String endpoint;

    public CallClientRequestResponseHandler(){

    }
    public CallClientRequestResponseHandler(String processName, String ngMethod, String wiName, String callType, String endpoint) {
        this.processName = processName;
        this.ngMethod = ngMethod;
        this.wiName = wiName;
        this.callType = callType;
        this.endpoint = endpoint;
    }

    public String getHandledRequest(String request){
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

    public String getHandledResponse(String response){
        try {
            if (Shared.isEmpty(response)) return "No Response from Call Client";
            String message = new XmlParser(response).getValueOf("message");
            String mainCode = new XmlParser(message).getValueOf("MainCode");
            if (Shared.isNotEmpty(mainCode) && isSuccess(mainCode)) return handler(message);

        } catch (Exception e){
            logger.error("Exception occurred in getHandledResponse method:  "+ e.getMessage());
        }
        return null;
    }



    private String handler(String message){
        String newString = null;
        String REGULAR_EXPRESSION= "(\\<MainCode>.+?\\</MainCode>)";
        Pattern pattern = Pattern.compile(REGULAR_EXPRESSION, Pattern.DOTALL | Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            newString = message.replaceAll(matcher.group(1), "");
        }
        return newString;
    }
    private boolean isSuccess(String mainCode){
        return mainCode.equalsIgnoreCase("0");
    }

}
