package com.newgen.worksteps;

import com.kelmorgan.ibpsformapis.apis.Form;
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

public class Aml implements IFormServerEventHandler, Constants, SharedI {
    private final Logger logger = LogGenerator.getLoggerInstance(Aml.class);

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
                break;
            case onChangeEvent:
                break;
            case onClickEvent:
                break;
            case onDoneEvent: {
                switch (control) {
                    case updatePepRepoEvent: {
                        Shared.updatePepRepo(ifr);
                    }
                    break;
                    case decisionHistoryEvent: {
                        Shared.setDecisionHistory(ifr);
                        break;
                    }
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
            Form.clearFields(ifr, new String[]{remarksLocal, decisionHistoryFlagLocal});
            if (Shared.isPrevWs(ifr, amlInitiatorWs)) {
                Shared.setRepoView(ifr);
                Shared.setDecision(ifr, decisionLocal, new String[]{decApprove, decDiscard});
            } else {
                Shared.checkPepVerification(ifr);
                Form.setVisible(ifr, new String[]{accountListSection, pepInfoSection, pepCategorySection, pepVerificationSection, decisionSection});
                Form.enableFields(ifr, new String[]{decisionLocal, remarksLocal});
                Form.setMandatory(ifr, new String[]{decisionLocal, remarksLocal});
                setDecision(ifr);
            }
        } catch (Exception e) {
            logger.error("Exception occurred in Aml FormLoad : " + e.getMessage());
        }
    }

    @Override
    public void sendMail(IFormReference ifr) {
        MailMessage mailMessage = new MailMessage(ifr);
        String message;
        String sendTo = Shared.getUsersMailsInGroup(ifr, LoadProp.pepMailGroup);
        if (Shared.isPrevWs(ifr, amlInitiatorWs)) {
            if (Shared.isDecisionApprove(ifr)) {
                //sendTo = Shared.getUsersMailsInGroup(ifr, amlGroupName);
                message = mailMessage.getApproveMsg();
                new MailSetup(ifr, Form.getWorkItemNumber(ifr), sendTo, empty, LoadProp.mailSubject, message);
            } else if (Shared.isDecisionDiscard(ifr)) {
                sendTo = Shared.getUsersMailsInGroup(ifr, amlGroupName);
                message = mailMessage.getAmlRejectMsg();
                new MailSetup(ifr, Form.getWorkItemNumber(ifr), sendTo, empty, LoadProp.mailSubject, message);
            }
        } else {
            if (Shared.isDecisionApprove(ifr)) {
                //sendTo = Shared.getUsersMailsInGroup(ifr, lineExecGroupName);
                message = mailMessage.getApproveMsg();
                new MailSetup(ifr, Form.getWorkItemNumber(ifr), sendTo, empty, LoadProp.mailSubject, message);
            } else if (Shared.isDecisionReturn(ifr)) {
               // sendTo = Shared.getUsersMailsInGroup(ifr, rmGroupLabel + Shared.getUserSol(ifr));
                message = mailMessage.getRejectMsg();
                new MailSetup(ifr, Form.getWorkItemNumber(ifr), sendTo, empty, LoadProp.mailSubject, message);
            }
        }
    }

    @Override
    public void setDecision(IFormReference ifr) {
        Shared.setDecision(ifr, decisionLocal, new String[]{decApprove, decReturn});
    }
}
