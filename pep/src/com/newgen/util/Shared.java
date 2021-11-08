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
	private static final String True = null;
    public static List<List<String>> resultSet;
    public static int validate;
    public static String message;
	

  
  
    public static String getCurrentWorkstep (IFormReference ifr) {
    	return ifr.getActivityName();
    	
    }
    public static boolean isProcessName(IFormReference ifr, String processName) {
    	return ifr.getProcessName().equalsIgnoreCase(processName);
    }
    public static String getProcessName (IFormReference ifr) {
    	return ifr.getProcessName();
    }

    private String getTat (String entryDate, String exitDate){
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
    private static void setDecisionHistory(IFormReference ifr, String staffId, String process, String marketType, String decision,
                                    String remarks, String prevWs, String entryDate, String exitDate, String tat){
        JSONArray jsRowArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(dhRowStaffId,staffId);
        jsonObject.put(dhRowProcess,process);
        jsonObject.put(dhRowMarketType,marketType);
        jsonObject.put(dhRowDecision,decision);
        jsonObject.put(dhRowRemarks,remarks);
        jsonObject.put(dhRowPrevWs,prevWs);
        jsonObject.put(dhRowEntryDate,entryDate);
        jsonObject.put(dhRowExitDate,exitDate);
        jsonObject.put(dhRowTat,tat);
        jsRowArray.add(jsonObject);

        ifr.addDataToGrid(decisionHisTable, jsRowArray);
    }
    public String getUsersMailsInGroup(IFormReference ifr, String groupName){
        StringBuilder groupMail= new StringBuilder();
        try {
            resultSet = new DbConnect(ifr, new Query().getUsersInGroup(groupName)).getData();
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
    public void setDropDown (IFormReference ifr,String local, String [] values){
        ifr.clearCombo(local);
        for (String value: values) ifr.addItemInCombo(local,value,value);
        clearFields(ifr,local);
    }
    public void setDropDown (IFormReference ifr,String local,String [] labels, String [] values){
        ifr.clearCombo(local);
        for (int i = 0; i < values.length; i++) ifr.addItemInCombo(local,labels[i],values[i]);
    }
    public  void setDecision (IFormReference ifr,String decisionLocal, String [] values){
        ifr.clearCombo(decisionLocal);
        for (String value: values) ifr.addItemInCombo(decisionLocal,value,value);
        clearFields(ifr,new String[]{decisionLocal});
    }
    public  void setDecision (IFormReference ifr,String decisionLocal,String [] labels, String [] values){
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
  
    public String getCurrentWorkStep(IFormReference ifr){
        return ifr.getActivityName();
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
}
