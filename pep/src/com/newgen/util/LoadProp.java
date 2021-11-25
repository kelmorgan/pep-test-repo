package com.newgen.util;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.Properties;

public class LoadProp implements  Constants {
    private static final Logger logger = LogGenerator.getLoggerInstance(LoadProp.class);
	public static String mailFrom;
	public static String processDefId;
    public static String serverIp;
    public static String activateAo;
    public static String aoProcessDefId;
    public static String aoQueueId;


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
        }
        catch  (UnsupportedEncodingException ex){
            ex.printStackTrace();
            logger.error("Error occurred in load property file - Unsupported Exception -- "+ ex.getMessage() );
        }
        catch (FileNotFoundException ex){
            ex.printStackTrace();
            logger.error("Error occurred in load property file - FileNotFoundException Exception-- "+ ex.getMessage());
        }
        catch (IOException ex){
            ex.printStackTrace();
            logger.error("Error occurred in load property file - IOException Exception-- "+ ex.getMessage());
        }
    }
}
