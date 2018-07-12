package com.cloudling.offer.error;

import org.beetl.core.ErrorHandler;
import org.beetl.core.exception.BeetlException;

import java.io.Writer;

public class WebErrorHandler implements ErrorHandler {

	@Override
	public void processExcption(BeetlException arg0, Writer arg1) {
		
			throw arg0;
			
		
	}

}
