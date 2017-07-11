package com.solarsystem.wheaterpredictor.api;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.solarsystem.wheaterpredictor.api.dto.HealthCheckResponse;
import com.solarsystem.wheaterpredictor.api.dto.WheaterStatus;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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

	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal error") })
	@ApiOperation(value = "Get wheater prediction for a day")
	@GET
	@Path("/clima")
	public WheaterStatus getWheaterStatus(@QueryParam("dia") Integer day);

	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal error") })
	@ApiOperation(value = "Get wheater prediction for a period")
	@GET
	@Path("/clima/bulk")
	public Collection<WheaterStatus> getPeriodWheaterStatus(@DefaultValue("0") @QueryParam("desde") Integer from,
			@DefaultValue("3700") @QueryParam("hasta") Integer to,
			@DefaultValue("false") @QueryParam("sorted") Boolean sorted);

}
