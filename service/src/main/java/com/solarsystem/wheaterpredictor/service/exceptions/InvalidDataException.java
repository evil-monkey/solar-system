package com.solarsystem.wheaterpredictor.service.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class InvalidDataException extends WebApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -209924355611162438L;
	
	
    public InvalidDataException(Exception e) {
            super(Response
                            .status(Response.Status.BAD_REQUEST)
                            .entity(ExceptionMessageBuilder.getInstance()
                                            .status(400).addField("error", e).build())
                            .type(MediaType.APPLICATION_JSON_TYPE).build());

    }   

    public InvalidDataException(String message) {
            super(Response
                            .status(Response.Status.BAD_REQUEST)
                            .entity(ExceptionMessageBuilder.getInstance()
                                            .status(400).addField("error", message).build())
                            .type(MediaType.APPLICATION_JSON_TYPE).build());
    }   

   

}
