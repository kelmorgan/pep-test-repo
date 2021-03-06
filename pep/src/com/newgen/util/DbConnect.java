package com.newgen.util;

import com.kelmorgan.ibpsformapis.apis.FormApi;
import com.newgen.iforms.custom.IFormReference;
import org.apache.log4j.Logger;

import java.util.List;

public class DbConnect {
    private static final Logger logger = LogGenerator.getLoggerInstance(DbConnect.class);
    private final IFormReference ifr;
    private final String query;
    public DbConnect(IFormReference ifr, String query){ this.ifr = ifr; this.query = query; }

    public List<List<String>> getData (){
        try {
            logger.info("get data query-- "+query);
            return FormApi.getData(ifr,query);}
        catch (Exception e){ logger.error("Exception Occurred in fetching Data from db-- "+ e.getMessage()); return null; }
    }
    public int saveQuery(){
        try {
            logger.info("save data query-- "+query);
            return FormApi.saveData(ifr,query);
        }
        catch(Exception e){ logger.error("Exception Occurred in saving Data to db-- "+ e.getMessage()); return -1; }
    }
}
