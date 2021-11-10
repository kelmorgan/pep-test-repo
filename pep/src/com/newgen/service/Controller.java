package com.newgen.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Controller {


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

}
