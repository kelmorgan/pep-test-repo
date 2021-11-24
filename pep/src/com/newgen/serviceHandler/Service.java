package com.newgen.serviceHandler;

import com.newgen.iforms.custom.IFormReference;
import com.newgen.util.Constants;
import com.newgen.util.LogGenerator;
import com.newgen.util.Shared;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;

public class Service implements Constants{
    private final Logger logger = LogGenerator.getLoggerInstance(Service.class);

    private IFormReference ifr;

    public Service(IFormReference ifr) {
        this.ifr = ifr;
    }

    public String getAccountListTest(){
        Shared.clearTable(ifr,accountListTable);
        String bvn = Shared.getBvn(ifr);

        if (Shared.isEmpty(bvn)) return "Kindly enter BVN";
        if (isBvnValid(bvn.length())){
            Shared.clearFields(ifr,bvnLocal);
            return "BVN must be 11 digits";
        }

        List<HashMap<String,String>> accountList = Controller.getAccountListTest();

        for (HashMap<String, String> row : accountList){
            String accountNumber = row.get("accountNumber");
            String accountName = row.get("accountName");
            String solId = row.get("solId");
            String branchName = row.get("branchName");

            Shared.setTableGridData(ifr,Constants.accountListTable,
                    new String[]{Constants.alColAccountNo,Constants.alColAccountName,Constants.alColSolId,Constants.alColBranchName},
                    new String[]{accountNumber,accountName,solId,branchName});
        }
        return null;
    }

    public String getAccountList(){
        Shared.clearTable(ifr,accountListTable);
        String bvn = Shared.getBvn(ifr);
        String wiName = Shared.getWorkItemNumber(ifr);

        if (Shared.isEmpty(bvn)) return "Kindly enter BVN";
        if (isBvnValid(bvn.length())){
            Shared.clearFields(ifr,bvnLocal);
            return "BVN must be 11 digits";
        }

      HashMap<String,Object> bvnData = Controller.getAccountLinkedToBvn(bvn,wiName);

       if (Shared.isNotEmpty(bvnData)) {

           if (bvnData.containsKey("error")) return bvnData.get("error").toString();

           List<String> accountList = (List<String>) bvnData.get("success");

           logger.info("Account list: "+accountList);


           for (String accountNumber : accountList){

               HashMap<String,String > accountData = Controller.fetchAcctDetails(accountNumber,wiName);

               assert accountData != null;
               if(!accountData.containsKey(errorKey)){
                   String accountName = accountData.get("name");
                   String sol = accountData.get("sol");
                   String branchName = accountData.get("branchName");

                   Shared.setTableGridData(ifr,accountListTable,
                           new String[]{alColAccountNo,alColAccountName,alColSolId,alColBranchName},
                           new String[]{accountNumber,accountName,sol,branchName});
               }
           }

       }

        return null;
    }

    private boolean isBvnValid(int bvnLength){
        return bvnLength < Constants.bvnLength;
    }
}
