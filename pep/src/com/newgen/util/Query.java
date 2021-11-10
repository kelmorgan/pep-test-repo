package com.newgen.util;

public class Query {
    public static String getLineExecutives(){
        return "select le_id, le_name from usr_0_fbn_lineexecutive";
    }
    public static String getIsMemberOfSol(String userId, String solId){
        return "select count(*) from usr_0_fbn_usr_branch_mapping where user_id = '"+userId+"' and sole_id = '"+solId+"'";
    }
    public static String getIsUserMemberOfGroup(String userId, String groupName){
        return "select count (username) from pdbuser where upper(username)= upper('"+userId+"')  and userindex in (select userindex from pdbgroupmember where groupindex = (select groupindex from pdbgroup where groupname = '"+groupName+"'))";
    }
    public static String getSolQuery(String userName) {
        return "select sole_id from usr_0_fbn_usr_branch_mapping where upper(user_id) = upper('" + userName + "')";
    }
    public static String getUserDetailsQuery(String userName) {
        return "select sole_id, branch_name from usr_0_fbn_usr_branch_mapping where upper(user_id) = upper('" + userName + "')";
    }
    public static String getUsersInGroup(String groupName) {
        return "select username from pdbuser where userindex in (select userindex from pdbgroupmember where groupindex = (select groupindex from PDBGroup where GroupName='" + groupName + "'))";
    }
    public static String getMailQuery(String wiName, String sendMail, String copyMail, String mailSubject, String mailMessage) {
        return "insert into wfmailqueuetable (" +
                "mailfrom," +
                "mailto," +
                "mailcc," +
                "mailsubject," +
                "mailmessage," +
                "mailcontenttype," +
                "mailpriority," +
                "insertedby," +
                "mailactiontype," +
                "insertedtime," +
                "processdefid," +
                "processinstanceid," +
                "workitemid," +
                "activityid," +
                "mailstatus) " +
                "values (" +
                "'" + LoadProp.mailFrom + "'," +
                "'" + sendMail + "'," +
                "'" + copyMail + "'," +
                "'" + mailSubject + "'," +
                "'" + mailMessage + "'," +
                "'text/html;charset=UTF-8'," +
                "1," +
                "'System'," +
                "'TRIGGER'," +
                "SYSDATE," +
                "" + LoadProp.processDefId + "," +
                "'" + wiName + "'," +
                "1," +
                "1," +
                "'N')";
    }
}
