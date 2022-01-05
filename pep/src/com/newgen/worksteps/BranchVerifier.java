package com.newgen.worksteps;

import com.kelmorgan.ibpsformapis.apis.FormApi;
import com.newgen.iforms.EControl;
import com.newgen.iforms.FormDef;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;
import com.newgen.mvcbeans.model.WorkdeskModel;
import com.newgen.utils.*;
import com.newgen.utils.mail.MailMessage;
import com.newgen.utils.mail.MailSetup;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
			case onChangeEvent:
			break;
			case onClickEvent:{
				switch (control){
					case generateDocEvent:{
						return new GenerateDocument(ifr).generateDoc();
					}
				}
			}
			break;
			case onDoneEvent:{
				switch (control){
					case checkDocEvent:{
						return Shared.checkDocGenerated(ifr);
					}
					case createAoWorkItemEvent:{
						return new CreateAoWorkItem(ifr).createWorkItem();
					}
					case decisionHistoryEvent:{
						Shared.setDecisionHistory(ifr);
					}
					break;
					case sendMailEvent:{
						sendMail(ifr);
					}
					break;
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
			FormApi.clearFields(ifr, new String[]{remarksLocal, decisionHistoryFlagLocal});
			Shared.checkPepVerification(ifr);
			if (Shared.isPrevWs(ifr,ccoWs)){
				Shared.onboardPepInRepo(ifr);
				FormApi.setVisible(ifr, new String[]{accountListSection, pepCategorySection, pepInfoSection,generateDocumentSection, pepVerificationSection, decisionSection});
				Shared.setDecisionApprove(ifr);
				FormApi.enableFields(ifr,new String[]{generatePepDocBtn});
				FormApi.setFields(ifr,remarksLocal,"Pep on-boarding approved and successful");
			}
			else {
				FormApi.setVisible(ifr, new String[]{accountListSection, pepCategorySection, pepInfoSection, pepVerificationSection, decisionSection});
				FormApi.enableFields(ifr, new String[]{decisionLocal, remarksLocal});
				FormApi.setMandatory(ifr, new String[]{decisionLocal, remarksLocal});
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
		MailMessage mailMessage = new MailMessage(ifr);
		String message;
		String sendTo = Shared.getUsersMailsInGroup(ifr, LoadProp.pepMailGroup);
		if(Shared.isDecisionApprove(ifr)){
			//sendTo = Shared.getUsersMailsInGroup(ifr,acoGroupName);
			message = mailMessage.getApproveMsg();
			new MailSetup(ifr,FormApi.getWorkItemNumber(ifr),sendTo,empty,LoadProp.mailSubject,message);
		}
		else if (Shared.isDecisionReturn(ifr)){
			//sendTo = Shared.getUsersMailsInGroup(ifr,rmGroupLabel+Shared.getUserSol(ifr));
			message = mailMessage.getRejectMsg();
			new MailSetup(ifr,FormApi.getWorkItemNumber(ifr),sendTo,empty,LoadProp.mailSubject,message);
		}
	}

	@Override
	public void setDecision(IFormReference ifr) {
		Shared.setDecision(ifr,decisionLocal,new String[]{decApprove,decReturn});
	}
}
