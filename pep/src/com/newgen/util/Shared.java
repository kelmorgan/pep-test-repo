package com.newgen.util;


import com.kelmorgan.ibpsformapis.apis.FormApi;
import com.newgen.iforms.custom.IFormReference;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Shared implements Constants {
    private static final Logger logger = LogGenerator.getLoggerInstance(Shared.class);
    public static List<List<String>> resultSet;
    public static int validate;
    public static String message;

    public static String getBmGroupName(IFormReference ifr) {
        return bmGroupLabel + getUserSol(ifr);
    }

    public static String getRmGroupName(IFormReference ifr) {
        return rmGroupLabel + getUserSol(ifr);
    }

    public static String getUserSol(IFormReference ifr) {
        return FormApi.getFieldValue(ifr, userSolIdLocal);
    }

    public static void checkBmIsInitiator(IFormReference ifr) {
        try {
            FormApi.clearFields(ifr, bvFlagLocal);
            resultSet = new DbConnect(ifr, Query.getIsUserMemberOfGroup(FormApi.getLoginUser(ifr), getBmGroupName(ifr))).getData();
            int count = getFormattedNumber(getDataByCoordinates(resultSet, 0, 0));
            if (count > 0) FormApi.setFields(ifr, bvFlagLocal, flagY);
        } catch (Exception e) {
            logger.info("Exception occurred in checkBmIsInitiator method: " + e.getMessage());
        }
    }

    public static void setInitiatorDetails(IFormReference ifr) {
        try {
            resultSet = new DbConnect(ifr, Query.getUserDetailsQuery(FormApi.getLoginUser(ifr))).getData();
            String sol = getDataByCoordinates(resultSet, 0, 0);
            String branchName = getDataByCoordinates(resultSet, 0, 1);
            FormApi.setFields(ifr, new String[]{userSolIdLocal, userBranchNameLocal, userIdLocal}, new String[]{sol, branchName, FormApi.getLoginUser(ifr)});
        } catch (Exception e) {
            logger.info("Exception occurred in setInitiatorDetails method: " + e.getMessage());
        }
    }

    public static void hideSections(IFormReference ifr) {
        FormApi.setInvisible(ifr, new String[]{accountListSection, pepInfoSection, pepVerificationSection, decisionSection, pepCategorySection, generateDocumentSection, pepRepositorySection});
    }

    public static boolean isProcessName(IFormReference ifr, String processName) {
        return FormApi.getProcessName(ifr).equalsIgnoreCase(processName);
    }

    private static String getTat(String entryDate, String exitDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(dbDateTimeFormat);
        try {
            Date d1 = sdf.parse(entryDate);
            Date d2 = sdf.parse(exitDate);

            long difference_In_Time = d2.getTime() - d1.getTime();
            long difference_In_Seconds = (difference_In_Time / 1000) % 60;
            long difference_In_Minutes = (difference_In_Time / (1000 * 60)) % 60;
            long difference_In_Hours = (difference_In_Time / (1000 * 60 * 60)) % 24;
            long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;
            logger.info("getTat method -- tat-- " + difference_In_Days + " days, " + difference_In_Hours + " hrs, " + difference_In_Minutes + " mins, " + difference_In_Seconds + " sec");

            return difference_In_Days + " days, " + difference_In_Hours + " hrs, " + difference_In_Minutes + " min, " + difference_In_Seconds + " sec";
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("Exception occurred in getTat method-- " + e.getMessage());
        }
        return null;
    }

    public static void setDecisionHistory(IFormReference ifr) {
        if (isDecisionHistoryEmpty(ifr)) {
            String tat = getTat(getEntryDate(ifr), getCurrentDateTime());
            FormApi.setTableGridData(ifr, decisionHistoryTable, new String[]{dhColStaffId, dhColPrevWs, dhColDecision, dhColRemarks, dhColEntryDate, dhColExitDate, dhColTat},
                    new String[]{FormApi.getLoginUser(ifr), FormApi.getCurrentWorkStep(ifr), getDecision(ifr), getRemarks(ifr), getEntryDate(ifr), getCurrentDateTime(), tat});
            FormApi.setFields(ifr, decisionHistoryFlagLocal, flagY);
        }
    }

    public static String getUsersMailsInGroup(IFormReference ifr, String groupName) {
        StringBuilder groupMail = new StringBuilder();
        try {
            resultSet = new DbConnect(ifr, Query.getUsersInGroup(groupName)).getData();
            for (List<String> result : resultSet)
                groupMail.append(result.get(0)).append(endMail).append(",");
        } catch (Exception e) {
            logger.error("Exception occurred in getUsersMailInGroup Method-- " + e.getMessage());
            return null;
        }
        logger.info("getUsersMailsGroup method --mail of users-- " + groupMail.toString().trim());
        return groupMail.toString().trim();
    }


    public static String getCurrentDateTime(String format) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
    }

    public static String getCurrentDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(dbDateTimeFormat));
    }

    public static String getCurrentDate() {
        return LocalDate.now().toString();
    }

    public static void setDecision(IFormReference ifr, String decisionLocal, String[] values) {
        FormApi.setDropDown(ifr, decisionLocal, values);
        FormApi.clearFields(ifr, decisionLocal);
    }

    public static void setDecision(IFormReference ifr, String decisionLocal, String[] labels, String[] values) {
        FormApi.setDropDown(ifr, decisionLocal, labels, values);
        FormApi.clearFields(ifr, decisionLocal);
    }

    public static boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    public static boolean isEmpty(Map<String, Object> map) {
        return map.isEmpty();
    }

    public static boolean isDecisionDiscard(IFormReference ifr) {
        return getDecision(ifr).equalsIgnoreCase(decDiscard);
    }

    public boolean compareDate(String startDate, String endDate) {
        return LocalDateTime.parse(endDate, DateTimeFormatter.ofPattern(dbDateTimeFormat)).isAfter(LocalDateTime.parse(startDate, DateTimeFormatter.ofPattern(dbDateTimeFormat)));
    }

    public static boolean isEmpty(List<List<String>> resultSet) {
        return resultSet.isEmpty();
    }

    public static String getFormattedString(float value) {
        return String.format("%.2f", value);
    }

    public static String getFormattedString(long value) {
        return String.valueOf(value);
    }

    public static String getFormattedString(int value) {
        return String.valueOf(value);
    }

    public static int getFormattedNumber(String data) {
        return Integer.parseInt(data);
    }

    public static LocalDate getDate(String date) {
        return (!isEmpty(date)) ? LocalDate.parse(date) : null;
    }

    public static LocalDate getDate(String date, String format) {
        return (!(isEmpty(date) && isEmpty(format))) ? LocalDate.parse(date, DateTimeFormatter.ofPattern(format)) : null;
    }

    public static LocalDateTime getDateTime(String date) {
        return (!isEmpty(date)) ? LocalDateTime.parse(date) : null;
    }

    public static LocalDateTime getDateTime(String date, String format) {
        return (!(isEmpty(date) && isEmpty(format))) ? LocalDateTime.parse(date, DateTimeFormatter.ofPattern(format)) : null;
    }

    public static void setWiName(IFormReference ifr) {
        FormApi.setFields(ifr, wiNameFormLocal, FormApi.getWorkItemNumber(ifr));
    }

    public static boolean isSaved(int value) {
        return value > 0;
    }

    public static String getDataByCoordinates(List<List<String>> resultSet, int row, int column) {
        return resultSet.get(row).get(column);
    }

    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

    public static boolean isNotEmpty(Map<String, Object> map) {
        return !isEmpty(map);
    }

    public static boolean isNotEmpty(List<List<String>> resultSet) {
        return !isEmpty(resultSet);
    }

    public static String getBvn(IFormReference ifr) {
        return FormApi.getFieldValue(ifr, bvnLocal);
    }

    public static String getPrevWs(IFormReference ifr) {
        return FormApi.getFieldValue(ifr, previousWsLocal);
    }

    public static boolean isPrevWs(IFormReference ifr, String workStep) {
        return getPrevWs(ifr).equalsIgnoreCase(workStep);
    }

    public static boolean isCurrWs(IFormReference ifr, String workStep) {
        return FormApi.getCurrentWorkStep(ifr).equalsIgnoreCase(workStep);
    }

    public static boolean isDecisionApprove(IFormReference ifr) {
        return getDecision(ifr).equalsIgnoreCase(decApprove);
    }

    public static boolean isDecisionSubmit(IFormReference ifr) {
        return getDecision(ifr).equalsIgnoreCase(decSubmit);
    }

    public static boolean isDecisionReturn(IFormReference ifr) {
        return getDecision(ifr).equalsIgnoreCase(decReturn);
    }

    public static String getDecision(IFormReference ifr) {
        return FormApi.getFieldValue(ifr, decisionLocal);
    }

    public static void checkSol(IFormReference ifr) {
        try {
            if (isNotMemberOfSol(ifr)) hideSections(ifr);
        } catch (Exception e) {
            hideSections(ifr);
            logger.error("Exception occurred check database details for user: " + e.getMessage());
        }
    }

    public static String getLineExecutive(IFormReference ifr) {
        return FormApi.getFieldValue(ifr, lineExecutiveLocal);
    }

    public static boolean isLineExecutive(IFormReference ifr, String executive) {
        return getLineExecutive(ifr).equalsIgnoreCase(executive);
    }

    public static void loadLineExecutive(IFormReference ifr) {
        try {
            if (isCurrWs(ifr, branchInitiatorWs)) FormApi.clearDropDown(ifr, lineExecutiveLocal);
            resultSet = new DbConnect(ifr, Query.getLineExecutives()).getData();
            resultSet.forEach(result -> {
                String lineExecName = result.get(0);
                FormApi.addToDropDown(ifr, lineExecutiveLocal, lineExecName);
            });
        } catch (Exception e) {
            logger.info("Exception occurred in Loading Line Executive Method: " + e.getMessage());
        }
    }

    public static void setLineExecutiveFilter(IFormReference ifr) {
        try {
            FormApi.clearFields(ifr, lineExecFilterLocal);
            resultSet = new DbConnect(ifr, Query.getLineExecutivesId(getLineExecutive(ifr))).getData();
            FormApi.setFields(ifr, lineExecFilterLocal, getDataByCoordinates(resultSet, 0, 0));
        } catch (Exception e) {
            logger.error("Exception occurred in setting line executive filter: " + e.getMessage());
        }
    }

    public static void setAcoFilter(IFormReference ifr) {
        try {
            FormApi.clearFields(ifr, acoFilterLocal);
            resultSet = new DbConnect(ifr, Query.getAcoId(getUserSol(ifr))).getData();
            FormApi.setFields(ifr, acoFilterLocal, getDataByCoordinates(resultSet, 0, 0));
        } catch (Exception e) {
            logger.info("Exception occurred in setting aco filter method: " + e.getMessage());
        }
    }

    public static String getAccountType(IFormReference ifr) {
        return FormApi.getFieldValue(ifr, pepAccountTypeLocal);
    }

    public static boolean isAccountType(IFormReference ifr, String accountType) {
        return getAccountType(ifr).equalsIgnoreCase(accountType);
    }

    public static void showAccountTypeOthersField(IFormReference ifr) {
        if (isAccountType(ifr, accountTypeOthers)) {
            FormApi.setVisible(ifr, otherAcctTypeLocal);
            FormApi.setMandatory(ifr, otherAcctTypeLocal);
        } else {
            FormApi.clearFields(ifr, otherAcctTypeLocal);
            FormApi.setInvisible(ifr, otherAcctTypeLocal);
        }
    }

    public static String getRemarks(IFormReference ifr) {
        return FormApi.getFieldValue(ifr, remarksLocal);
    }

    public static boolean isNotMemberOfSol(IFormReference ifr) {
        return Integer.parseInt(new DbConnect(ifr, Query.getIsMemberOfSol(FormApi.getLoginUser(ifr), getUserSol(ifr))).getData().get(0).get(0)) <= 0;
    }

    public static String getEntryDate(IFormReference ifr) {
        return FormApi.getFieldValue(ifr, entryDateLocal);
    }

    public static boolean isDecisionHistoryEmpty(IFormReference ifr) {
        return isEmpty(getDecisionHistoryFlag(ifr));
    }

    private static String getDecisionHistoryFlag(IFormReference ifr) {
        return FormApi.getFieldValue(ifr, decisionHistoryFlagLocal);
    }

    public static String getPepCategory(IFormReference ifr) {
        return FormApi.getFieldValue(ifr, pepCategoryLocal);
    }

    public static String getPepAccountCategory(IFormReference ifr) {
        return FormApi.getFieldValue(ifr, pepAccountCategoryLocal);
    }

    public static boolean isPepCategory(IFormReference ifr, String category) {
        return getPepCategory(ifr).equalsIgnoreCase(category);
    }

    public static boolean isPepAcctCategory(IFormReference ifr, String category) {
        return getPepAccountCategory(ifr).equalsIgnoreCase(category);
    }

    public static String setPepMandatoryInfoFields(IFormReference ifr) {

        if (isEmpty(getPepCategory(ifr))) return "Kindly Choose Pep Category";

        if (isEmpty(getPepAccountCategory(ifr))) return "Kindly Choose Pep Account Category";

        FormApi.enableFields(ifr, new String[]{surNameLocal, firstNameLocal, otherNameLocal, addressLocal, pepSolIdLocal, pepBranchNameLocal, pepStatusLocal, srcOfWealthLocal, purposeOfAccountLocal, officeDesignationLocal, pepAccountTypeLocal, isDocCompletedLocal, isLinkedPepLocal});
        FormApi.setMandatory(ifr, new String[]{surNameLocal, firstNameLocal, addressLocal, pepSolIdLocal, pepBranchNameLocal, pepStatusLocal, srcOfWealthLocal, purposeOfAccountLocal, officeDesignationLocal, pepAccountTypeLocal, isDocCompletedLocal, isLinkedPepLocal});
        FormApi.setVisible(ifr,new String[]{pepInfoSection,pepVerificationSection});

        if (isPepCategory(ifr, pepCategoryExisting)) {
            FormApi.enableFields(ifr, new String[]{accountNoLocal, accountOpeningDateLocal});
            FormApi.setMandatory(ifr, new String[]{accountNoLocal, accountOpeningDateLocal});
            FormApi.setInvisible(ifr, new String[]{surNameLocal, firstNameLocal, otherNameLocal});
            FormApi.setVisible(ifr, new String[]{pepNameLocal});
        } else {
            FormApi.clearFields(ifr, new String[]{accountNoLocal, accountOpeningDateLocal});
            FormApi.disableFields(ifr, new String[]{accountNoLocal, accountOpeningDateLocal});
            FormApi.undoMandatory(ifr, new String[]{accountNoLocal, accountOpeningDateLocal});
        }

        if (isPepAcctCategory(ifr, pepAcctCategoryCorporate)) {
            FormApi.enableFields(ifr, new String[]{tinLocal});
            FormApi.setMandatory(ifr, new String[]{tinLocal});
        } else {
            FormApi.disableFields(ifr, new String[]{tinLocal});
            FormApi.clearFields(ifr, new String[]{tinLocal});
            FormApi.undoMandatory(ifr, new String[]{tinLocal});
        }

        return null;
    }

    public static String getIsLinkedPep(IFormReference ifr) {
        return FormApi.getFieldValue(ifr, isLinkedPepLocal);
    }

    public static void linkedPep(IFormReference ifr) {
        if (isLinkedPep(ifr)) {
            FormApi.setVisible(ifr, new String[]{linkedPepLocal});
            FormApi.enableFields(ifr, new String[]{linkedPepLocal});
            FormApi.setMandatory(ifr, new String[]{linkedPepLocal});
        } else {
            FormApi.setInvisible(ifr, new String[]{linkedPepLocal});
            FormApi.undoMandatory(ifr, new String[]{linkedPepLocal});
            FormApi.clearFields(ifr, new String[]{linkedPepLocal});
        }
    }

    public static boolean isLinkedPep(IFormReference ifr) {
        return getIsLinkedPep(ifr).equalsIgnoreCase(yes);
    }

    public static void checkPepVerification(IFormReference ifr) {
        if (isLinkedPep(ifr)) FormApi.setVisible(ifr, linkedPepLocal);

        if (isAccountType(ifr, accountTypeOthers)) FormApi.setVisible(ifr, accountTypeOthers);
    }

    public static void setDecisionApprove(IFormReference ifr) {
        FormApi.setFields(ifr, decisionLocal, decApprove);
    }

    public static String getDocFlag(IFormReference ifr) {
        return FormApi.getFieldValue(ifr, docFlagLocal);
    }

    public static boolean isDocGenerated(IFormReference ifr) {
        return isEmpty(getDocFlag(ifr));
    }

    public static void setDocFlag(IFormReference ifr) {
        FormApi.setFields(ifr, docFlagLocal, flagY);
    }

    public static String checkDocGenerated(IFormReference ifr) {
        if (isDocGenerated(ifr)) return "Kindly generate Pep On-boarding Document";
        return null;
    }

    public static void setRepoView(IFormReference ifr) {
        FormApi.setVisible(ifr, new String[]{accountListSection, pepRepositorySection, decisionSection});
        FormApi.setInvisible(ifr, new String[]{lineExecutiveLocal});
        FormApi.enableFields(ifr, new String[]{bvnLocal, searchBvnBtn, repoAcctNoLocal, decisionLocal, remarksLocal});
        FormApi.setMandatory(ifr, new String[]{bvnLocal, repoAcctNoLocal, decisionLocal, remarksLocal});

        if (isCurrWs(ifr, amlWs)) FormApi.disableFields(ifr, new String[]{bvnLocal, searchBvnBtn, repoAcctNoLocal});
    }

    public static String getRepoAcctNo(IFormReference ifr) {
        return FormApi.getFieldValue(ifr, repoAcctNoLocal);
    }

    public static String setRepoInfo(IFormReference ifr) {
        try {

            String acctNo = getRepoAcctNo(ifr);

            if (isEmpty(acctNo)) return "Kindly enter pep Account Number";

            resultSet = new DbConnect(ifr, Query.getPepRepoDetails(acctNo)).getData();

            if (isEmpty(resultSet)) return "No record found for this pep account number";

            resultSet.forEach(result -> {
                String sol = result.get(0);
                String branchName = result.get(1);
                String accountName = result.get(2);
                String pepName = result.get(3);
                String address = result.get(4);
                String position = result.get(5);
                String natureOfBusiness = result.get(6);
                String acctOpenDate = result.get(7);

                FormApi.setFields(ifr, new String[]{repoAcctNameLocal, repoAddressLocal, repoAcctOpenDateLocal, repoBranchNameLocal, repoSolIdLocal, repoNOfBusiness, repoPepNameLocal, repoPositionLocal},
                        new String[]{accountName, address, acctOpenDate, branchName, sol, natureOfBusiness, pepName, position});

            });
        } catch (Exception e) {
            logger.info("Exception Occurred in setRepInfo Method:  " + e.getMessage());
        }
        return null;
    }

    public static void updatePepRepo(IFormReference ifr) {
        validate = new DbConnect(ifr, Query.updatePepRepo(FormApi.getWorkItemNumber(ifr), getBvn(ifr), getRepoAcctNo(ifr))).saveQuery();

        if (isSaved(validate)) logger.info("Pep Repo Updated");
    }

    public static void onboardPepInRepo(IFormReference ifr) {
        if (isOnboardedFlagNotSet(ifr)) {
            String sol = FormApi.getFieldValue(ifr, pepSolIdLocal);
            String branchName = FormApi.getFieldValue(ifr, pepBranchNameLocal);
            String acctNo = FormApi.getFieldValue(ifr, accountNoLocal);
            String pepName = getPepName(ifr);
            String address = FormApi.getFieldValue(ifr, addressLocal);
            String officePosition = FormApi.getFieldValue(ifr, officeDesignationLocal);
            String acctOpenDate = FormApi.getFieldValue(ifr, accountOpeningDateLocal);
            String wiName = FormApi.getWorkItemNumber(ifr);
            String bvn = getBvn(ifr);
            validate = 0;


            if (isPepCategory(ifr, pepCategoryNew))
                validate = new DbConnect(ifr, Query.setPepRepoNew(wiName, sol, branchName, pepName, address, officePosition, bvn)).saveQuery();
            else if (isPepCategory(ifr, pepCategoryExisting))
                validate = new DbConnect(ifr, Query.setPepRepoExisting(wiName, sol, branchName, acctNo, pepName, address, officePosition, acctOpenDate, bvn)).saveQuery();

            if (isSaved(validate)) {
                setOnboardedFlag(ifr);
                logger.info("Pep Record inserted in repo successfully");
            }
        }
    }

    private static String getPepName(IFormReference ifr) {
        String firstName = FormApi.getFieldValue(ifr, firstNameLocal);
        String surName = FormApi.getFieldValue(ifr, surNameLocal);
        String otherName = FormApi.getFieldValue(ifr, otherNameLocal);

        return firstName + " " + surName + " " + otherName;
    }

    private static void setOnboardedFlag(IFormReference ifr) {
        validate = new DbConnect(ifr, Query.setOnboardedFlag(FormApi.getWorkItemNumber(ifr))).saveQuery();
        if (isSaved(validate)) logger.info("OnboardedFlag set successfully");
    }

    private static boolean isOnboardedFlagNotSet(IFormReference ifr) {
        return Integer.parseInt(new DbConnect(ifr, Query.isOnboardedFlagSet(FormApi.getWorkItemNumber(ifr))).getData().get(0).get(0)) == 0;
    }

    public static String getFirstName(IFormReference ifr) {
        return FormApi.getFieldValue(ifr, firstNameLocal);
    }

    public static String getSurName(IFormReference ifr) {
        return FormApi.getFieldValue(ifr, surNameLocal);
    }

    public static String getOtherName(IFormReference ifr) {
        return FormApi.getFieldValue(ifr, otherNameLocal);
    }

    public static void setStaffName(IFormReference ifr, String nameLocal, String staffIdLocal) {
        try {
            resultSet = new DbConnect(ifr, Query.getStaffName(FormApi.getLoginUser(ifr))).getData();
            String firstName = getDataByCoordinates(resultSet, 0, 0);
            String surName = getDataByCoordinates(resultSet, 0, 1);
            String fullName = firstName.trim().toUpperCase() + " " + surName.trim().toUpperCase();

            validate = new DbConnect(ifr, Query.updateSignNameInfo(nameLocal, staffIdLocal, FormApi.getWorkItemNumber(ifr), fullName, FormApi.getLoginUser(ifr))).saveQuery();
            FormApi.setFields(ifr, new String[]{nameLocal, staffIdLocal}, new String[]{fullName, FormApi.getLoginUser(ifr)});
            if (isSaved(validate)) logger.info("Staff Name successfully Updated");
        } catch (Exception e) {
            logger.error("Exception occurred in setStaffName Method: " + e.getMessage());
        }
    }

    public static void setSignDate(IFormReference ifr, String signDateLocal) {
        if (isDecisionApprove(ifr)) {
            String signDate = getCurrentDateTime(dbDateTimeFormat);
            validate = new DbConnect(ifr, Query.updateSignDateInfo(signDateLocal, signDate, FormApi.getWorkItemNumber(ifr))).saveQuery();
            FormApi.setFields(ifr, signDateLocal, signDate);

            if (isSaved(validate)) logger.info("Sign Date successfully Updated");
        }
    }

    public static String validatePepDocuments(IFormReference ifr){
        if (isDecisionSubmit(ifr)){
            List<String> documents = getDocumentList(ifr);

           for (String document : documents) {
               if (isDocNotAttached(ifr, document)) {
                   switch (document) {
                       case aoDoc: return documentAttachMsg + aoDoc;
                       case addressVerificationDoc: return  documentAttachMsg + addressVerificationDoc;
                       case cddDoc: return  documentAttachMsg + cddDoc;
                       case idDoc: return  documentAttachMsg + idDoc;
                       case idVerificationDoc: return  documentAttachMsg + idVerificationDoc;
                       case bvnDoc: return  documentAttachMsg + bvnDoc;
                       case mandateCardDoc: return  documentAttachMsg + mandateCardDoc;
                       case taxIdDoc: return  documentAttachMsg + taxIdDoc;
                   }
               }
           }
        }
        return null;
    }
    private static List<String> getDocumentList(IFormReference ifr){
        List<String> documentList = new ArrayList<>();
        documentList.add(aoDoc);
        documentList.add(addressVerificationDoc);
        documentList.add(cddDoc);
        documentList.add(idDoc);
        documentList.add(idVerificationDoc);
        documentList.add(bvnDoc);
        documentList.add(mandateCardDoc);
        if (isPepAcctCategory(ifr,pepAcctCategoryCorporate)) documentList.add(taxIdDoc);
        return documentList;
    }

    private static boolean isDocNotAttached(IFormReference ifr,String documentName){
        return Integer.parseInt(new DbConnect
                (ifr,Query.getCheckDocQuery(FormApi.getWorkItemNumber(ifr),documentName)).getData().get(0).get(0) ) < 1;
    }

    public static void checkExistingPep(IFormReference ifr){
        if (isPepCategory(ifr, pepCategoryExisting)) {
            FormApi.setInvisible(ifr, new String[]{surNameLocal, firstNameLocal, otherNameLocal});
            FormApi.setVisible(ifr, new String[]{pepNameLocal});
        }
    }
    public static String getPepAccount(IFormReference ifr){
        return FormApi.getFieldValue(ifr, accountNoLocal);
    }

}
