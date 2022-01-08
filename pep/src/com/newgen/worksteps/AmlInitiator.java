package com.newgen.worksteps;

import com.kelmorgan.ibpsformapis.apis.FormApi;
import com.newgen.api.serviceHandler.Service;
import com.newgen.iforms.EControl;
import com.newgen.iforms.FormDef;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;
import com.newgen.mvcbeans.model.WorkdeskModel;
import com.newgen.util.Constants;
import com.newgen.util.LoadProp;
import com.newgen.util.Shared;
import com.newgen.util.SharedI;
import com.newgen.util.mail.MailMessage;
import com.newgen.util.mail.MailSetup;
import org.json.simple.JSONArray;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AmlInitiator implements IFormServerEventHandler, SharedI, Constants {
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
            case onChangeEvent: {
                switch (control) {
                    case setRepoEvent: {
                        return Shared.setRepoInfo(ifr);
                    }
                }
            }
            break;
            case onClickEvent: {
                switch (control) {
                    case apiEvent: {
                        return new Service(ifr).getAccountList();
                    }
                }
            }
            break;
            case onDoneEvent: {
                switch (control) {
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
        Shared.hideSections(ifr);
        FormApi.setFields(ifr, new String[]{currentWsLocal, previousWsLocal}, new String[]{FormApi.getCurrentWorkStep(ifr), na});
        Shared.setInitiatorDetails(ifr);
        Shared.setRepoView(ifr);
        setDecision(ifr);
    }

    @Override
    public void sendMail(IFormReference ifr) {
        if (Shared.isDecisionSubmit(ifr)) {
            String sendTo = Shared.getUsersMailsInGroup(ifr, LoadProp.pepMailGroup);
            String message = new MailMessage(ifr).getAmlInitiatorMsg();
            new MailSetup(ifr, FormApi.getWorkItemNumber(ifr), sendTo, empty, LoadProp.mailSubject, message);
        }
    }

    @Override
    public void setDecision(IFormReference ifr) {
        Shared.setDecision(ifr, decisionLocal, new String[]{decSubmit, decDiscard});
    }
}
