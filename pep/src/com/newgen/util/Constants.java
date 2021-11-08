package com.newgen.util;

import java.io.File;

public interface Constants {
	//ProcessName 
	
	//WorkSteps
	String workstep1 = "workstep1";
	String workstep2 = "workstep2";

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
	String logPath = "nglogs/NGF_Logs/ProcessName/";
	String configPath = System.getProperty("user.dir") + File.separator + "FBNConfig" + File.separator + "processName.properties";

	//Api Service Name
}
