package com.newgen.util;

import com.newgen.iforms.custom.IFormReference;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

public class Shared implements Constants {
    private static final Logger logger = LogGenerator.getLoggerInstance(Shared.class);
    public static List<List<String>> resultSet;
    public static int validate;
    public static String message;

    public static String getBmGroupName(IFormReference ifr){
        return bmGroupLabel + getUserSol(ifr);
    }

    public static String getRmGroupName(IFormReference ifr){
        return rmGroupLabel + getUserSol(ifr);
    }

    public static String getUserSol(IFormReference ifr){
        return getFieldValue(ifr,userSolIdLocal);
    }
    public static void checkBmIsInitiator(IFormReference ifr){
        try {
            clearFields(ifr, bvFlagLocal);
            resultSet = new DbConnect(ifr, Query.getIsUserMemberOfGroup(getLoginUser(ifr), getBmGroupName(ifr))).getData();
            int count = getFormattedNumber(getDataByCoordinates(resultSet, 0, 0));
            if (count > 0) setFields(ifr, bvFlagLocal, flag);
        } catch (Exception e){
            logger.info("Exception occurred in checkBmIsInitiator method: "+e.getMessage());
        }
    }

    public static void setInitiatorDetails(IFormReference ifr){
        try {
            resultSet = new DbConnect(ifr, Query.getUserDetailsQuery(getLoginUser(ifr))).getData();
            String sol = getDataByCoordinates(resultSet, 0, 0);
            String branchName = getDataByCoordinates(resultSet, 0, 1);
            setFields(ifr, new String[]{userSolIdLocal, userBranchNameLocal, userIdLocal}, new String[]{sol, branchName, getLoginUser(ifr)});
        } catch (Exception e){
            logger.info("Exception occurred in setInitiatorDetails method: "+e.getMessage());
        }
    }
    public static void hideSections(IFormReference ifr){
        setInvisible(ifr,new String[]{accountListSection,pepInfoSection,pepVerificationSection,decisionSection,pepCategorySection,generateDocumentSection,pepRepositorySection});
    }
    public static String getCurrentWorkStep(IFormReference ifr) {
    	return ifr.getActivityName();
    }
    public static boolean isProcessName(IFormReference ifr, String processName) {
    	return ifr.getProcessName().equalsIgnoreCase(processName);
    }
    public static String getProcessName (IFormReference ifr) {
    	return ifr.getProcessName();
    }

    private static String getTat (String entryDate, String exitDate){
        SimpleDateFormat sdf = new SimpleDateFormat(dbDateTimeFormat);
        try {
            Date d1 = sdf.parse(entryDate);
            Date d2 = sdf.parse(exitDate);

            long difference_In_Time = d2.getTime() - d1.getTime();
            long difference_In_Seconds = (difference_In_Time / 1000) % 60;
            long difference_In_Minutes = (difference_In_Time / (1000 * 60)) % 60;
            long difference_In_Hours = (difference_In_Time / (1000 * 60 * 60)) % 24;
            long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;
            logger.info("getTat method -- tat-- "+ difference_In_Days + " days, " + difference_In_Hours + " hrs, " + difference_In_Minutes + " mins, " + difference_In_Seconds + " sec");

            return  difference_In_Days + " days, " + difference_In_Hours + " hrs, " + difference_In_Minutes + " min, " + difference_In_Seconds + " sec";
        }
        catch (ParseException e) {
            e.printStackTrace();
            logger.error("Exception occurred in getTat method-- "+ e.getMessage());
        }
        return null;
    }
    public static void setDecisionHistory(IFormReference ifr){
        if (isDecisionHistoryEmpty(ifr)) {
            String tat = getTat(getEntryDate(ifr), getCurrentDateTime());
            setTableGridData(ifr, decisionHistoryTable, new String[]{dhColStaffId, dhColPrevWs, dhColDecision, dhColRemarks, dhColEntryDate, dhColExitDate, dhColTat},
                    new String[]{getLoginUser(ifr), getCurrentWorkStep(ifr), getDecision(ifr), getRemarks(ifr), getEntryDate(ifr), getCurrentDateTime(), tat});
            setFields(ifr,decisionHistoryFlagLocal,flag);
        }
    }
    public String getUsersMailsInGroup(IFormReference ifr, String groupName){
        StringBuilder groupMail= new StringBuilder();
        try {
            resultSet = new DbConnect(ifr, Query.getUsersInGroup(groupName)).getData();
            for (List<String> result : resultSet)
                groupMail.append(result.get(0)).append(endMail).append(",");
        } catch (Exception e){
            logger.error("Exception occurred in getUsersMailInGroup Method-- "+ e.getMessage());
            return null;
        }
        logger.info("getUsersMailsGroup method --mail of users-- "+ groupMail.toString().trim());
        return groupMail.toString().trim();
    }
 
    
    public String getCurrentDateTime (String format){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
    }
    public static String getCurrentDateTime (){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(dbDateTimeFormat));
    }
    public static String  getCurrentDate (){
        return LocalDate.now().toString();
    }
  
    public static String getWorkItemNumber (IFormReference ifr){
        return (String)ifr.getControlValue(wiNameLocal);
    }
    public static String getLoginUser(IFormReference ifr){
        return ifr.getUserName().toUpperCase();
    }
  
    public void hideShow (IFormReference ifr, String[] fields,String state) { for(String field: fields) ifr.setStyle(field,visible,state);}
    public static void setFields (IFormReference ifr, String [] locals,String [] values){
        for (int i = 0; i < locals.length; i++) ifr.setValue(locals[i], values[i]);
    }
    public static void setFields (IFormReference ifr, String local,String value){
        ifr.setValue(local,value);
    }
    public static void setDropDown (IFormReference ifr,String local, String [] values){
        ifr.clearCombo(local);
        for (String value: values) ifr.addItemInCombo(local,value,value);
        clearFields(ifr,local);
    }
    public static void setDropDown (IFormReference ifr,String local,String [] labels, String [] values){
        ifr.clearCombo(local);
        for (int i = 0; i < values.length; i++) ifr.addItemInCombo(local,labels[i],values[i]);
    }
    public static void setDropDown (IFormReference ifr,String local,String label, String value){
       ifr.addItemInCombo(local,label,value);
    }
    public static void setDropDown (IFormReference ifr,String local, String value){
       ifr.addItemInCombo(local,value,value);
    }
    public static void clearDropDown(IFormReference ifr,String local){
        ifr.clearCombo(local);
    }
    public static void  setDecision (IFormReference ifr,String decisionLocal, String [] values){
        ifr.clearCombo(decisionLocal);
        for (String value: values) ifr.addItemInCombo(decisionLocal,value,value);
        clearFields(ifr,new String[]{decisionLocal});
    }
    public static  void setDecision (IFormReference ifr,String decisionLocal,String [] labels, String [] values){
        ifr.clearCombo(decisionLocal);
        for (int i = 0; i < values.length; i++) ifr.addItemInCombo(decisionLocal,labels[i],values[i]);
        clearFields(ifr,new String[]{decisionLocal});
    }
    public void addToDropDown (IFormReference ifr,String local,String [] labels, String [] values){
        for (int i = 0; i < values.length; i++) ifr.addItemInCombo(local,labels[i],values[i]);
    }
    public static void disableFields(IFormReference ifr, String [] fields) { for(String field: fields) ifr.setStyle(field,disable, True); }
    public static void disableFields(IFormReference ifr, String field) { ifr.setStyle(field,disable, True); }
    public static void clearFields(IFormReference ifr, String [] fields) { for(String field: fields) ifr.setValue(field, empty); }
    public static void setVisible(IFormReference ifr, String[] fields) { for(String field: fields) ifr.setStyle(field,visible, True);}
    public static void setInvisible(IFormReference ifr, String [] fields ) { for(String field: fields) ifr.setStyle(field,visible, False); }
    public static void setInvisible(IFormReference ifr, String field ) { ifr.setStyle(field,visible, False); }
    public static void enableFields(IFormReference ifr, String [] fields) {for(String field: fields) ifr.setStyle(field,disable, False);}
    public static void enableFields(IFormReference ifr, String field) {ifr.setStyle(field,disable, False);}
    public static void setMandatory(IFormReference ifr, String []fields) { for(String field: fields) ifr.setStyle(field,mandatory, True);}
    public static void undoMandatory(IFormReference ifr, String [] fields) { for(String field: fields) ifr.setStyle(field,mandatory, False); }
    public static void clearTables(IFormReference ifr, String [] tables){for (String table: tables) ifr.clearTable(table);}
    public static void clearTables(IFormReference ifr, String table){ifr.clearTable(table);}
    public static String getFieldValue(IFormReference ifr, String local){return ifr.getValue(local).toString();}
    public static boolean isEmpty(String s) {return s == null || s.trim().isEmpty();}
    public boolean compareDate(String startDate, String endDate){
      return  LocalDateTime.parse(endDate, DateTimeFormatter.ofPattern(dbDateTimeFormat)).isAfter(LocalDateTime.parse(startDate,DateTimeFormatter.ofPattern(dbDateTimeFormat)));
    }
    public static void setTableGridData(IFormReference ifr, String tableLocal, String [] columns, String [] rowValues){
        JSONArray jsRowArray = new JSONArray();
        jsRowArray.add(setTableRows(columns,rowValues));
        ifr.addDataToGrid(tableLocal,jsRowArray);
    }
    private static JSONObject setTableRows(String [] columns, String [] rowValues){
        JSONObject jsonObject = new JSONObject();
        for (int i = 0 ; i < columns.length; i++)
            jsonObject.put(columns[i],rowValues[i]);

        return jsonObject;
    }
    public static String addToCurrentDate(int tenor){
        return LocalDate.now().plusDays(tenor).toString();
    }
    public static String addToDate(String startDate,int tenor){
        return LocalDate.parse(startDate).plusDays(tenor).toString();
    }
    public static String addToDate(String startDate,int tenor,String dateFormat){
        return LocalDate.parse(startDate,DateTimeFormatter.ofPattern(dateFormat)).plusDays(tenor).toString();
    }
    public static boolean isLeapYear (){
        return LocalDate.now().isLeapYear();
    }
    public static boolean isLeapYear (String date){
            return LocalDate.parse(date).isLeapYear();
    }
 
    public static long getDaysToSetDate(String date){
        return ChronoUnit.DAYS.between(LocalDate.now(),LocalDate.parse(date));
    }
    public static long getDaysBetweenTwoDates(String startDate, String endDate){
        return ChronoUnit.DAYS.between(LocalDate.parse(startDate),LocalDate.parse(endDate));
    }
  
    public static void  clearTable(IFormReference ifr, String tableLocal){
        ifr.clearTable(tableLocal);
    }
 
    public boolean isDateEqual (String date1, String date2){
        return LocalDate.parse(date1).isEqual(LocalDate.parse(date2));
    }
  
    public static boolean isEmpty(List<List<String>> resultSet){
        return  resultSet.isEmpty();
    }
   

    public static void setTableCellValue(IFormReference ifr,String tableName, int rowIndex, int columnIndex,String value){
        ifr.setTableCellValue(tableName,rowIndex,columnIndex,value);
    }
    public static String getTableCellValue(IFormReference ifr,String tableName,int rowIndex, int columnIndex){
        return ifr.getTableCellValue(tableName,rowIndex,columnIndex);
    }
    public static String getFormattedString(float value){
        return String.format("%.2f",value);
    }
    public static String getFormattedString(long value){
        return String.valueOf(value);
    }
    public static String getFormattedString(int value){
        return String.valueOf(value);
    }
    public static  int getFormattedNumber(String data){
        return Integer.parseInt(data);
    }
    public static LocalDate getDate(String date){
        return (!isEmpty(date)) ? LocalDate.parse(date) : null;
    }
    public static LocalDate getDate(String date,String format){
        return (!(isEmpty(date) && isEmpty(format))) ? LocalDate.parse(date,DateTimeFormatter.ofPattern(format)) : null;
    }

    public static LocalDateTime getDateTime(String date){
        return (!isEmpty(date)) ? LocalDateTime.parse(date) : null;
    }
    public static LocalDateTime getDateTime(String date,String format){
        return (!(isEmpty(date) && isEmpty(format))) ? LocalDateTime.parse(date,DateTimeFormatter.ofPattern(format)) : null;
    }
    public static void clearFields(IFormReference ifr, String field) {ifr.setValue(field,empty);}
    public static void setVisible(IFormReference ifr, String field) { ifr.setStyle(field,visible,True);}
    public static void hideFields(IFormReference ifr, String [] fields ) { for(String field: fields) ifr.setStyle(field,visible,False); }
    public static void setMandatory(IFormReference ifr, String field) { ifr.setStyle(field,mandatory,True); }
    public static void undoMandatory(IFormReference ifr, String field) { ifr.setStyle(field,mandatory,False); }
    public static void setWiName(IFormReference ifr){
	    setFields(ifr,wiNameFormLocal,getWorkItemNumber(ifr));
	}
    public static boolean isSaved(int value){
        return value > 0;
    }
    public static String getDataByCoordinates(List<List<String>> resultSet, int row, int column){
       return  resultSet.get(row).get(column);
    }
    public static boolean isNotEmpty(String value){
        return !isEmpty(value);
    }
    public static boolean isNotEmpty(List<List<String>> resultSet){
        return !isEmpty(resultSet);
    }
    public static String getBvn(IFormReference ifr){
        return getFieldValue(ifr,bvnLocal);
    }
    public static  String getPrevWs (IFormReference ifr){
        return  getFieldValue(ifr,previousWsLocal);
    }

    public static boolean isPrevWs(IFormReference ifr, String workStep){
        return getPrevWs(ifr).equalsIgnoreCase(workStep);
    }
    public static boolean isCurrWs(IFormReference ifr, String workStep){
        return getCurrentWorkStep(ifr).equalsIgnoreCase(workStep);
    }

    public static boolean isDecisionApprove(IFormReference ifr){
        return getDecision(ifr).equalsIgnoreCase(decApprove);
    }
    public static boolean isDecisionSubmit(IFormReference ifr){
        return getDecision(ifr).equalsIgnoreCase(decSubmit);
    }
    public static boolean isDecisionReturn(IFormReference ifr){
        return getDecision(ifr).equalsIgnoreCase(decReturn);
    }
    public static String getDecision(IFormReference ifr){
        return getFieldValue(ifr,decisionLocal);
    }
    public static void checkSol(IFormReference ifr){
        try{
            if (isNotMemberOfSol(ifr)) hideSections(ifr);
        }
        catch (Exception e){
            hideSections(ifr);
            logger.error("Exception occurred check database details for user: "+ e.getMessage());
        }
    }
    public static  String getLineExecutive(IFormReference ifr){
        return getFieldValue(ifr, lineExecutiveLocal);
    }

    public static  boolean isLineExecutive(IFormReference ifr, String executive){
        return  getLineExecutive(ifr).equalsIgnoreCase(executive);
    }

    public static void loadLineExecutive(IFormReference ifr){
        try {
            if (isCurrWs(ifr,branchInitiatorWs)) clearDropDown(ifr, lineExecutiveLocal);
            resultSet = new DbConnect(ifr, Query.getLineExecutives()).getData();
            for (List<String> result : resultSet) {
                String lineExecName = result.get(0);
                setDropDown(ifr, lineExecutiveLocal,lineExecName);
            }
        }catch (Exception e){
            logger.info("Exception occurred in Loading Line Executive Method: "+ e.getMessage());
        }
    }
    public static void setLineExecutiveFilter(IFormReference ifr){
        try {
            clearFields(ifr, lineExecFilterLocal);
            resultSet = new DbConnect(ifr, Query.getLineExecutivesId(getLineExecutive(ifr))).getData();
            setFields(ifr, lineExecFilterLocal, getDataByCoordinates(resultSet, 0, 0));
        }catch (Exception e){
            logger.error("Exception occurred in setting line executive filter: "+e.getMessage());
        }
    }

    public static void setAcoFilter(IFormReference ifr){
        try {
            clearFields(ifr, acoFilterLocal);
            resultSet = new DbConnect(ifr, Query.getAcoId(getUserSol(ifr))).getData();
            setFields(ifr, acoFilterLocal, getDataByCoordinates(resultSet, 0, 0));
        } catch (Exception e){
            logger.info("Exception occurred in setting aco filter method: "+e.getMessage());
        }
    }

    public static  String getAccountType(IFormReference ifr){
        return getFieldValue(ifr,pepAccountTypeLocal);
    }
    public static boolean isAccountType(IFormReference ifr, String accountType){
        return getAccountType(ifr).equalsIgnoreCase(accountType);
    }
    public static void  showAccountTypeOthersField(IFormReference ifr){
       if ( isAccountType(ifr,accountTypeOthers) ) {
           setVisible(ifr,otherAcctTypeLocal);
           setMandatory(ifr,otherAcctTypeLocal);
       }
       else {
           clearFields(ifr,otherAcctTypeLocal);
           setInvisible(ifr,otherAcctTypeLocal);
       }
    }

    public static String getRemarks(IFormReference ifr){
        return getFieldValue(ifr,remarksLocal);
    }

    public static boolean isNotMemberOfSol (IFormReference ifr){
        return Integer.parseInt(new DbConnect(ifr,Query.getIsMemberOfSol(getLoginUser(ifr), getUserSol(ifr))).getData().get(0).get(0)) <= 0;
    }
    public static String getEntryDate(IFormReference ifr){
        return  getFieldValue(ifr,entryDateLocal);
    }
    public static boolean isDecisionHistoryEmpty(IFormReference ifr){
        return isEmpty(getDecisionHistoryFlag(ifr));
    }

    private static String getDecisionHistoryFlag(IFormReference ifr){
        return getFieldValue(ifr,decisionHistoryFlagLocal);
    }
    public static String getPepCategory(IFormReference ifr){
        return getFieldValue(ifr,pepCategoryLocal);
    }
    public static String getPepAccountCategory(IFormReference ifr){
        return getFieldValue(ifr,pepAccountCategoryLocal);
    }

    public static boolean isPepCategory(IFormReference ifr, String category){
        return getPepCategory(ifr).equalsIgnoreCase(category);
    }

    public static boolean isPepAcctCategory(IFormReference ifr, String category){
        return getPepAccountCategory(ifr).equalsIgnoreCase(category);
    }

    public static String setPepMandatoryInfoFields(IFormReference ifr){

        if (isEmpty(getPepCategory(ifr))) return "Kindly Choose Pep Category";

        if (isEmpty(getPepAccountCategory(ifr))) return "Kindly Choose Pep Account Category";

        enableFields(ifr, new String[]{surNameLocal,firstNameLocal,otherNameLocal,addressLocal,pepSolIdLocal,pepBranchNameLocal,pepStatusLocal,srcOfWealthLocal,purposeOfAccountLocal,officeDesignationLocal,pepAccountTypeLocal,isDocCompletedLocal,isLinkedPepLocal});
        setMandatory(ifr, new String[]{surNameLocal,firstNameLocal,addressLocal,pepSolIdLocal,pepBranchNameLocal,pepStatusLocal,srcOfWealthLocal,purposeOfAccountLocal,officeDesignationLocal,pepAccountTypeLocal,isDocCompletedLocal,isLinkedPepLocal});

        if (isPepCategory(ifr,pepCategoryExisting)){
            enableFields(ifr,new String[]{accountNoLocal,accountOpeningDateLocal});
            setMandatory(ifr,new String[]{accountNoLocal,accountOpeningDateLocal});
        }
        else {
            clearFields(ifr,new String[]{accountNoLocal,accountOpeningDateLocal});
            disableFields(ifr,new String[]{accountNoLocal,accountOpeningDateLocal});
            undoMandatory(ifr,new String[]{accountNoLocal,accountOpeningDateLocal});
        }

        if (isPepAcctCategory(ifr,pepAcctCategoryCorporate)){
            enableFields(ifr,new String[]{tinLocal});
            setMandatory(ifr,new String[]{tinLocal});
        }
        else {
            disableFields(ifr,new String[]{tinLocal});
            clearFields(ifr,new String[]{tinLocal});
            undoMandatory(ifr,new String[]{tinLocal});
        }

        return  null;
    }

    public static String getIsLinkedPep(IFormReference ifr){
        return getFieldValue(ifr,isLinkedPepLocal);
    }

    public static void linkedPep(IFormReference ifr){
        if (isLinkedPep(ifr)){
            setVisible(ifr,new String[]{linkedPepLocal});
            enableFields(ifr,new String[]{linkedPepLocal});
            setMandatory(ifr,new String[]{linkedPepLocal});
        }
        else {
            setInvisible(ifr,new String[]{linkedPepLocal});
            undoMandatory(ifr,new String[]{linkedPepLocal});
            clearFields(ifr,new String[]{linkedPepLocal});
        }
    }
    public static boolean isLinkedPep(IFormReference ifr){
        return getIsLinkedPep(ifr).equalsIgnoreCase(yes);
    }

    public static void checkPepVerification(IFormReference ifr){
        if (isLinkedPep(ifr)) setVisible(ifr,linkedPepLocal);

        if (isAccountType(ifr,accountTypeOthers)) setVisible(ifr,accountTypeOthers);
    }

    public static void setDecisionApprove(IFormReference ifr){
        setFields(ifr,decisionLocal,decApprove);
    }
    public  static String getSessionId(IFormReference ifr){
        return ifr.getObjGeneralData().getM_strDMSSessionId();
    }

    public static String getDocFlag(IFormReference ifr){
        return getFieldValue(ifr,docFlagLocal);
    }
    public static boolean isDocGenerated(IFormReference ifr){
        return getDocFlag(ifr).equalsIgnoreCase(flag);
    }
    public static void setDocFlag(IFormReference ifr){
        setFields(ifr,docFlagLocal,flag);
    }

    public static String checkDocGenerated(IFormReference ifr){
        if (!isDocGenerated(ifr)) return "Kindly generate Pep On-boarding Document";
        return null;
    }
    public static void setRepoView(IFormReference ifr){
        setVisible(ifr,new String[]{accountListSection,pepRepositorySection,decisionSection});
        setInvisible(ifr, new String[]{lineExecutiveLocal});
        enableFields(ifr, new String[]{bvnLocal,searchBvnBtn,repoAcctNoLocal,decisionLocal,remarksLocal});
        setMandatory(ifr, new String[]{bvnLocal,repoAcctNoLocal,decisionLocal,remarksLocal});

        if (isCurrWs(ifr,amlWs)) disableFields(ifr, new String[]{bvnLocal,searchBvnBtn,repoAcctNoLocal});
    }

    public static String getRepoAcctNo (IFormReference ifr){
        return getFieldValue(ifr,repoAcctNoLocal);
    }
    public static String setRepoInfo(IFormReference ifr){
        try {

            String acctNo = getRepoAcctNo(ifr);

            if (isEmpty(acctNo)) return "Kindly enter pep Account Number";

            resultSet = new DbConnect(ifr, Query.getPepRepoDetails(acctNo)).getData();

            if (isEmpty(resultSet)) return "No record found for this pep account number";

            for (List<String> result : resultSet) {
                String sol = result.get(0);
                String branchName = result.get(1);
                String accountName = result.get(2);
                String pepName = result.get(3);
                String address = result.get(4);
                String position = result.get(5);
                String natureOfBusiness = result.get(6);
                String acctOpenDate = result.get(7);

                setFields(ifr, new String[]{repoAcctNameLocal, repoAddressLocal, repoAcctOpenDateLocal, repoBranchNameLocal, repoSolIdLocal, repoNOfBusiness, repoPepNameLocal, repoPositionLocal},
                        new String[]{accountName, address, acctOpenDate, branchName, sol, natureOfBusiness, pepName, position});
            }
        }catch (Exception e){
            logger.info("Exception Occurred in setRepInfo Method:  "+e.getMessage());
        }

        return null;
    }



}
