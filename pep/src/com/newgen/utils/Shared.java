package com.newgen.utils;


import com.kelmorgan.ibpsformapis.apis.Form;
import com.newgen.iforms.custom.IFormReference;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        return Form.getFieldValue(ifr, userSolIdLocal);
    }

    public static void checkBmIsInitiator(IFormReference ifr) {
        try {
            Form.clearFields(ifr, bvFlagLocal);
            resultSet = new DbConnect(ifr, Query.getIsUserMemberOfGroup(Form.getLoginUser(ifr), getBmGroupName(ifr))).getData();
            int count = getFormattedNumber(getDataByCoordinates(resultSet, 0, 0));
            if (count > 0) Form.setFields(ifr, bvFlagLocal, flagY);
        } catch (Exception e) {
            logger.info("Exception occurred in checkBmIsInitiator method: " + e.getMessage());
        }
    }

    public static void setInitiatorDetails(IFormReference ifr) {
        try {
            resultSet = new DbConnect(ifr, Query.getUserDetailsQuery(Form.getLoginUser(ifr))).getData();
            String sol = getDataByCoordinates(resultSet, 0, 0);
            String branchName = getDataByCoordinates(resultSet, 0, 1);
            Form.setFields(ifr, new String[]{userSolIdLocal, userBranchNameLocal, userIdLocal}, new String[]{sol, branchName, Form.getLoginUser(ifr)});
        } catch (Exception e) {
            logger.info("Exception occurred in setInitiatorDetails method: " + e.getMessage());
        }
    }

    public static void hideSections(IFormReference ifr) {
        Form.setInvisible(ifr, new String[]{accountListSection, pepInfoSection, pepVerificationSection, decisionSection, pepCategorySection, generateDocumentSection, pepRepositorySection});
    }

    public static boolean isProcessName(IFormReference ifr, String processName) {
        return Form.getProcessName(ifr).equalsIgnoreCase(processName);
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
            Form.setTableGridData(ifr, decisionHistoryTable, new String[]{dhColStaffId, dhColPrevWs, dhColDecision, dhColRemarks, dhColEntryDate, dhColExitDate, dhColTat},
                    new String[]{Form.getLoginUser(ifr), Form.getCurrentWorkStep(ifr), getDecision(ifr), getRemarks(ifr), getEntryDate(ifr), getCurrentDateTime(), tat});
            Form.setFields(ifr, decisionHistoryFlagLocal, flagY);
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
        Form.setDropDown(ifr, decisionLocal, values);
        Form.clearFields(ifr, decisionLocal);
    }

    public static void setDecision(IFormReference ifr, String decisionLocal, String[] labels, String[] values) {
        Form.setDropDown(ifr, decisionLocal, labels, values);
        Form.clearFields(ifr, decisionLocal);
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
        Form.setFields(ifr, wiNameFormLocal, Form.getWorkItemNumber(ifr));
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
        return Form.getFieldValue(ifr, bvnLocal);
    }

    public static String getPrevWs(IFormReference ifr) {
        return Form.getFieldValue(ifr, previousWsLocal);
    }

    public static boolean isPrevWs(IFormReference ifr, String workStep) {
        return getPrevWs(ifr).equalsIgnoreCase(workStep);
    }

    public static boolean isCurrWs(IFormReference ifr, String workStep) {
        return Form.getCurrentWorkStep(ifr).equalsIgnoreCase(workStep);
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
        return Form.getFieldValue(ifr, decisionLocal);
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
        return Form.getFieldValue(ifr, lineExecutiveLocal);
    }

    public static boolean isLineExecutive(IFormReference ifr, String executive) {
        return getLineExecutive(ifr).equalsIgnoreCase(executive);
    }

    public static void loadLineExecutive(IFormReference ifr) {
        try {
            if (isCurrWs(ifr, branchInitiatorWs)) Form.clearDropDown(ifr, lineExecutiveLocal);
            resultSet = new DbConnect(ifr, Query.getLineExecutives()).getData();
            resultSet.forEach(result -> {
                String lineExecName = result.get(0);
                Form.addToDropDown(ifr, lineExecutiveLocal, lineExecName);
            });
        } catch (Exception e) {
            logger.info("Exception occurred in Loading Line Executive Method: " + e.getMessage());
        }
    }

    public static void setLineExecutiveFilter(IFormReference ifr) {
        try {
            Form.clearFields(ifr, lineExecFilterLocal);
            resultSet = new DbConnect(ifr, Query.getLineExecutivesId(getLineExecutive(ifr))).getData();
            Form.setFields(ifr, lineExecFilterLocal, getDataByCoordinates(resultSet, 0, 0));
        } catch (Exception e) {
            logger.error("Exception occurred in setting line executive filter: " + e.getMessage());
        }
    }

    public static void setAcoFilter(IFormReference ifr) {
        try {
            Form.clearFields(ifr, acoFilterLocal);
            resultSet = new DbConnect(ifr, Query.getAcoId(getUserSol(ifr))).getData();
            Form.setFields(ifr, acoFilterLocal, getDataByCoordinates(resultSet, 0, 0));
        } catch (Exception e) {
            logger.info("Exception occurred in setting aco filter method: " + e.getMessage());
        }
    }

    public static String getAccountType(IFormReference ifr) {
        return Form.getFieldValue(ifr, pepAccountTypeLocal);
    }

    public static boolean isAccountType(IFormReference ifr, String accountType) {
        return getAccountType(ifr).equalsIgnoreCase(accountType);
    }

    public static void showAccountTypeOthersField(IFormReference ifr) {
        if (isAccountType(ifr, accountTypeOthers)) {
            Form.setVisible(ifr, otherAcctTypeLocal);
            Form.setMandatory(ifr, otherAcctTypeLocal);
        } else {
            Form.clearFields(ifr, otherAcctTypeLocal);
            Form.setInvisible(ifr, otherAcctTypeLocal);
        }
    }

    public static String getRemarks(IFormReference ifr) {
        return Form.getFieldValue(ifr, remarksLocal);
    }

    public static boolean isNotMemberOfSol(IFormReference ifr) {
        return Integer.parseInt(new DbConnect(ifr, Query.getIsMemberOfSol(Form.getLoginUser(ifr), getUserSol(ifr))).getData().get(0).get(0)) <= 0;
    }

    public static String getEntryDate(IFormReference ifr) {
        return Form.getFieldValue(ifr, entryDateLocal);
    }

    public static boolean isDecisionHistoryEmpty(IFormReference ifr) {
        return isEmpty(getDecisionHistoryFlag(ifr));
    }

    private static String getDecisionHistoryFlag(IFormReference ifr) {
        return Form.getFieldValue(ifr, decisionHistoryFlagLocal);
    }

    public static String getPepCategory(IFormReference ifr) {
        return Form.getFieldValue(ifr, pepCategoryLocal);
    }

    public static String getPepAccountCategory(IFormReference ifr) {
        return Form.getFieldValue(ifr, pepAccountCategoryLocal);
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

        Form.enableFields(ifr, new String[]{surNameLocal, firstNameLocal, otherNameLocal, addressLocal, pepSolIdLocal, pepBranchNameLocal, pepStatusLocal, srcOfWealthLocal, purposeOfAccountLocal, officeDesignationLocal, pepAccountTypeLocal, isDocCompletedLocal, isLinkedPepLocal});
        Form.setMandatory(ifr, new String[]{surNameLocal, firstNameLocal, addressLocal, pepSolIdLocal, pepBranchNameLocal, pepStatusLocal, srcOfWealthLocal, purposeOfAccountLocal, officeDesignationLocal, pepAccountTypeLocal, isDocCompletedLocal, isLinkedPepLocal});

        if (isPepCategory(ifr, pepCategoryExisting)) {
            Form.enableFields(ifr, new String[]{accountNoLocal, accountOpeningDateLocal});
            Form.setMandatory(ifr, new String[]{accountNoLocal, accountOpeningDateLocal});
        } else {
            Form.clearFields(ifr, new String[]{accountNoLocal, accountOpeningDateLocal});
            Form.disableFields(ifr, new String[]{accountNoLocal, accountOpeningDateLocal});
            Form.undoMandatory(ifr, new String[]{accountNoLocal, accountOpeningDateLocal});
        }

        if (isPepAcctCategory(ifr, pepAcctCategoryCorporate)) {
            Form.enableFields(ifr, new String[]{tinLocal});
            Form.setMandatory(ifr, new String[]{tinLocal});
        } else {
            Form.disableFields(ifr, new String[]{tinLocal});
            Form.clearFields(ifr, new String[]{tinLocal});
            Form.undoMandatory(ifr, new String[]{tinLocal});
        }

        return null;
    }

    public static String getIsLinkedPep(IFormReference ifr) {
        return Form.getFieldValue(ifr, isLinkedPepLocal);
    }

    public static void linkedPep(IFormReference ifr) {
        if (isLinkedPep(ifr)) {
            Form.setVisible(ifr, new String[]{linkedPepLocal});
            Form.enableFields(ifr, new String[]{linkedPepLocal});
            Form.setMandatory(ifr, new String[]{linkedPepLocal});
        } else {
            Form.setInvisible(ifr, new String[]{linkedPepLocal});
            Form.undoMandatory(ifr, new String[]{linkedPepLocal});
            Form.clearFields(ifr, new String[]{linkedPepLocal});
        }
    }

    public static boolean isLinkedPep(IFormReference ifr) {
        return getIsLinkedPep(ifr).equalsIgnoreCase(yes);
    }

    public static void checkPepVerification(IFormReference ifr) {
        if (isLinkedPep(ifr)) Form.setVisible(ifr, linkedPepLocal);

        if (isAccountType(ifr, accountTypeOthers)) Form.setVisible(ifr, accountTypeOthers);
    }

    public static void setDecisionApprove(IFormReference ifr) {
        Form.setFields(ifr, decisionLocal, decApprove);
    }

    public static String getDocFlag(IFormReference ifr) {
        return Form.getFieldValue(ifr, docFlagLocal);
    }

    public static boolean isDocGenerated(IFormReference ifr) {
        return isEmpty(getDocFlag(ifr));
    }

    public static void setDocFlag(IFormReference ifr) {
        Form.setFields(ifr, docFlagLocal, flagY);
    }

    public static String checkDocGenerated(IFormReference ifr) {
        if (isDocGenerated(ifr)) return "Kindly generate Pep On-boarding Document";
        return null;
    }

    public static void setRepoView(IFormReference ifr) {
        Form.setVisible(ifr, new String[]{accountListSection, pepRepositorySection, decisionSection});
        Form.setInvisible(ifr, new String[]{lineExecutiveLocal});
        Form.enableFields(ifr, new String[]{bvnLocal, searchBvnBtn, repoAcctNoLocal, decisionLocal, remarksLocal});
        Form.setMandatory(ifr, new String[]{bvnLocal, repoAcctNoLocal, decisionLocal, remarksLocal});

        if (isCurrWs(ifr, amlWs)) Form.disableFields(ifr, new String[]{bvnLocal, searchBvnBtn, repoAcctNoLocal});
    }

    public static String getRepoAcctNo(IFormReference ifr) {
        return Form.getFieldValue(ifr, repoAcctNoLocal);
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

                Form.setFields(ifr, new String[]{repoAcctNameLocal, repoAddressLocal, repoAcctOpenDateLocal, repoBranchNameLocal, repoSolIdLocal, repoNOfBusiness, repoPepNameLocal, repoPositionLocal},
                        new String[]{accountName, address, acctOpenDate, branchName, sol, natureOfBusiness, pepName, position});

            });
        } catch (Exception e) {
            logger.info("Exception Occurred in setRepInfo Method:  " + e.getMessage());
        }
        return null;
    }

    public static void updatePepRepo(IFormReference ifr) {
        validate = new DbConnect(ifr, Query.updatePepRepo(Form.getWorkItemNumber(ifr), getBvn(ifr), getRepoAcctNo(ifr))).saveQuery();

        if (isSaved(validate)) logger.info("Pep Repo Updated");
    }

    public static void onboardPepInRepo(IFormReference ifr) {
        if (isOnboardedFlagNotSet(ifr)) {
            String sol = Form.getFieldValue(ifr, pepSolIdLocal);
            String branchName = Form.getFieldValue(ifr, pepBranchNameLocal);
            String acctNo = Form.getFieldValue(ifr, accountNoLocal);
            String pepName = getPepName(ifr);
            String address = Form.getFieldValue(ifr, addressLocal);
            String officePosition = Form.getFieldValue(ifr, officeDesignationLocal);
            String acctOpenDate = Form.getFieldValue(ifr, accountOpeningDateLocal);
            String wiName = Form.getWorkItemNumber(ifr);
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
        String firstName = Form.getFieldValue(ifr, firstNameLocal);
        String surName = Form.getFieldValue(ifr, surNameLocal);
        String otherName = Form.getFieldValue(ifr, otherNameLocal);

        return firstName + " " + surName + " " + otherName;
    }

    private static void setOnboardedFlag(IFormReference ifr) {
        validate = new DbConnect(ifr, Query.setOnboardedFlag(Form.getWorkItemNumber(ifr))).saveQuery();
        if (isSaved(validate)) logger.info("OnboardedFlag set successfully");
    }

    private static boolean isOnboardedFlagNotSet(IFormReference ifr) {
        return Integer.parseInt(new DbConnect(ifr, Query.isOnboardedFlagSet(Form.getWorkItemNumber(ifr))).getData().get(0).get(0)) == 0;
    }

    public static String getFirstName(IFormReference ifr) {
        return Form.getFieldValue(ifr, firstNameLocal);
    }

    public static String getSurName(IFormReference ifr) {
        return Form.getFieldValue(ifr, surNameLocal);
    }

    public static String getOtherName(IFormReference ifr) {
        return Form.getFieldValue(ifr, otherNameLocal);
    }

    public static void setStaffName(IFormReference ifr, String nameLocal, String staffIdLocal) {
        try {
            resultSet = new DbConnect(ifr, Query.getStaffName(Form.getLoginUser(ifr))).getData();
            String firstName = getDataByCoordinates(resultSet, 0, 0);
            String surName = getDataByCoordinates(resultSet, 0, 1);
            String fullName = firstName.trim().toUpperCase() + " " + surName.trim().toUpperCase();

            validate = new DbConnect(ifr, Query.updateSignNameInfo(nameLocal, staffIdLocal, Form.getWorkItemNumber(ifr), fullName, Form.getLoginUser(ifr))).saveQuery();
            Form.setFields(ifr, new String[]{nameLocal, staffIdLocal}, new String[]{fullName, Form.getLoginUser(ifr)});
            if (isSaved(validate)) logger.info("Staff Name successfully Updated");
        } catch (Exception e) {
            logger.error("Exception occurred in setStaffName Method: " + e.getMessage());
        }
    }

    public static void setSignDate(IFormReference ifr, String signDateLocal) {
        if (isDecisionApprove(ifr)) {
            String signDate = getCurrentDateTime(dbDateTimeFormat);
            validate = new DbConnect(ifr, Query.updateSignDateInfo(signDateLocal, signDate, Form.getWorkItemNumber(ifr))).saveQuery();
            Form.setFields(ifr, signDateLocal, signDate);

            if (isSaved(validate)) logger.info("Sign Date successfully Updated");
        }
    }
}
