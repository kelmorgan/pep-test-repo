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
	 String pepStatusLocal = "PEPSTATUS";
	 String tinLocal = "TIN";
	 String srcOfWealthLocal = "SRCINCOME";
	 String purposeOfAccountLocal = "ACCTPURPOSE";
	 String officeDesignationLocal = "OFFICEDESIGN";
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
	 String alColAccountNo = "Account Nu";
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

	//sections
	String accountListSection = "ACCTLINKEDINFOSECTION";
	String pepInfoSection = "PEPINFORMATIONSECTION";
	String pepVerificationSection= "PEPVERIFICATIONSECTION";
	String decisionSection= "DECISIONSECTION";
	String generateDocumentSection = "GENERATEDOCSECTION";
	String pepCategorySection = "PEPCATEGORYSECTION";

    
	//common variables
	 String dbDateTimeFormat = "";
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

	
	//process info


	//config
	String logPath = "nglogs/NGF_Logs/pep/";
	String configPath = System.getProperty("user.dir") + File.separator + "FBNConfig" + File.separator + "pep.properties";

	//Api Service Name
}
