package com.newgen.utils;

import com.kelmorgan.ibpsformapis.apis.FormApi;
import com.newgen.iforms.custom.IFormReference;
import org.apache.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class GenerateDocument implements Constants{
    private static final Logger logger = LogGenerator.getLoggerInstance(GenerateDocument.class);
    private final IFormReference ifr;

    public GenerateDocument(IFormReference ifr) {
        this.ifr = ifr;
    }

    public  String generateDoc () {
        try {
            String request = getGenerateTemplateRequest(FormApi.getWorkItemNumber(ifr),
                    LoadProp.jtsIp,LoadProp.jtsPort,FormApi.getSessionId(ifr),LoadProp.serverIp,LoadProp.serverPort,
                    LoadProp.serverName,LoadProp.cabinetName,FormApi.getProcessName(ifr),LoadProp.templateName, FormApi.getCurrentWorkStep(ifr));

            logger.info("Request for template generation: "+ request);

            int templatePort = Integer.parseInt(LoadProp.templatePort);
            String response;
            try {
             response = callSocketServer(LoadProp.serverIp,templatePort,request);
            logger.info("Response from template generation: "+ response);



            } catch (Exception e) {
                response = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
                        + "<message>\n"
                        + "<ErrorCode>1</ErrorCode>\n"
                        + "<ErrorDesc>Error while generating template.</ErrorDesc>\n"
                        + "</message>";
            }

            Shared.setDocFlag(ifr);
            return response;
        }
        catch (Exception e){
            logger.error("Exception occurred in generateDoc Method: "+ e.getMessage());
        }
        return  null;
    }

    private static String callSocketServer(String serverIp,int templatePort, String request) {
        final String SS_EXEC_ERROR_MSG="Error from Call Client Socket Server while Web-Service execution";
        final String SS_CONN_ERROR_MSG="Could not connect to Call Client Socket Server";
        String responseXml;
        String sTemp = "";
        try
        {
            logger.info("from call SocketServer try block");
            String tempResponseXml;
            Socket client = new Socket(serverIp, templatePort);
            client.setSoTimeout(300000);

            try
            {
                DataOutputStream outData = new DataOutputStream(client.getOutputStream());
                byte[] dataByteArr = request.getBytes(StandardCharsets.UTF_8);
                outData.writeInt(dataByteArr.length);
                outData.write(dataByteArr);
                DataInputStream in = new DataInputStream(client.getInputStream());
                int dataLength = in.readInt();
                byte[] data = new byte[dataLength];
                in.readFully(data);
                tempResponseXml = new String(data, StandardCharsets.UTF_8);
                logger.info("CommonMethods : tempResponseXml : "+tempResponseXml);
                in.close();
            } catch (IOException e)
            {
                tempResponseXml = SS_CONN_ERROR_MSG;
            } catch (Exception e)
            {
                tempResponseXml = SS_EXEC_ERROR_MSG;
            }

            if(tempResponseXml.length() == 0)
            {
                responseXml= "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                        "<message>\n" +
                        "<MainCode>-1</MainCode>\n" +
                        "<EDESC>No Response Received from Call Client Socket Server.</EDESC>\n" +
                        "</message>";

            }
            else
            {
                if (tempResponseXml.equals(SS_EXEC_ERROR_MSG)|| tempResponseXml.equals(SS_CONN_ERROR_MSG))
                {
                    responseXml="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
                            +"<message>\n"
                            + "<MainCode>-1</MainCode>\n"
                            + "<EDESC>"+tempResponseXml+"</EDESC>\n"
                            + "</message>";
                }
                else
                {
                    responseXml=tempResponseXml;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            responseXml="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<message>\n" +
                    "<MainCode>-1</MainCode>\n" +
                    "<EDESC>Not able to Connect with Socket Server.</EDESC>\n" +
                    "</message>";
        }

        return sTemp+responseXml;
    }

    private String getGenerateTemplateRequest(String wiName, String jtsIp,String jtsPort, String sessionId, String serverIp, String serverPort,String serverName, String cabinetName, String processName,String templateName,String currentWorkStep){
        return "WI_NAME=" + wiName + "~~JTS_IP="+jtsIp+"~~JTS_PORT="+jtsPort+"~~SESSION_ID=" + sessionId + "~~SERVER_IP=" + serverIp + "~~SERVER_PORT=" + serverPort + "~~SERVER_NAME="+serverName+"~~CABINET_NAME=" + cabinetName + "~~PROCESS_NAME=" + processName + "~~TEMPLATE_NAME=" + templateName + "~~ACTIVITY_NAME=" + currentWorkStep;
    }
}
