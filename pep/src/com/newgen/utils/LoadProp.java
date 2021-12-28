package com.newgen.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class LoadProp implements Constants {
    public static String mailFrom;
    public static String processDefId;
    public static String activateAo;
    public static String aoProcessDefId;
    public static String aoQueueId;
    public static String cabinetName;
    public static String templateName;
    public static String templatePort;
    public static String serverIp;
    public static String serverPort;
    public static String jtsIp;
    public static String jtsPort;
    public static String serverName;
    public static String mailSubject;
    public static String pepMailGroup;
    public static String logPath;
    public static String taxIdDoc;


    static {
        try {
            Properties properties = new Properties();
            InputStream in = new FileInputStream(configPath);
            properties.load(in);

            activateAo = properties.getProperty(activateAoField);
            aoQueueId = properties.getProperty(aoQueueIdField);
            aoProcessDefId = properties.getProperty(aoProcessDefIdField);
            mailFrom = properties.getProperty(mailFromField);
            processDefId = properties.getProperty(processDefIdField);
            cabinetName = properties.getProperty(cabinetNameField);
            templateName = properties.getProperty(templateNameField);
            templatePort = properties.getProperty(templatePortField);
            serverIp = properties.getProperty(serverIpField);
            serverName = properties.getProperty(serverNameField);
            serverPort = properties.getProperty(serverPortField);
            jtsIp = properties.getProperty(jtsIpField);
            jtsPort = properties.getProperty(jtsPortField);
            mailSubject = properties.getProperty(mailSubjectField);
            pepMailGroup = properties.getProperty(pepMailGroupField);
            logPath = properties.getProperty(logPathField);
        } catch (Exception e) {
            System.out.println("Exception in Pep Process LoadProd Class: " + e.getMessage());
        }
    }
}
