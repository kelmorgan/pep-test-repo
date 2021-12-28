package com.newgen.iforms.user;

import com.newgen.iforms.custom.IFormListenerFactory;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;
import com.newgen.utils.Constants;
import com.newgen.utils.LogGenerator;
import com.newgen.utils.Shared;
import com.newgen.worksteps.*;
import org.apache.log4j.Logger;

public class PEP implements IFormListenerFactory, Constants {
	private final Logger logger = LogGenerator.getLoggerInstance(PEP.class);

	@Override
	public IFormServerEventHandler getClassInstance(IFormReference ifr) {
		logger.info("Code got here!!");
		IFormServerEventHandler objActivity = null;
		try {

			//String workStep = Form.getCurrentWorkStep(ifr);
			String workStep = branchInitiatorWs;
			logger.info("workStep: " + workStep);

			if (Shared.isProcessName(ifr, pepProcessName)) {
				switch (workStep) {
					case branchInitiatorWs: {
						objActivity = new BranchInitiator();
						break;
					}
					case branchVerifierWs: {
						objActivity = new BranchVerifier();
						break;
					}
					case acoWs: {
						objActivity = new Aco();
						break;
					}
					case amlWs: {
						objActivity = new Aml();
						break;
					}
					case lineExecutiveWs: {
						objActivity = new LineExecutive();
						break;
					}
					case ccoWs: {
						objActivity = new Cco();
						break;
					}
					case reworkWs: {
						objActivity = new Rework();
						break;
					}
					case amlInitiatorWs: {
						objActivity = new AmlInitiator();
						break;
					}
					case exitWs:
					case discardWs:
					case queryWs: {
						objActivity = new Exit();
						break;
					}

				}
			}
		}
		catch (Exception e){
			logger.error("Exception occurred in pep home class "+ e.getMessage());
		}
		return objActivity;
	}

}
