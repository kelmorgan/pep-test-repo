package com.newgen.api.generateXml;

public class RequestXml {

    public static String getBvnValidationRequest(String bvn) {
        return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">\n" +
                "<soapenv:Header/>\n" +
                "<soapenv:Body>\n" +
                "<tem:ValidateBVN>\n" +
                "<!--Optional:-->\n" +
                "<tem:BvnNumber>"+bvn+"</tem:BvnNumber>\n" +
                "</tem:ValidateBVN>\n" +
                "</soapenv:Body>\n" +
                "</soapenv:Envelope>";
    }

    public static String getBvnLinkAcctRequest(String bvn) {
        return "<ServiceCall>fetchRMdetails</ServiceCall><CallType>Individual</CallType><BVN>" + bvn + "</BVN>";
    }

    public static String getSavingAcctRequest(String accountNumber) {
        return "<SBAcctInqRequest>"
                + "<SBAcctInqRq>"
                + "<SBAcctId>"
                + "<AcctId>" + accountNumber + "</AcctId>"
                + "<AcctType>"
                + "<SchmType>SBA</SchmType>"
                + "</AcctType>"
                + "</SBAcctId>"
                + "</SBAcctInqRq>"
                + "</SBAcctInqRequest>";
    }

    public static String getCurrentAcctRequest(String accountNumber) {
        return "<ODAcctInqRequest>"
                + "<ODAcctInqRq>"
                + "<ODAcctId>"
                + "<AcctId>" + accountNumber + "</AcctId>"
                + "<AcctType>"
                + "<SchmType>ODA</SchmType>"
                + "</AcctType>"
                + "</ODAcctId>"
                + "</ODAcctInqRq>"
                + "</ODAcctInqRequest>";
    }

    public static String getSpecialAcctRequest(String accountNumber) {
        return "<CAAcctInqRequest>"
                + "<CAAcctInqRq>"
                + "<CAAcctId>"
                + "<AcctId>" + accountNumber + "</AcctId>"
                + "</CAAcctId>"
                + "</CAAcctInqRq>"
                + "</CAAcctInqRequest>";
    }

    public static String getGenerateTemplateRequest(String wiName, String jtsIp, String jtsPort, String sessionId, String serverIp, String serverPort, String serverName, String cabinetName, String processName, String templateName, String currentWorkStep) {
        return "WI_NAME=" + wiName + "~~JTS_IP=" + jtsIp + "~~JTS_PORT=" + jtsPort + "~~SESSION_ID=" + sessionId + "~~SERVER_IP=" + serverIp + "~~SERVER_PORT=" + serverPort + "~~SERVER_NAME=" + serverName + "~~CABINET_NAME=" + cabinetName + "~~PROCESS_NAME=" + processName + "~~TEMPLATE_NAME=" + templateName + "~~ACTIVITY_NAME=" + currentWorkStep;
    }

}
