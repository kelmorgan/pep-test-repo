package com.newgen.utils.mail;

import com.kelmorgan.ibpsformapis.apis.Form;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.utils.Constants;
import com.newgen.utils.Shared;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class MailMessage {
    private final IFormReference ifr;
    private final Properties properties = new Properties();
    private String message;

    public MailMessage(IFormReference ifr) {
        this.ifr = ifr;
        try {
            InputStream inputStream = new FileInputStream(Constants.mailMessagePath);
            properties.load(inputStream);
        } catch (Exception e){
            System.out.println("Exception Occurred: "+e.getMessage());
        }
    }

    public String getBranchInitiatorMsg(){
        message = properties.getProperty(Constants.branchInitiatorMsg);
        message = message.replaceAll("&<WorkItemName>&", Form.getWorkItemNumber(ifr));
        return message;
    }
    public String getApproveMsg(){
        message = properties.getProperty(Constants.approveMsg);
        message = message.replaceAll("&<WorkItemName>&", Form.getWorkItemNumber(ifr));
        return message;
    }
    public String getRejectMsg(){
        message = properties.getProperty(Constants.rejectMsg);
        message = message.replaceAll("&<WorkItemName>&", Form.getWorkItemNumber(ifr));
        message = message.replaceAll("&<Remarks>&", Shared.getRemarks(ifr));
        return message;
    }
    public String getReturnMsg(){
        message = properties.getProperty(Constants.returnMsg);
        message = message.replaceAll("&<WorkItemName>&", Form.getWorkItemNumber(ifr));
        return message;
    }
    public String getAmlInitiatorMsg(){
        message = properties.getProperty(Constants.amlInitiatorMsg);
        message = message.replaceAll("&<WorkItemName>&", Form.getWorkItemNumber(ifr));
        return message;
    }
    public String getAmlApproveMsg(){
        message = properties.getProperty(Constants.amlApproveMsg);
        message = message.replaceAll("&<WorkItemName>&", Form.getWorkItemNumber(ifr));
        return message;
    }
    public String getAmlRejectMsg(){
        message = properties.getProperty(Constants.amlRejectMsg);
        message = message.replaceAll("&<WorkItemName>&", Form.getWorkItemNumber(ifr));
        message = message.replaceAll("&<Remarks>&", Shared.getRemarks(ifr));
        return message;
    }
}
