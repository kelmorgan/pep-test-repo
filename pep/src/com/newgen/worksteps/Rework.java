package com.newgen.worksteps;

import com.kelmorgan.ibpsformapis.apis.Form;
import com.newgen.iforms.EControl;
import com.newgen.iforms.FormDef;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;
import com.newgen.mvcbeans.model.WorkdeskModel;
import com.newgen.api.serviceHandler.Service;
import com.newgen.utils.Constants;
import com.newgen.utils.LogGenerator;
import com.newgen.utils.Shared;
import com.newgen.utils.SharedI;
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
                    case isLinkedPepEvent:{
                        Shared.linkedPep(ifr);
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
            Form.clearFields(ifr,new String[]{remarksLocal,decisionHistoryFlagLocal});
            Form.setVisible(ifr,new String[]{accountListSection,pepInfoSection,pepCategorySection,pepVerificationSection,decisionSection});
            Form.enableFields(ifr,new String[]{bvnLocal,pepCategoryLocal,pepAccountCategoryLocal,lineExecutiveLocal,decisionLocal,remarksLocal,searchBvnBtn});
            Form.setMandatory(ifr,new String[]{bvnLocal,pepCategoryLocal,pepAccountCategoryLocal,lineExecutiveLocal,decisionLocal,remarksLocal});
            Shared.setPepMandatoryInfoFields(ifr);
            Shared.checkPepVerification(ifr);
            setDecision(ifr);
            Shared.checkSol(ifr);
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
