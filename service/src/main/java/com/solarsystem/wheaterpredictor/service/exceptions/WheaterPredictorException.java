package com.solarsystem.wheaterpredictor.service.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class WheaterPredictorException extends WebApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7483280955119362865L;

	public WheaterPredictorException(String message) {
            super(Response
                            .status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity(ExceptionMessageBuilder.getInstance()
                                            .status(500).addField("error", message).build())
                            .type(MediaType.APPLICATION_JSON_TYPE).build());
    }   

}
