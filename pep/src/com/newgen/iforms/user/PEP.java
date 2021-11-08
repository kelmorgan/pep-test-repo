package com.newgen.iforms.user;

import com.newgen.iforms.custom.IFormListenerFactory;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;
import com.newgen.util.Constants;
import com.newgen.util.Shared;
import com.newgen.worksteps.WorkStep1;
import com.newgen.worksteps.WorkStep2;

public class PEP implements IFormListenerFactory, Constants {

	@Override
	public IFormServerEventHandler getClassInstance(IFormReference ifr) {
		IFormServerEventHandler objActivity = null;
		String processName = Shared.getProcessName(ifr);
		String workstep = Shared.getCurrentWorkstep(ifr);
	
		if (Shared.isProcessName(ifr, processName)) {
			switch (workstep) {
				case workstep1:{
					objActivity = new WorkStep1();
					break;
				}
				case workstep2:{
					objActivity = new WorkStep2();
					break;
				}
			}
		}
		return objActivity;
	}

}
