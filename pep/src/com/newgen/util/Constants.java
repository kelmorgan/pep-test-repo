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
	 String wiNameFormLocal = "";
 	 String wiNameLocal = "WorkItemName";
     String previousWsLocal = "";
	 String currentWsLocal = "";
	 String staffIdLocal = "";
	 String userSolIdLocal = "";
	 String userBranchNameLocal = "";
	 String lineExecutiveLocal = "";
	 String bvnLocal = "";
	 String accountNoLocal = "";
	 String surNameLocal = "";
	 String firstNameLocal = "";
	 String otherNameLocal = "";
	 String addressLocal = "";
	 String pepSolIdLocal = "";
	 String pepBranchNameLocal = "";
	 String pepStatusLocal = "";
	 String tinLocal = "";
	 String srcOfWealthLocal = "";
	 String purposeOfAccountLocal = "";
	 String officeDesignationLocal = "";
	 String pepAccountTypeLocal = "";
	 String otherAcctTypeLocal = "";
	 String isDocCompletedLocal = "";
	 String isLinkedPepLocal = "";
	 String linkedPepLocal = "";
	 String decisionLocal = "";
	 String remarksLocal = "";
	 String bvFlagLocal = "";
	 String entryDateLocal = "";
	 String ccoApprovedFlagLocal = "CCOAPPROVEDFLAG";
	 String accountListTable = "";
	 String alColAccountNo = "";
	 String alColAccountName = "";
	 String alColSolId = "";
	 String alColBranchName = "";
	 String lineExecFilterLocal = "";
	 String decisionHisTable = "";
	 String aocFilterLocal = "";

	//sections
	String accountListSection = "";
	String pepInfoSection = "";
	String pepVerificationSection= "";
	String decisionSection= "";

    
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
	 String decSubmit = "Submit";
	 String decRework = "Rework";
	 String decReturn = "Return";
	 String decReject = "Reject";
	 String decApprove = "Approve";
	 String decDiscard = "Discard";
	 int bvnLength = 11;
	 String lineExecRetailCommercial = "Retail And Commercial Banking";
	 String lineExecCorporate = "Corporate Banking";
	 String lineExecPublic = "Public Sector";
	 String lineExecRetailCommercialId = "1";
	 String lineExecCorporateId = "2";
	 String lineExecPublicId = "3";


	//eventName/controlName
	String apiEvent = "apiEvent";
	String lineExecFilterEvent= "lineExecFilterEvent";

	
	//process info


	//config
	String logPath = "nglogs/NGF_Logs/pep/";
	String configPath = System.getProperty("user.dir") + File.separator + "FBNConfig" + File.separator + "pep.properties";

	//Api Service Name
}
