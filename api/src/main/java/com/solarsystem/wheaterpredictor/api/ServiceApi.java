package com.solarsystem.wheaterpredictor.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.solarsystem.wheaterpredictor.api.dto.HealthCheckResponse;

/**
 * 
 * @author projas
 * 
 */
@Api(value = "wheater-predictor-service")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/")
public interface ServiceApi {

	static final String SERVICE_NAME = "wheater-predictor-service";

	static final String VERSION = "0.0.1";

	@ApiOperation(value = "Keep-alive")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Alive!") })
	@Path("/keep-alive")
	@GET
	public void keepAlive();

	@ApiOperation(value = "Return service name, version and dependencies status", response = Object.class)
	@Path("/health-check")
	@GET
	public HealthCheckResponse healthCheck();

	@ApiOperation(value = "Service version", response = Object.class)
	@Path("/version")
	@GET
	public Object getVersion();
/*
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Operaror has been registered"),
			@ApiResponse(code = 400, message = "Bad Request/Invalid message"),
			@ApiResponse(code = 500, message = "Internal error") })
	@ApiOperation(value = "Register operator")
	@Path("/operators")
	@POST
	public void registerOperator(OperatorDTO operator);

	@ApiResponses(value = { @ApiResponse(code = 201, message = "Call has been dispatched"),
			@ApiResponse(code = 400, message = "Bad Request/Invalid message"),
			@ApiResponse(code = 500, message = "Internal error") })
	@ApiOperation(value = "Dispatch call")
	@Path("/calls")
	@POST
	public void dispatchCall(CallDTO call);

	@ApiResponses(value = { @ApiResponse(code = 201, message = "Notificable service has been subscribed"),
			@ApiResponse(code = 400, message = "Bad Request/Invalid message"),
			@ApiResponse(code = 500, message = "Internal error") })
	@ApiOperation(value = "Susbcribe")
	@Path("/subscribers")
	@POST
	public void subscribe(NotificableServiceDTO service);

	@ApiResponses(value = { @ApiResponse(code = 204, message = "OK"),
			@ApiResponse(code = 400, message = "Bad Request/Invalid message"),
			@ApiResponse(code = 404, message = "Not found"), @ApiResponse(code = 500, message = "Internal error") })
	@ApiOperation(value = "Unregister operator")
	@Path("/operators/{username}")
	@DELETE
	public void unregisterOperator(@PathParam("username") String username);

	@ApiResponses(value = { @ApiResponse(code = 204, message = "OK"),
			@ApiResponse(code = 400, message = "Bad Request/Invalid message"),
			@ApiResponse(code = 404, message = "Not found"), @ApiResponse(code = 500, message = "Internal error") })
	@ApiOperation(value = "Unregister operator")
	@Path("/subscribers/{servicename}")
	@DELETE
	public void unsubscribeService(@PathParam("servicename") String serviceName);
	
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal error") })
	@ApiOperation(value = "Get calls in progress")
	@Path("/inprogress")
	@GET
	public Collection<CallDTO> getInProgress();
*/	
	
}
