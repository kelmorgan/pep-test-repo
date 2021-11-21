package com.newgen.worksteps;

import com.newgen.iforms.EControl;
import com.newgen.iforms.FormDef;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;
import com.newgen.mvcbeans.model.WorkdeskModel;
import com.newgen.util.Constants;
import com.newgen.util.LogGenerator;
import com.newgen.util.Shared;
import com.newgen.util.SharedI;
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
        switch (event){
            case onLoadEvent:
                break;
            case onChangeEvent:
                break;
            case onClickEvent:
                break;
            case onDoneEvent:{
                switch (control){
                    case updatePepRepoEvent:{
                        Shared.updatePepRepo(ifr);
                    }
                    break;
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
            Shared.clearFields(ifr,new String[]{remarksLocal,decisionHistoryFlagLocal});
            if (Shared.isPrevWs(ifr,amlInitiatorWs)){
                Shared.setRepoView(ifr);
                Shared.setDecision(ifr,decisionLocal,new String[]{decApprove,decDiscard});
            }
            else {
                Shared.checkPepVerification(ifr);
                Shared.setVisible(ifr, new String[]{accountListSection, pepInfoSection, pepCategorySection, pepVerificationSection, decisionSection});
                Shared.enableFields(ifr, new String[]{decisionLocal, remarksLocal});
                Shared.setMandatory(ifr, new String[]{decisionLocal, remarksLocal});
                setDecision(ifr);
            }
        }
        catch (Exception e){
            logger.error("Exception occurred in Aml FormLoad : "+ e.getMessage());
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
