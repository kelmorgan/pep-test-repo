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
	 String generatePepDocBtn= "GENERATEDOCUMENTBTN";
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

	//sections
	String accountListSection = "ACCTLINKEDINFOSECTION";
	String pepInfoSection = "PEPINFORMATIONSECTION";
	String pepVerificationSection= "PEPVERIFICATIONSECTION";
	String decisionSection= "DECISIONSECTION";
	String generateDocumentSection = "GENERATEDOCSECTION";
	String pepCategorySection = "PEPCATEGORYSECTION";
	String pepRepositorySection = "REPOSECTION";

    
	//common variables
	 String dbDateTimeFormat = "yyyy-MM-dd HH:mm:ss";
	 String dbDateFormat = "yyyy-MM-dd";
     String endMail = "firstbanknigeria.com";
 	 String visible = "visible";
 	 String False = "false";
 	 String mandatory = "mandatory";
	 String disable ="disable";
	 String empty ="";
	 String True = "true";
	 String bmGroupLabel = "BM_";
	 String rmGroupLabel= "RM_";
	 String flag = "Y";
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
	 String accountTypeOthers= "Others";
	 String na = "N.A";
	 String pepCategoryNew = "New";
	 String pepCategoryExisting = "Existing";
	 String pepAcctCategoryIndividual = "Individual";
	 String pepAcctCategoryCorporate = "Corporate";
	 String yes = "YES";
	 String apiSuccess = "SUCCESS";
	 String apiStatus = "Status";
	 String apiFailed = "Status";
	 String apiFailure = "Status";
	 String apiSuccessFailureFlag = "SuccessOrFailure";
	 String successKey = "success";
	 String errorKey = "error";


	//eventName/controlName
	String onClickEvent = "onClickEvent";
	String onChangeEvent = "onChangeEvent";
	String onDoneEvent = "onDoneEvent";
	String onLoadEvent = "onLoadEvent";
	String apiEvent = "apiEvent";
	String lineExecFilterEvent= "lineExecFilterEvent";
	String accountTypeEvent = "accountTypeEvent";
	String decisionHistoryEvent = "decisionHistoryEvent";
	String sendMailEvent = "sendMailEvent";
	String mandatoryPepInfoEvent = "mandatoryPepInfoEvent";
	String isLinkedPepEvent  = "isLinkedPepEvent";
	String generateDocEvent = "generateDocEvent";
	String checkDocEvent = "checkDocEvent";
	String setRepoEvent = "setRepoEvent";
	String updatePepRepoEvent = "updatePepRepoEvent";

	
	//process info

	//service info
	String activateAoField = "ACTIVATEAO";


	//Call Client Configs
	String getBvnAcctListAppCode = "ACCTLINKTOBVN";
	String getSavingAcctAppCode = "SBAcctInqrespAcctStatus";
	String getCurrentAcctAppCode = "ODAcctInqrespAcctStatus";
	String getSpecialAcctAppcode = "FetchCAAccountDetails";
	String callTypeFinacle  = "Finacle";
	String endpointCustomFIFinacle  = "EndPointUrl";
	String endpointBpmFinacle  = "AM_EndPointUrl";

	//config
	String logPath = "nglogs/NGF_Logs/pep/";
	String configPath = System.getProperty("user.dir") + File.separator + "FBNConfig" + File.separator + "pep.properties";

	//Api Service Name
}
