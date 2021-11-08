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

	//sections

    
	//common variables
	 String dbDateTimeFormat = "";
	 String dhRowStaffId = "";
     String dhRowProcess = "";
     String dhRowMarketType = "";
     String dhRowDecision = "";
     String dhRowRemarks = "";
     String dhRowPrevWs = "";
     String dhRowEntryDate = "";
     String dhRowExitDate = "";
     String dhRowTat = "";
     String decisionHisTable = "";
     String endMail = "firstbanknigeria.com";
 	 String visible = "visible";
 	 String False = "false";
 	 String mandatory = "mandatory";
	 String disable ="disable";
	 String empty ="";
	 String True = "true";
	

	//eventName/controlName

	
	//process info


	//config
	String logPath = "nglogs/NGF_Logs/pep/";
	String configPath = System.getProperty("user.dir") + File.separator + "FBNConfig" + File.separator + "pep.properties";

	//Api Service Name
}
