package com.newgen.api.generateXml;

public class RequestXml {

    public static String getBvnLinkAcctRequest(String bvn){
        return "<ServiceCall>fetchRMdetails</ServiceCall><CallType>Individual</CallType><BVN>"+bvn+"</BVN>";
    }

    public static String getSavingAcctRequest(String accountNumber){
        return "<SBAcctInqRequest>"
                +		"<SBAcctInqRq>"
                +			"<SBAcctId>"
                +				"<AcctId>"+accountNumber+"</AcctId>"
                +				"<AcctType>"
                +					"<SchmType>SBA</SchmType>"
                +				"</AcctType>"
                +			"</SBAcctId>"
                +		"</SBAcctInqRq>"
                +	"</SBAcctInqRequest>";
    }
    public static String getCurrentAcctRequest(String accountNumber){
        return "<ODAcctInqRequest>"
                +		"<ODAcctInqRq>"
                +			"<ODAcctId>"
                +				"<AcctId>"+accountNumber+"</AcctId>"
                +				"<AcctType>"
                +					"<SchmType>ODA</SchmType>"
                +				"</AcctType>"
                +			"</ODAcctId>"
                +		"</ODAcctInqRq>"
                +	"</ODAcctInqRequest>";
    }
    public static String getSpecialAcctRequest(String accountNumber){
        return "<CAAcctInqRequest>"
                +		"<CAAcctInqRq>"
                +			"<CAAcctId>"
                +				"<AcctId>"+accountNumber+"</AcctId>"
                +			"</CAAcctId>"
                +		"</CAAcctInqRq>"
                +	"</CAAcctInqRequest>";
    }

}
