package com.newgen.utils;

import com.kelmorgan.ibpservices.initializer.IBPSServiceHandler;
import com.kelmorgan.ibpservices.initializer.ServiceInitializer;
import com.kelmorgan.ibpservices.initializer.ServiceInjector;
import com.kelmorgan.ibpsformapis.apis.Form;
import com.newgen.iforms.custom.IFormReference;
import org.apache.log4j.Logger;

public class CreateAoWorkItem {
    private final Logger logger = LogGenerator.getLoggerInstance(CreateAoWorkItem.class);
    private final IFormReference ifr;

    public CreateAoWorkItem(IFormReference ifr) {
        this.ifr = ifr;
    }

    public String createWorkItem() {
        try {
            if (isAoActive() && Shared.isPrevWs(ifr, Constants.ccoWs)) {
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
                if (Shared.isNotEmpty(aoWiName))
                    new DbConnect(ifr, Query.setAoDetails(aoWiName, Form.getWorkItemNumber(ifr))).saveQuery();

                return "WorkItem successfully created on AO process. RefNo: " + aoWiName;
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

}
