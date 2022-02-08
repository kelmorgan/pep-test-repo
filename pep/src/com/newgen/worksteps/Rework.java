package com.newgen.worksteps;

import com.kelmorgan.ibpsformapis.apis.FormApi;
import com.newgen.api.serviceHandler.Service;
import com.newgen.iforms.EControl;
import com.newgen.iforms.FormDef;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;
import com.newgen.mvcbeans.model.WorkdeskModel;
import com.newgen.util.*;
import com.newgen.util.mail.MailMessage;
import com.newgen.util.mail.MailSetup;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Rework implements IFormServerEventHandler, SharedI, Constants {
    private final Logger logger = LogGenerator.getLoggerInstance(Rework.class);

    @Override
    public void beforeFormLoad(FormDef formDef, IFormReference ifr) {
        formLoad(ifr);
    }

    @Override
    public String setMaskedValue(String s, String s1) {
        return s1;
    }

    @Override
    public JSONArray executeEvent(FormDef formDef, IFormReference iFormReference, String s, String s1) {
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
                        break;
                    }
                    case accountTypeEvent: {
                        Shared.showAccountTypeOthersField(ifr);
                        break;
                    }
                    case isLinkedPepEvent: {
                        Shared.linkedPep(ifr);
                        break;
                    }
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
                    case checkDocEvent: {
                        return Shared.validatePepDocuments(ifr);
                    }
                    case setPepNameEvent:{
                        Shared.setPepName(ifr);
                    }
                    break;
                    case decisionHistoryEvent: {
                        Shared.setDecisionHistory(ifr);
                    }
                    break;
                    case sendMailEvent: {
                        sendMail(ifr);
                    }
                    break;
                }
            }
        }
        return null;
    }

    @Override
    public JSONArray validateSubmittedForm(FormDef formDef, IFormReference iFormReference, String s) {
        return null;
    }

    @Override
    public String executeCustomService(FormDef formDef, IFormReference iFormReference, String s, String s1, String s2) {
        return null;
    }

    @Override
    public String getCustomFilterXML(FormDef formDef, IFormReference iFormReference, String s) {
        return null;
    }

    @Override
    public String generateHTML(EControl eControl) {
        return null;
    }

    @Override
    public String introduceWorkItemInWorkFlow(IFormReference iFormReference, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return null;
    }

    @Override
    public String introduceWorkItemInWorkFlow(IFormReference iFormReference, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, WorkdeskModel workdeskModel) {
        return null;
    }

    @Override
    public void formLoad(IFormReference ifr) {
        try {
            Shared.hideSections(ifr);
            Shared.checkBmIsInitiator(ifr);
            Shared.loadLineExecutive(ifr);
            FormApi.clearFields(ifr, new String[]{remarksLocal, decisionHistoryFlagLocal});
            FormApi.setVisible(ifr, new String[]{accountListSection, pepInfoSection, pepCategorySection, pepVerificationSection, decisionSection});
            FormApi.enableFields(ifr, new String[]{bvnLocal, pepCategoryLocal, pepAccountCategoryLocal, lineExecutiveLocal, decisionLocal, remarksLocal, searchBvnBtn});
            FormApi.setMandatory(ifr, new String[]{bvnLocal, pepCategoryLocal, pepAccountCategoryLocal, lineExecutiveLocal, decisionLocal, remarksLocal});
            Shared.setPepMandatoryInfoFields(ifr);
            Shared.checkPepVerification(ifr);
            Shared.checkPepStatus(ifr);
            setDecision(ifr);
            Shared.checkSol(ifr);
        } catch (Exception e) {
            logger.error("Exception occurred in Branch Initiator FormLoad : " + e.getMessage());
        }
    }

    @Override
    public void sendMail(IFormReference ifr) {
        if (Shared.isDecisionSubmit(ifr)) {
            String sendTo = Shared.getUsersMailsInGroup(ifr, LoadProp.pepMailGroup);
            String message = new MailMessage(ifr).getRejectMsg();
            new MailSetup(ifr, FormApi.getWorkItemNumber(ifr), sendTo, empty, LoadProp.mailSubject, message);
        }
    }

    @Override
    public void setDecision(IFormReference ifr) {
        Shared.setDecision(ifr, decisionLocal, new String[]{decSubmit, decDiscard});
    }
}
