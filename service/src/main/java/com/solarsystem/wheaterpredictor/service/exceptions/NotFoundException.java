package com.solarsystem.wheaterpredictor.service.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class NotFoundException extends WebApplicationException {
	
	/** 
     * 
     */
    private static final long serialVersionUID = 3660999042017692867L;

    public NotFoundException() {
            super(Response.status(Response.Status.NOT_FOUND)
                            .entity(ExceptionMessageBuilder.getInstance().status(404).message("Entity not found.").build())
                            .type(MediaType.APPLICATION_JSON_TYPE).build());
    }   

    public NotFoundException(String message) {
            super(Response.status(Response.Status.NOT_FOUND)
                            .entity(ExceptionMessageBuilder.getInstance().status(404).message(message).build())
                            .type(MediaType.APPLICATION_JSON_TYPE).build());
    }   
  
}
