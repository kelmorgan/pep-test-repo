package com.newgen.util;

import com.kelmorgan.ibpservices.initializer.IBPSServiceHandler;
import com.kelmorgan.ibpservices.initializer.ServiceInitializer;
import com.kelmorgan.ibpservices.initializer.ServiceInjector;
import com.kelmorgan.ibpsformapis.apis.FormApi;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.util.mail.MailMessage;
import com.newgen.util.mail.MailSetup;
import org.apache.log4j.Logger;

public class CreateAoWorkItem {
    private final Logger logger = LogGenerator.getLoggerInstance(CreateAoWorkItem.class);
    private final IFormReference ifr;

    public CreateAoWorkItem(IFormReference ifr) {
        this.ifr = ifr;
    }

    public String createWorkItem() {
        try {
            if (isValid()) {
                String otherName = Shared.isNotEmpty(Shared.getOtherName(ifr)) ? Shared.getOtherName(ifr) : Constants.empty;
                String attributes = "<BVNNO>" + Shared.getBvn(ifr) + "</BVNNO><R_SNAME>" + Shared.getSurName(ifr) + "</R_SNAME><R_FNAME>" + Shared.getFirstName(ifr) + "</R_FNAME><R_ONAME>" + otherName + "</R_ONAME>";
                logger.info("attribute: " + attributes);
                String processDefId = LoadProp.aoProcessDefId;
                logger.info("processDefId: " + processDefId);
                String queueId = LoadProp.aoQueueId;
                logger.info("queueId: " + queueId);
                IBPSServiceHandler service = initializedService();
                String aoWiName = service.createWorkItem(attributes, processDefId, queueId, Constants.flagN);
                logger.info("Ao workItem created: " + aoWiName);
                service.disconnectCabinet();
                if (Shared.isNotEmpty(aoWiName)) {
                    FormApi.setFields(ifr,Constants.aoWiNameFlagLocal,Constants.flagY);
                    new DbConnect(ifr, Query.setAoDetails(aoWiName, FormApi.getWorkItemNumber(ifr))).saveQuery();
                    sendMail();
                    return "WorkItem successfully created on AO process. RefNo: " + aoWiName;
                } else return "Something went wrong in creating AO WorkItem. " + Constants.exceptionMsg;
            }
        } catch (Exception e) {
            logger.error("Exception occurred in createAoWiName method: " + e.getMessage());
            return "Exception occurred in createAoWiName method. " + Constants.exceptionMsg;
        }
        return null;
    }

    private IBPSServiceHandler initializedService() {
        ServiceInjector serviceInjector = new ServiceInitializer();
        return serviceInjector.getService(Constants.configPath);
    }

    private boolean isAoActive() {
        return LoadProp.activateAo.equalsIgnoreCase(Constants.flagY);
    }

    private boolean isValid() {
        return isAoActive() && Shared.isAONotGenerated(ifr) && Shared.isPrevWs(ifr, Constants.ccoWs) && Shared.isPepCategory(ifr, Constants.pepCategoryNew);
    }

    private void sendMail() {
        String sendTo = FormApi.getLoginUser(ifr) + Constants.endMail;
        String message = new MailMessage(ifr).getAoWorkItemMsg();
        new MailSetup(ifr, FormApi.getWorkItemNumber(ifr), sendTo, Constants.empty, LoadProp.mailSubject, message);
    }

}
