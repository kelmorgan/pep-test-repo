package com.newgen.util;

import java.io.File;

public interface Constants {
    //ProcessName
    String pepProcessName = "PEP";

    //WorkSteps
    String branchInitiatorWs = "BRANCHINITIATOR";
    String branchVerifierWs = "BRANCHVERIFIER";
    String acoWs = "ACO";
    String amlWs = "AML";
    String lineExecutiveWs = "LINEEXECUTIVE";
    String ccoWs = "CCO";
    String reworkWs = "REWORK";
    String amlInitiatorWs = "AMLINITIATOR";
    String queryWs = "QUERY";
    String discardWs = "DISCARD";
    String exitWs = "EXIT";

    //process Ids
    String wiNameFormLocal = "WINAME";
    String wiNameLocal = "WorkItemName";
    String previousWsLocal = "PREVIOUSWRKSTEP";
    String currentWsLocal = "CURRENTWRKSTEP";
    String userIdLocal = "STAFFID";
    String userSolIdLocal = "SOLID";
    String userBranchNameLocal = "BRCHNAME";
    String lineExecutiveLocal = "LINEEXEC";
    String bvnLocal = "BVN";
    String bvnNameLocal = "BVNNAME";
    String accountNoLocal = "ACCTNUM";
    String surNameLocal = "SURNAME";
    String firstNameLocal = "FIRSTNAME";
    String otherNameLocal = "OTHERNAME";
    String addressLocal = "ADDRESS";
    String pepSolIdLocal = "ACCTSOL";
    String pepBranchNameLocal = "BRCHNAMEEXT";
    String pepStatusLocal = "PEPSTATUSNEW";
    String tinLocal = "TIN";
    String srcOfWealthLocal = "SRCINCOME";
    String purposeOfAccountLocal = "ACCTPURPOSE";
    String officeDesignationLocal = "OFFICEDESIGNNEW";
    String pepAccountTypeLocal = "ACCTTYPE";
    String otherAcctTypeLocal = "OTHERS";
    String isDocCompletedLocal = "DOCREQUIRE";
    String isLinkedPepLocal = "BUSASSFAM";
    String linkedPepLocal = "LINKEDPEP";
    String decisionLocal = "DECISION";
    String remarksLocal = "REMARK";
    String bvFlagLocal = "BVFLAG";
    String entryDateLocal = "EntryDateTime";
    String ccoApprovedFlagLocal = "CCOAPPROVEDFLAG";
    String accountListTable = "table3";
    String alColAccountNo = "Account Number";
    String alColAccountName = "Account Name";
    String alColSolId = "SOL ID";
    String alColBranchName = "Branch Name";
    String lineExecFilterLocal = "LINEEXECFILTER";
    String acoFilterLocal = "ACOFILTER";
    String decisionHistoryTable = "table4";
    String dhColStaffId = "Staff ID";
    String dhColPrevWs = "Previous WorkStep";
    String dhColDecision = "Decision";
    String dhColRemarks = "Remark";
    String dhColEntryDate = "Entry Date";
    String dhColExitDate = "Exit Date";
    String dhColTat = "TAT";
    String decisionHistoryFlagLocal = "DHFLAG";
    String accountOpeningDateLocal = "ACCTDATE";
    String pepCategoryLocal = "CUSTOMERCATEGORY";
    String pepAccountCategoryLocal = "ACCTCATEGORY";
    String fetchPepInfoBtn = "FETCHPEPINFOBTN";
    String searchBvnBtn = "SEARCHBVNBTN";
    String generatePepDocBtn = "GENERATEDOCUMENTBTN";
    String generateAoBtn = "GENERATEAOBTN";
    String docFlagLocal = "DOCFLAG";
    String repoSolIdLocal = "REPO_SOLID";
    String repoBranchNameLocal = "REPO_BRANCHNAME";
    String repoAcctNameLocal = "REPO_ACCTNAMENEW";
    String repoAcctNoLocal = "REPO_ACCTNO";
    String repoPepNameLocal = "REPO_PEPNAME";
    String repoAddressLocal = "REPO_ADDRESS";
    String repoPositionLocal = "REPO_POSITION";
    String repoNOfBusiness = "REPO_NOFBUSINESS";
    String repoAcctOpenDateLocal = "REPO_ACCTOPENDATE";
    String aoWiNameFlagLocal = "AOFLAG";
    String aoWiNameLocal = "AOWINAME";
    String edNameLocal = "EDNAME";
    String ccoNameLocal = "CCONAME";
    String edStaffIdLocal = "EDSTAFFID";
    String ccoStaffIdLocal = "CCOSTAFFID";
    String edSignDateLocal = "EDSIGNDATE";
    String ccoSignDateLocal = "CCOSIGNDATE";
    String pepNameLocal = "PEPNAME";
    String pepOnboardedDateLocal= "INITIATIONDATE";
    String initiatorMailLocal = "INITIATORMAIL";

    //sections
    String accountListSection = "ACCTLINKEDINFOSECTION";
    String pepInfoSection = "PEPINFORMATIONSECTION";
    String pepVerificationSection = "PEPVERIFICATIONSECTION";
    String decisionSection = "DECISIONSECTION";
    String generateSection = "GENERATEDOCSECTION";
    String pepCategorySection = "PEPCATEGORYSECTION";
    String pepRepositorySection = "REPOSECTION";

    //common variables
    String dbDateTimeFormat = "yyyy-MM-dd HH:mm:ss";
    String dbDateFormat = "yyyy-MM-dd";
    String endMail = "@firstbanknigeria.com";
    String visible = "visible";
    String False = "false";
    String mandatory = "mandatory";
    String disable = "disable";
    String empty = "";
    String True = "true";
    String bmGroupLabel = "BM_";
    String rmGroupLabel = "RM_";
    String flagY = "Y";
    String flagN = "N";
    String decSubmit = "SUBMIT";
    String decRework = "REWORK";
    String decReturn = "RETURN";
    String decReject = "REJECT";
    String decApprove = "APPROVE";
    String decDiscard = "DISCARD";
    int bvnLength = 11;
    String lineExecRetailCommercial = "Retail And Commercial Banking";
    String lineExecCorporate = "Corporate Banking";
    String lineExecPublic = "Public Sector";
    String accountTypeOthers = "Others";
    String na = "N.A";
    String pepCategoryNew = "New";
    String pepCategoryExisting = "Existing";
    String pepAcctCategoryIndividual = "Individual";
    String pepAcctCategoryCorporate = "Corporate";
    String yes = "YES";
    String apiSuccess = "SUCCESS";
    String apiStatus = "Status";
    String apiFailed = "FAILED";
    String apiFailure = "FAILURE";
    String apiSuccessFailureFlag = "SuccessOrFailure";
    String successKey = "success";
    String errorKey = "error";
    String exceptionMsg = "Kindly contact IBPS support";
    String documentAttachMsg ="Kindly attach document. Document Name : ";


    //eventName/controlName
    String onClickEvent = "onClickEvent";
    String onChangeEvent = "onChangeEvent";
    String onDoneEvent = "onDoneEvent";
    String onLoadEvent = "onLoadEvent";
    String apiEvent = "apiEvent";
    String lineExecFilterEvent = "lineExecFilterEvent";
    String accountTypeEvent = "accountTypeEvent";
    String decisionHistoryEvent = "decisionHistoryEvent";
    String sendMailEvent = "sendMailEvent";
    String mandatoryPepInfoEvent = "mandatoryPepInfoEvent";
    String isLinkedPepEvent = "isLinkedPepEvent";
    String generateDocEvent = "generateDocEvent";
    String checkDocEvent = "checkDocEvent";
    String setRepoEvent = "setRepoEvent";
    String updatePepRepoEvent = "updatePepRepoEvent";
    String createAoWorkItemEvent = "createAoWorkItemEvent";
    String signEvent = "signEvent";
    String setAccountDetailsEvent = "setAccountDetailsEvent";
    String testEvent = "testEvent";
    String setPepNameEvent = "setPepNameEvent";
    String setDocGenerationEvent = "setDocGenerationEvent";

    //process info

    //Document List
    String aoDoc = "COMPLETED ACCOUNT OPENING FORM";
    String addressVerificationDoc = "ADDRESS VERIFICATION";
    String cddDoc = "CUSTOMER DUE DILIGENCE FORM";
    String idDoc = "MEANS OF IDENTIFICATION";
    String idVerificationDoc = "EVIDENCE OF IDENTITY CARD VERIFICATION";
    String taxIdDoc = "TAX IDENTIFICATION VERIFICATION PAGE";
    String mandateCardDoc = "MANDATE CARD";
    String bvnDoc = "BVN VALIDATION PAGE";


    //Call Client Configs
    String getBvnAcctListAppCode = "ACCTLINKTOBVN";
    String getSavingAcctAppCode = "SBAcctInqrespAcctStatus";
    String getCurrentAcctAppCode = "ODAcctInqrespAcctStatus";
    String getSpecialAcctAppcode = "FetchCAAccountDetails";
    String callTypeFinacle = "Finacle";
    String callTypeWebservice = "WebService";
    String endpointCustomFIFinacle = "EndPointUrl";
    String endpointBpmFinacle = "AM_EndPointUrl";
    String endpointBvnValidator = "BvnValidator";
    String soapActionBvnValidator = "BVNValidation";

    //config
    String configPath = System.getProperty("user.dir") + File.separator + "FBNConfig" + File.separator + "pep.properties";
   // String mailMessagePath = "PEP" + File.separator + "config" + File.separator + "mailmessages.properties";
    String mailMessagePath =System.getProperty("user.dir") + File.separator + "FBNConfig" + File.separator + "pepmailmessages.properties";
    //String logPathField = "LOGPATH";
    String logPathField = "nglogs/NGF_Logs/pep/";
    String activateAoField = "ACTIVATEAO";
    String aoQueueIdField = "AOQUEUEID";
    String aoProcessDefIdField = "AOPROCESSDEFID";
    String mailFromField = "MAILFROM";
    String processDefIdField = "PROCESSDEFID";
    String cabinetNameField = "CABINETNAME";
    String templateNameField = "TEMPLATENAME";
    String templatePortField = "TEMPLATEPORT";
    String serverIpField = "SERVERIP";
    String serverPortField = "SERVERPORT";
    String jtsIpField = "JTSIP";
    String jtsPortField = "JTSPORT";
    String serverNameField = "SERVERNAME";
    String mailSubjectField = "MAILSUBJECT";
    String pepMailGroupField = "PEPMAILGROUP";
    String amlGroupNameField = "AMLGROUPNAME";
    String ccoGroupNameField = "CCOGROUPNAME";

    //mail messages config
    String branchInitiatorMsg = "BRANCHINITIATORMSG";
    String approveMsg = "APPROVEMSG";
    String bvApproveMsg = "BVAPPROVEMSG";
    String rejectMsg = "REJECTMSG";
    String returnMsg = "RETURNMSG";
    String amlInitiatorMsg = "AMLINITIATORMSG";
    String amlApproveMsg = "AMLAPPROVEMSG";
    String amlRejectMsg = "AMLREJECTMSG";
    String aoWorkItemMsg = "CREATEAOWORKITEMMSG";
}
