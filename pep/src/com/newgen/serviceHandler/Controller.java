package com.newgen.serviceHandler;

import com.newgen.api.generateXml.CallClientRequestResponseHandler;
import com.newgen.api.generateXml.RequestXml;
import com.newgen.api.service.Api;
import com.newgen.util.Constants;
import com.newgen.util.LogGenerator;
import com.newgen.util.Shared;
import com.newgen.util.XmlParser;
import org.apache.log4j.Logger;

import java.util.*;

public class Controller implements Constants {

    private static final Logger logger = LogGenerator.getLoggerInstance(Controller.class);


    public static List<HashMap<String,String>> getAccountListTest(){

        List<HashMap<String,String>> accountList = new ArrayList<>();
        HashMap<String ,String> row1 = new HashMap<>();
        HashMap<String ,String> row2 = new HashMap<>();

        row1.put("accountNumber","3297471273");
        row1.put("accountName","John Doe");
        row1.put("solId","230");
        row1.put("branchName","Marina");


        row2.put("accountNumber","2092123445");
        row2.put("accountName","John Doe");
        row2.put("solId","191");
        row2.put("branchName","Iganmu");

        accountList.add(0,row1);
        accountList.add(1,row2);

        return accountList;
    }

    public static HashMap<String,Object> getAccountLinkedToBvn(String bvn,String wiName){
        HashMap<String,Object> result = new HashMap<>();
        try {
            CallClientRequestResponseHandler handler = new CallClientRequestResponseHandler(pepProcessName, getBvnAcctListAppCode, wiName, callTypeFinacle, endpointCustomFIFinacle);
            String request = handler.getHandledRequest(RequestXml.getBvnLinkAcctRequest(bvn));
            logger.info("getAccountLinkedToBvn request: " + request);
            String response = handler.getHandledResponse(Api.execute(request));
            logger.info("getAccountLinkedToBvn response: " + response);

            List<String> accountList = new ArrayList<>();


            if (Shared.isNotEmpty(response)){

                XmlParser xmlParser = new XmlParser(response);
                String status = xmlParser.getValueOf(apiStatus);
                String apiFlag = xmlParser.getValueOf(apiSuccessFailureFlag);

                if (isStatusSuccess(status) && isSuccessFailure(apiFlag,flag)){
                    Set<Map<String,String>> data = xmlParser.getXMLData(response,"RMInfo");
                    for (Map<String,String> dataSet : data) accountList.add(dataSet.get("AcctID"));

                    result.put(successKey,accountList);
                    return result;
                }
                else if (isStatusSuccess(status) && isSuccessFailure(apiFlag,flagN)){
                    String errMsgErrorTag = xmlParser.getValueOf("Error");
                    String errMsgMessageTag = xmlParser.getValueOf("Message");

                    if (Shared.isNotEmpty(errMsgErrorTag)) {
                        result.put(errorKey,errMsgErrorTag);

                        return result;
                    }
                    else if (Shared.isNotEmpty(errMsgMessageTag)){
                        result.put(errorKey,errMsgMessageTag);
                        return result;
                    }

                }
                else if (isStatusFailed(status)){
                    String errorDesc = xmlParser.getValueOf("ErrorDesc");
                    errorDesc = errorDesc + ": Kindly Contact iBPS support";
                    result.put(errorKey,errorDesc);
                    return result;
                }
            }
            else {
                String exception = "No Response for fetch Account Linked to BVN Api Call. Contact iBPS support";
                result.put(errorKey,exception);
            }

        } catch (Exception e){
            String exception = "Exception occurred in getAccountLinkedToBvn method: "+e.getMessage();
            logger.error(exception);
            result.put(errorKey,exception);

            return result;
        }
        return null;
    }


    public static HashMap<String,String> fetchAcctDetails(String accountNumber,String wiName){
        logger.info("Welcome to fetch account details call");
        HashMap<String,String> result = new HashMap<>();
        try {
            String request;
            String response = null;
            logger.info("account Number-- "+ accountNumber );

            CallClientRequestResponseHandler handler =new CallClientRequestResponseHandler();

            handler.setProcessName(pepProcessName);
            handler.setWiName(wiName);
            handler.setCallType(callTypeFinacle);
            handler.setEndpoint(endpointCustomFIFinacle);

            if (Shared.isEmpty(accountNumber)){
                result.put(errorKey,"No accountNumber to fetch");
                return result;
            }

            if (accountNumber.startsWith("1")){
                handler.setNgMethod(getSpecialAcctAppcode);
                request = handler.getHandledRequest(RequestXml.getSavingAcctRequest(accountNumber));
                logger.info("Special account request: "+request);
                response = handler.getHandledResponse(Api.execute(request));
            }

            else if (accountNumber.startsWith("2")){
                handler.setEndpoint(endpointBpmFinacle);
                handler.setNgMethod(getCurrentAcctAppCode);
                request = handler.getHandledRequest(RequestXml.getCurrentAcctRequest(accountNumber));
                logger.info("Current account request: "+request);
                response = handler.getHandledResponse(Api.execute(request));
            }

            else if (accountNumber.startsWith("3")){
                handler.setNgMethod(getSavingAcctAppCode);
                request = handler.getHandledRequest(RequestXml.getSavingAcctRequest(accountNumber));
                logger.info("Saving account request: "+request);
                response = handler.getHandledResponse(Api.execute(request));
            }

                logger.info("fetch account response-- " + response);

                if (Shared.isNotEmpty(response)) {
                    XmlParser xmlParser = new XmlParser(response);
                    String status = xmlParser.getValueOf(apiStatus);

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

                        return result;

                    } else if (isStatusFailed(status)) {
                        String errCode = xmlParser.getValueOf("ErrorCode");
                        String errDesc = xmlParser.getValueOf("ErrorDesc");
                        String errType = xmlParser.getValueOf("ErrorType");
                        logger.info("ErrorType : " + errType + " ErrorCode : " + errCode + " ErrorDesc : " + errDesc + ".");

                        result.put(errorKey,errDesc);
                    }
                }
                else {
                    result.put(errorKey,"No response from fetch account api for this account: "+accountNumber);
                    return result;
                }

        } catch (Exception e){
            String exception = "Exception occurred in fetchAcctDetails method: "+e.getMessage();
            logger.error(exception);
            result.put(errorKey,exception);
            return  result;
        }
        return null;
    }

    private static boolean isStatusSuccess(String status){
        return status.equalsIgnoreCase(apiSuccess);
    }

    private static boolean isStatusFailed(String status){
        return status.equalsIgnoreCase(apiSuccess);
    }

    private static boolean isSuccessFailure(String apiFlag,String compareFlag){
        return apiFlag.equalsIgnoreCase(compareFlag);
    }

}
