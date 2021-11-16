package com.newgen.worksteps;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newgen.mvcbeans.model.wfobjects.WDGeneralData;
import com.newgen.service.Service;
import com.newgen.util.*;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;

import com.newgen.iforms.EControl;
import com.newgen.iforms.FormDef;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;
import com.newgen.mvcbeans.model.WorkdeskModel;

public class BranchVerifier implements IFormServerEventHandler , SharedI, Constants {
	private final Logger logger = LogGenerator.getLoggerInstance(BranchVerifier.class);

	@Override
	public void beforeFormLoad(FormDef arg0, IFormReference ifr) {
		formLoad(ifr);
	}

	@Override
	public String executeCustomService(FormDef arg0, IFormReference arg1, String arg2, String arg3, String arg4) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONArray executeEvent(FormDef arg0, IFormReference arg1, String arg2, String arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String executeServerEvent(IFormReference ifr, String control, String event, String data) {
		switch (event){
			case onLoadEvent:
			case onChangeEvent:{
			}
			break;
			case onClickEvent:{
				switch (control){
					case generateDocEvent:{
						return GenerateDocument.generateDoc(ifr, Shared.getSessionId(ifr));
					}
				}
			}
			break;
			case onDoneEvent:{
				switch (control){
					case decisionHistoryEvent:{
						Shared.setDecisionHistory(ifr);
						break;
					}
					case sendMailEvent:
				}
			}
		}
		return null;
	}

	@Override
	public String generateHTML(EControl arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCustomFilterXML(FormDef arg0, IFormReference arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String introduceWorkItemInWorkFlow(IFormReference arg0, HttpServletRequest arg1, HttpServletResponse arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String introduceWorkItemInWorkFlow(IFormReference arg0, HttpServletRequest arg1, HttpServletResponse arg2,
			WorkdeskModel arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setMaskedValue(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return arg1;
	}

	@Override
	public JSONArray validateSubmittedForm(FormDef arg0, IFormReference arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void formLoad(IFormReference ifr) {
		try {
			Shared.hideSections(ifr);
			Shared.clearFields(ifr, new String[]{remarksLocal, decisionHistoryFlagLocal});
			Shared.checkPepVerification(ifr);
			if (Shared.isPrevWs(ifr,ccoWs)){
				Shared.setVisible(ifr, new String[]{accountListSection, pepCategorySection, pepInfoSection,generateDocumentSection, pepVerificationSection, decisionSection});
				Shared.setDecisionApprove(ifr);
				Shared.setFields(ifr,remarksLocal,"Pep on-boarding approved and successful");
			}
			else {
				Shared.setVisible(ifr, new String[]{accountListSection, pepCategorySection, pepInfoSection, pepVerificationSection, decisionSection});
				Shared.enableFields(ifr, new String[]{decisionLocal, remarksLocal});
				Shared.setMandatory(ifr, new String[]{decisionLocal, remarksLocal});
				setDecision(ifr);
			}
			Shared.checkSol(ifr);
		}
		catch (Exception e){
			logger.error("Exception occurred in Branch Verifier FormLoad : "+ e.getMessage());
		}
	}

	@Override
	public void sendMail(IFormReference ifr) {

	}

	@Override
	public void setDecision(IFormReference ifr) {
		Shared.setDecision(ifr,decisionLocal,new String[]{decApprove,decReturn});
	}
}
