package com.newgen.util.mail;

import com.kelmorgan.ibpsformapis.apis.FormApi;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.util.Constants;
import com.newgen.util.Shared;

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
        } catch (Exception e) {
            System.out.println("Exception Occurred: " + e.getMessage());
        }
    }

    public String getBranchInitiatorMsg() {
        message = properties.getProperty(Constants.branchInitiatorMsg);
        return message.replaceAll("&<WorkItemName>&", FormApi.getWorkItemNumber(ifr));
    }

    public String getApproveMsg() {
        message = properties.getProperty(Constants.approveMsg);
        return message.replaceAll("&<WorkItemName>&", FormApi.getWorkItemNumber(ifr));
    }

    public String getRejectMsg() {
        message = properties.getProperty(Constants.rejectMsg);
        message = message.replaceAll("&<WorkItemName>&", FormApi.getWorkItemNumber(ifr));
        return message.replaceAll("&<Remarks>&", Shared.getRemarks(ifr));
    }

    public String getReturnMsg() {
        message = properties.getProperty(Constants.returnMsg);
        return message.replaceAll("&<WorkItemName>&", FormApi.getWorkItemNumber(ifr));
    }

    public String getAmlInitiatorMsg() {
        message = properties.getProperty(Constants.amlInitiatorMsg);
        return message.replaceAll("&<WorkItemName>&", FormApi.getWorkItemNumber(ifr));
    }

    public String getAmlApproveMsg() {
        message = properties.getProperty(Constants.amlApproveMsg);
        return message.replaceAll("&<WorkItemName>&", FormApi.getWorkItemNumber(ifr));
    }

    public String getAmlRejectMsg() {
        message = properties.getProperty(Constants.amlRejectMsg);
        message = message.replaceAll("&<WorkItemName>&", FormApi.getWorkItemNumber(ifr));
        return message.replaceAll("&<Remarks>&", Shared.getRemarks(ifr));
    }
}
