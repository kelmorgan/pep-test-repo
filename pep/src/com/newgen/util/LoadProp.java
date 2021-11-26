package com.newgen.util;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.Properties;

public class LoadProp implements  Constants {
    private static final Logger logger = LogGenerator.getLoggerInstance(LoadProp.class);
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


    static {
        try {
            logger.info("Start loading properties file");
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
        }
        catch  (Exception e){
            logger.error("Error occurred in load property file-- "+ e.getMessage() );
        }
    }
}
