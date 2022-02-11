package com.newgen.api.controllers;

import com.kelmorgan.callclienthandler.handler.RequestResponseHandler;
import com.kelmorgan.callclienthandler.services.RestServiceHandler;
import com.kelmorgan.callclienthandler.services.SoapServiceHandler;
import com.kelmorgan.xmlparser.parser.XmlParser;
import com.newgen.util.Constants;
import com.newgen.util.LogGenerator;
import com.newgen.util.Shared;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class BvnValidatorController {
    private final Logger logger = LogGenerator.getLoggerInstance(BvnValidatorController.class);
    private final RequestResponseHandler requestResponseHandler;
    private final String request;
    private final Map<String, String> result = new HashMap<>();

    public BvnValidatorController(String processName, String soapAction, String wiName, String endpoint, String request) {
        this.requestResponseHandler = new SoapServiceHandler(processName,wiName, endpoint,soapAction);
        this.request = request;
    }

    public Map<String, String> validateBvn() {
        logger.info("-------------New Validate Bvn Request-----------------");
        try {
            String handledRequest = requestResponseHandler.getHandledRequest(request);
            logger.info("handledRequest: " + handledRequest);
            String handledResponse = requestResponseHandler.getHandledResponse(handledRequest);
            logger.info("handledResponse: " + handledResponse);

            if (Shared.isNotEmpty(handledResponse)) {
                XmlParser xmlParser = new XmlParser(handledResponse);
                String responseCode = xmlParser.getValueOf("a:ResponseCode");

                if (bvnValidated(responseCode)) {
                    String fullName = xmlParser.getValueOf("a:LastName") + " " + xmlParser.getValueOf("a:FirstName") + " " + xmlParser.getValueOf("a:MiddleName");
                    logger.info("fullName: "+fullName);
                    result.put(Constants.successKey, fullName);
                } else {
                    String responseMessage = xmlParser.getValueOf("a:ResponseMessage");
                    result.put(Constants.errorKey, responseMessage);
                }
            } else result.put(Constants.errorKey, "No response from NIBBS Validate BVN Api. " + Constants.exceptionMsg);

        } catch (Exception e) {
            result.clear();
            result.put(Constants.errorKey, "Exception occurred in calling NIBBS validate BVN Api. " + Constants.exceptionMsg);
        }
        return result;
    }

    private boolean bvnValidated(String responseCode) {
        return responseCode.equalsIgnoreCase("00");
    }
}
