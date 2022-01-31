package com.newgen.tests;

import com.kelmorgan.ibpsformapis.apis.FormApi;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.util.Constants;
import com.newgen.util.LoadProp;
import com.newgen.util.LogGenerator;
import com.newgen.util.Shared;
import com.newgen.util.mail.MailMessage;
import com.newgen.util.mail.MailSetup;
import org.apache.log4j.Logger;

public class MailTests {

    private final IFormReference ifr;
    private final Logger logger = LogGenerator.getLoggerInstance(MailTests.class);

    public MailTests(IFormReference ifr) {
        this.ifr = ifr;
    }

    public void mainCall() {
        sendMail();
    }

    private void sendMail() {
        try {
            String groupName = LoadProp.pepMailGroup;
            logger.info("groupName: " + groupName);
            String sendTo = Shared.getUsersMailsInGroup(ifr, groupName);
            logger.info("sendTo: " + sendTo);

            String message = new MailMessage(ifr).getBranchInitiatorMsg();
            logger.info("message to send: " + message);
            new MailSetup(ifr, FormApi.getWorkItemNumber(ifr), sendTo, Constants.empty, LoadProp.mailSubject, message);
        } catch (Exception e) {
            logger.error("Exception Occurred: " + e.getMessage());
        }
    }
}
