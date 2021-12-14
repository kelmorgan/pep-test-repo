package com.newgen.api.controllers;

import com.kelmorgan.callclienthandler.handler.RequestResponseHandler;
import com.kelmorgan.callclienthandler.services.SoapServiceHandler;
import com.kelmorgan.xmlparser.parser.XmlParser;
import com.newgen.utils.Constants;
import com.newgen.utils.LogGenerator;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class AccountDetailsController {
    private final Logger logger = LogGenerator.getLoggerInstance(AccountDetailsController.class);
    private final RequestResponseHandler requestResponseHandler;
    private final String request;
    private final Map<String, String> result = new HashMap<>();

    public AccountDetailsController(String processName, String wiName, String callType, String appCode, String endPoint, String request) {
        this.request = request;
        this.requestResponseHandler = new SoapServiceHandler(processName,appCode,wiName,callType,endPoint);
    }

    public Map<String,String> getAccountDetails(){
        logger.info("-------------------New Fetch account details request-------------------");
        try {
            String handledRequest = requestResponseHandler.getHandledRequest(request);
            logger.info("handledRequest: " + handledRequest);
            String handledResponse = requestResponseHandler.getHandledResponse(handledRequest);
            logger.info("handledResponse: " + handledResponse);

            XmlParser xmlParser = new XmlParser(handledResponse);
            String status = xmlParser.getValueOf(Constants.apiStatus);

            if (isStatusSuccess(status)) {
                String sol = xmlParser.getValueOf("SOL");
                logger.info("sol- "+ sol);
                String branchName = xmlParser.getValueOf("SOLDESC");
                logger.info("branchName- "+ branchName);

                String cusDetails = xmlParser.getValueOf("PersonName");
                xmlParser.setInputXML(cusDetails);
                String name = xmlParser.getValueOf("Name");
                logger.info("name- "+ name);

                result.put("sol",sol);
                result.put("branchName",branchName);
                result.put("name",name);
            } else if (isStatusFailed(status)) {
                String errorDesc = xmlParser.getValueOf("ErrorDesc");
                errorDesc = errorDesc + ". " + Constants.exceptionMsg;
                result.put(Constants.errorKey,errorDesc);
            }
            logger.info("-------------------Call Completed-------------------");
        } catch (Exception e){
            String exception = "Exception occurred in Fetching Account Details:  "+e.getMessage();
            logger.error("Exception message: "+ exception);
            result.put(Constants.errorKey,exception);
        }
        return result;
    }

    private static boolean isStatusSuccess(String status){
        return status.equalsIgnoreCase(Constants.apiSuccess);
    }

    private static boolean isStatusFailed(String status){
        return status.equalsIgnoreCase(Constants.apiFailed) || status.equalsIgnoreCase(Constants.apiFailure);
    }
}
