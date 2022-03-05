package com.newgen.util;

import com.kelmorgan.ibpsformapis.apis.FormApi;
import com.kelmorgan.templategenerationhandler.service.Generate;
import com.kelmorgan.templategenerationhandler.service.GenerateDocumentHandler;
import com.newgen.iforms.custom.IFormReference;
import org.apache.log4j.Logger;

public class GenerateDocument implements Constants{
    private static final Logger logger = LogGenerator.getLoggerInstance(GenerateDocument.class);
    private final IFormReference ifr;

    public GenerateDocument(IFormReference ifr) {
        this.ifr = ifr;
    }

    public String generateDocument(){
        return handler().generateDocument();
    }

    private GenerateDocumentHandler handler (){
        return  new Generate(FormApi.getWorkItemNumber(ifr),
                LoadProp.jtsIp,LoadProp.jtsPort,FormApi.getSessionId(ifr),LoadProp.serverIp,LoadProp.serverPort,
                LoadProp.serverName,LoadProp.cabinetName,FormApi.getProcessName(ifr),LoadProp.templateName, FormApi.getCurrentWorkStep(ifr),Integer.parseInt(LoadProp.templatePort));
    }

}
