package com.newgen.service;

import com.newgen.iforms.custom.IFormReference;
import com.newgen.util.Constants;
import com.newgen.util.Shared;

import java.util.HashMap;
import java.util.List;

public class Service {

    private IFormReference ifr;

    public Service(IFormReference ifr) {
        this.ifr = ifr;
    }

    public String getAccountListTest(){
        Shared.clearTable(ifr,Constants.accountListTable);
        String bvn = Shared.getBvn(ifr);

        if (Shared.isEmpty(bvn)) return "Kindly enter BVN";
        if (isBvnValid(bvn.length())){
            Shared.clearFields(ifr,Constants.bvnLocal);
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

    private boolean isBvnValid(int bvnLength){
        return bvnLength < Constants.bvnLength;
    }
}
