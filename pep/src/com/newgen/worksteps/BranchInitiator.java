package com.newgen.worksteps;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newgen.service.Service;
import com.newgen.util.Constants;
import com.newgen.util.LogGenerator;
import com.newgen.util.Shared;
import com.newgen.util.SharedI;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;

import com.newgen.iforms.EControl;
import com.newgen.iforms.FormDef;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;
import com.newgen.mvcbeans.model.WorkdeskModel;



public class BranchInitiator implements IFormServerEventHandler , SharedI, Constants {
	private final Logger logger = LogGenerator.getLoggerInstance(BranchInitiator.class);

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
				switch (control){
					case lineExecFilterEvent:{
						Shared.setLineExecutiveFilter(ifr);
						break;
					}
					case accountTypeEvent:{
						Shared.showAccountTypeOthersField(ifr);
						break;
					}
					case mandatoryPepInfoEvent:{
					return Shared.setPepMandatoryInfoFields(ifr);
					}
				}
			}
			break;
			case onClickEvent:{
				switch (control) {
					case apiEvent: {
						return new Service(ifr).getAccountListTest();
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
		return arg1;
	}

	@Override
	public JSONArray validateSubmittedForm(FormDef arg0, IFormReference arg1, String arg2) {
		return null;
	}


	@Override
	public void formLoad(IFormReference ifr) {
		try {
			Shared.hideSections(ifr);
			Shared.setInitiatorDetails(ifr);
			Shared.checkBmIsInitiator(ifr);
			Shared.setFields(ifr,new String[]{currentWsLocal,previousWsLocal}, new String[]{Shared.getCurrentWorkStep(ifr),na});
			Shared.setVisible(ifr,new String[]{accountListSection,pepInfoSection,pepVerificationSection,decisionSection});
			Shared.enableFields(ifr,new String[]{bvnLocal,pepCategoryLocal,pepAccountCategoryLocal});
			Shared.setMandatory(ifr,new String[]{bvnLocal,pepCategoryLocal,pepAccountCategoryLocal});
			Shared.loadLineExecutive(ifr);
			Shared.setAcoFilter(ifr);
			setDecision(ifr);
		}
		catch (Exception e){
			logger.error("Exception occurred in Branch Initiator FormLoad : "+ e.getMessage());
		}
	}

	@Override
	public void sendMail(IFormReference ifr) {

	}

	@Override
	public void setDecision(IFormReference ifr) {
		Shared.setDecision(ifr,decisionLocal,new String[]{decSubmit,decDiscard});
	}


}
