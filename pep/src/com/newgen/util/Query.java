package com.newgen.util;

public class Query {

    public static String updatePepRepo(String wiName, String accountNumber){
        return "update pep_master set winame = '"+wiName+"' where accountnumber = '"+accountNumber+"'";
    }
    public static String getPepRepoDetails(String accountNumber){
        return "select solid,branchname, accountname,pepname, address, office_position, nature_of_business, acct_opn_date  from pep_master where accountnumber = '"+accountNumber+"'";
    }
    public static String getAcoGroup(String id){
        return "select aco_group from usr_0_fbn_aco_groupname where aco_id = '"+id+"'";
    }
    public static  String getAcoId(String sol){
        return "select aco_id from usr_0_fbn_aco_mapping where sole_id = '"+sol+"'";
    }
    public static String getLineExecutives(){
        return "select le_name from usr_0_fbn_lineexecutive";
    }
    public static String getLineExecutivesId(String name){
        return "select le_id from usr_0_fbn_lineexecutive where upper(le_name) = upper('"+name+"')";
    }

    public static String getIsMemberOfSol(String userId, String sol){
        return "select count(*) from usr_0_fbn_usr_branch_mapping where upper(user_id) = upper('"+userId+"') and sole_id = '"+sol+"'";
    }
    public static String getIsUserMemberOfGroup(String user, String groupName){
        return "select count (username) from pdbuser where upper(username)= upper('"+user+"')  and userindex in (select userindex from pdbgroupmember where groupindex = (select groupindex from pdbgroup where groupname = '"+groupName+"'))";
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
