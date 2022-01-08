package com.newgen.api.serviceHandler;

import com.kelmorgan.ibpsformapis.apis.FormApi;
import com.newgen.api.controllers.AccountDetailsController;
import com.newgen.api.controllers.AccountLinkedToBvnController;
import com.newgen.api.controllers.TestController;
import com.newgen.api.generateXml.RequestXml;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.util.Constants;
import com.newgen.util.LogGenerator;
import com.newgen.util.Shared;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Service implements Constants {
    private final Logger logger = LogGenerator.getLoggerInstance(Service.class);

    private final IFormReference ifr;
    private final String processName;
    private final String wiName;

    public Service(IFormReference ifr) {
        this.ifr = ifr;
        this.processName = FormApi.getProcessName(ifr);
        this.wiName = FormApi.getWorkItemNumber(ifr);
    }

    public String getAccountListTest() {
        FormApi.clearTable(ifr, accountListTable);
        String bvn = Shared.getBvn(ifr);

        if (Shared.isEmpty(bvn)) return "Kindly enter BVN";
        if (isBvnValid(bvn.length())) {
            FormApi.clearFields(ifr, bvnLocal);
            return "BVN must be 11 digits";
        }

        List<HashMap<String, String>> accountList = TestController.getAccountListTest();

        for (HashMap<String, String> row : accountList) {
            String accountNumber = row.get("accountNumber");
            String accountName = row.get("accountName");
            String solId = row.get("solId");
            String branchName = row.get("branchName");

            FormApi.setTableGridData(ifr, Constants.accountListTable,
                    new String[]{Constants.alColAccountNo, Constants.alColAccountName, Constants.alColSolId, Constants.alColBranchName},
                    new String[]{accountNumber, accountName, solId, branchName});
        }
        return null;
    }

    public String getAccountList() {
        try {
            FormApi.clearTable(ifr, accountListTable);
            String bvn = Shared.getBvn(ifr);
            if (Shared.isEmpty(bvn)) return "Kindly enter BVN";
            if (isBvnValid(bvn.length())) {
                FormApi.clearFields(ifr, bvnLocal);
                return "BVN must be 11 digits";
            }
            AccountLinkedToBvnController accountLinkedToBvnController =
                    new AccountLinkedToBvnController(processName, getBvnAcctListAppCode,
                            wiName, callTypeFinacle, endpointCustomFIFinacle, RequestXml.getBvnLinkAcctRequest(bvn));

            Map<String, Object> bvnData = accountLinkedToBvnController.getAccountList();

            if (!bvnData.isEmpty()) {
                if (bvnData.containsKey(errorKey)) return bvnData.get(errorKey).toString();

                List<String> accountList = (List<String>) bvnData.get(successKey);

                logger.info("Account list: " + accountList);

                accountList.forEach(accountNumber -> {
                    try {
                        AccountDetailsController controller = getAccountDetailsController(accountNumber);
                        assert controller != null;
                        Map<String, String> accountDetails = controller.getAccountDetails();

                        if (!accountDetails.containsKey(errorKey)) {
                            String accountName = accountDetails.get("name");
                            String sol = accountDetails.get("sol");
                            String branchName = accountDetails.get("branchName");

                            FormApi.setTableGridData(ifr, accountListTable,
                                    new String[]{alColAccountNo, alColAccountName, alColSolId, alColBranchName},
                                    new String[]{accountNumber, accountName, sol, branchName});
                        }
                    } catch (Exception e) {
                        logger.error("Exception occurred getAccountList method Iterating through account List: " + e.getMessage());
                    }
                });
            }
        } catch (Exception e) {
            logger.error("Exception occurred getAccountList method: " + e.getMessage());
        }

        return null;
    }
    public String getAccountDetails(){
        try{
            if (Shared.isPepCategory(ifr, pepCategoryExisting)) {

                String accountNumber = Shared.getPepAccount(ifr);

                if (accountNumber.isEmpty()) return "Kindly enter Account Number";

                AccountDetailsController controller = getAccountDetailsController(accountNumber);
                assert controller != null;
                Map<String, String> accountDetails = controller.getAccountDetails();

                if (!accountDetails.isEmpty()) {
                    if (accountDetails.containsKey(errorKey)) return accountDetails.get(errorKey);

                    logger.info("account Number : "+ accountNumber);
                    String name = accountDetails.get("name");
                    String mapDate = accountDetails.get("acctDate");
                    logger.info("mapDate: "+mapDate);
                    String []  dateArray = mapDate.split("T");
                    String  date = dateArray[0];
                    logger.info("date: "+date);

                    String formattedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(dbDateFormat)).toString();
                    logger.info("formatted date: "+formattedDate);

                    FormApi.setFields(ifr,new String[]{pepNameLocal,accountOpeningDateLocal},new String[]{name,formattedDate});
                }
            }
        }
        catch (Exception e){
            logger.error("Exception occurred get account details method: " + e.getMessage());
            return "Exception occurred in getting account details : Contact iBPS Support";
        }
        return null;
    }

    private boolean isBvnValid(int bvnLength) {
        return bvnLength < Constants.bvnLength;
    }

    private AccountDetailsController getAccountDetailsController(String accountNumber) {
        if (accountNumber.startsWith("1")) {
            return new AccountDetailsController(processName, wiName, callTypeFinacle, getSpecialAcctAppcode, endpointBpmFinacle, RequestXml.getSpecialAcctRequest(accountNumber));
        } else if (accountNumber.startsWith("2")) {
            return new AccountDetailsController(processName, wiName, callTypeFinacle, getCurrentAcctAppCode, endpointCustomFIFinacle, RequestXml.getCurrentAcctRequest(accountNumber));
        } else if (accountNumber.startsWith("3")) {
            return new AccountDetailsController(processName, wiName, callTypeFinacle, getSavingAcctAppCode, endpointCustomFIFinacle, RequestXml.getSavingAcctRequest(accountNumber));
        }
        return null;
    }
}
