package com.newgen.worksteps;

import com.kelmorgan.ibpsformapis.apis.FormApi;
import com.newgen.api.serviceHandler.Service;
import com.newgen.iforms.EControl;
import com.newgen.iforms.FormDef;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;
import com.newgen.mvcbeans.model.WorkdeskModel;
import com.newgen.tests.MailTests;
import com.newgen.util.*;
import com.newgen.util.mail.MailMessage;
import com.newgen.util.mail.MailSetup;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class BranchInitiator implements IFormServerEventHandler, SharedI, Constants {
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
        switch (event) {
            case onLoadEvent:
            case onChangeEvent: {
                switch (control) {
                    case lineExecFilterEvent: {
                        Shared.setLineExecutiveFilter(ifr);
                    }
                    break;
                    case accountTypeEvent: {
                        Shared.showAccountTypeOthersField(ifr);
                    }
                    break;
                    case isLinkedPepEvent: {
                        Shared.linkedPep(ifr);
                    }
                    break;
                    case mandatoryPepInfoEvent: {
                        return Shared.setPepMandatoryInfoFields(ifr);
                    }
                }
            }
            break;
            case onClickEvent: {
                switch (control) {
                    case apiEvent: {
                        return new Service(ifr).getAccountList();
                    }
                    case setAccountDetailsEvent:{
                        return new Service(ifr).getAccountDetails();
                    }
                }
            }
            break;
            case onDoneEvent: {
                switch (control) {
                    case checkDocEvent:{
                        return Shared.validatePepDocuments(ifr);
                    }
                    case decisionHistoryEvent: {
                        Shared.setDecisionHistory(ifr);
                    }
                    break;
                    case sendMailEvent:{
                        sendMail(ifr);
                    }
                    break;
                }
            }
            break;
            case testEvent:{
                new MailTests(ifr).mainCall();
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
            FormApi.setFields(ifr, new String[]{currentWsLocal, previousWsLocal}, new String[]{FormApi.getCurrentWorkStep(ifr), na});
            FormApi.setVisible(ifr, new String[]{accountListSection, decisionSection, pepCategorySection});
            FormApi.enableFields(ifr, new String[]{bvnLocal, pepCategoryLocal, pepAccountCategoryLocal, lineExecutiveLocal, decisionLocal, remarksLocal, searchBvnBtn});
            FormApi.setMandatory(ifr, new String[]{bvnLocal, pepCategoryLocal, pepAccountCategoryLocal, lineExecutiveLocal, decisionLocal, remarksLocal});
            Shared.loadLineExecutive(ifr);
            Shared.setAcoFilter(ifr);
            setDecision(ifr);
        } catch (Exception e) {
            logger.error("Exception occurred in Branch Initiator FormLoad : " + e.getMessage());
        }
    }

    @Override
    public void sendMail(IFormReference ifr) {
        if (Shared.isDecisionSubmit(ifr)) {
            String sendTo = Shared.getUsersMailsInGroup(ifr, LoadProp.pepMailGroup);
            String message = new MailMessage(ifr).getBranchInitiatorMsg();
            new MailSetup(ifr, FormApi.getWorkItemNumber(ifr), sendTo, empty, LoadProp.mailSubject, message);
        }
    }

    @Override
    public void setDecision(IFormReference ifr) {
        Shared.setDecision(ifr, decisionLocal, new String[]{decSubmit, decDiscard});
    }


}
