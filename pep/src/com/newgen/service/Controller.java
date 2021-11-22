package com.newgen.service;

import com.newgen.api.generateXml.CallClientRequestHandler;
import com.newgen.api.generateXml.RequestXml;
import com.newgen.api.service.Api;
import com.newgen.util.Constants;
import com.newgen.util.LogGenerator;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Controller {

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

    public static String getAccountLinkedToBvn(String bvn,String wiName){
        String request = new CallClientRequestHandler(Constants.pepProcessName,Constants.getBvnAcctListAppCode,wiName,Constants.callTypeFinacle,Constants.endpointCustomFIFinacle).getCallClientRequest(RequestXml.getBvnLinkAcctRequest(bvn));
        logger.info("getAccountLinkedToBvn request: "+request);
        String output = Api.execute(request);
        logger.info("getAccountLinkedToBvn output: "+output);


        return null;
    }

}
