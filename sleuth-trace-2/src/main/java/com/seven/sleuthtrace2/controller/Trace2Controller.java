package com.seven.sleuthtrace2.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Trace2Controller {
	
	private final Logger logger = LoggerFactory.getLogger(Trace2Controller.class);

	@RequestMapping(value = "/trace-2", method = RequestMethod.GET)
	public String trace(HttpServletRequest request) {
		logger.info("===<call trace-2, TraceId={}, SpanId={}>===", 
				request.getHeader("X-B3-TraceId"), request.getHeader("X-B3-SpanId"));
		return "Trace";
	}

}
