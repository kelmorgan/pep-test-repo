package com.newgen.api.controllers;

import com.kelmorgan.callclienthandler.handler.RequestResponseHandler;
import com.kelmorgan.callclienthandler.services.SoapServiceHandler;
import com.kelmorgan.xmlparser.parser.XmlParser;
import com.newgen.util.Constants;
import com.newgen.util.LogGenerator;
import com.newgen.util.Shared;
import org.apache.log4j.Logger;

import java.util.*;


public class AccountLinkedToBvnController {
    private final Logger logger = LogGenerator.getLoggerInstance(AccountLinkedToBvnController.class);
    private final RequestResponseHandler requestResponseHandler;
    private final String request;
    private final Map<String, Object> result = new HashMap<>();

    public AccountLinkedToBvnController(String processName, String ngMethod, String wiName, String callType, String endpoint, String request) {
        this.request = request;
        this.requestResponseHandler = new SoapServiceHandler(processName, ngMethod, wiName, callType, endpoint);
    }


    public Map<String, Object> getAccountList() {
        logger.info("-------------New Fetch Account Linked Request-----------------");
        try {
            String handledRequest = requestResponseHandler.getHandledRequest(request);
            logger.info("handledRequest: " + handledRequest);
            String handledResponse = requestResponseHandler.getHandledResponse(handledRequest);
            logger.info("handledResponse: " + handledResponse);

            List<String> accountList = new ArrayList<>();

            XmlParser xmlParser = new XmlParser(handledResponse);
            String status = xmlParser.getValueOf(Constants.apiStatus);
            String apiFlag = xmlParser.getValueOf(Constants.apiSuccessFailureFlag);

            if (isStatusSuccess(status) && isSuccessFailure(apiFlag, Constants.flagY)) {
                Set<Map<String, String>> data = xmlParser.getXMLData(handledResponse, "RMInfo");
                for (Map<String, String> dataSet : data) accountList.add(dataSet.get("AcctID"));
                result.put(Constants.successKey, accountList);
            } else if (isStatusSuccess(status) && isSuccessFailure(apiFlag, Constants.flagN)) {
                String errMsgErrorTag = xmlParser.getValueOf("Error");
                String errMsgMessageTag = xmlParser.getValueOf("Message");

                if (Shared.isNotEmpty(errMsgErrorTag)) {
                    result.put(Constants.errorKey, errMsgErrorTag);
                } else if (Shared.isNotEmpty(errMsgMessageTag)) {
                    result.put(Constants.errorKey, errMsgMessageTag);
                }
            } else if (isStatusFailed(status)) {
                String errorDesc = xmlParser.getValueOf("ErrorDesc");
                errorDesc = errorDesc + ". " + Constants.exceptionMsg;
                result.put(Constants.errorKey, errorDesc);
            }

            logger.info("-------------Call Completed-------------");
        } catch (Exception e) {
            String exception = "Exception occurred in Fetching Linked Accounts: " + e.getMessage();
            logger.error("Exception message: " + exception);
            result.put(Constants.errorKey, exception);
        }
        return result;
    }

    private static boolean isStatusSuccess(String status) {
        return status.equalsIgnoreCase(Constants.apiSuccess);
    }

    private static boolean isStatusFailed(String status) {
        return status.equalsIgnoreCase(Constants.apiFailed) || status.equalsIgnoreCase(Constants.apiFailure);
    }

    private static boolean isSuccessFailure(String apiFlag, String compareFlag) {
        return apiFlag.equalsIgnoreCase(compareFlag);
    }

}
