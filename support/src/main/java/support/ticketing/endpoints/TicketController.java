package support.ticketing.endpoints;

import io.vertx.core.http.HttpServerRequest;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import support.configuration.handler.ResponseMessage;
import support.ticket.domains.Ticket;
import support.ticket.domains.repository.TicketRepository;
import support.ticketing.services.TicketService;
import support.ticketing.services.payload.TicketFilterRequest;
import support.ticketing.services.payload.TicketRequest;
import support.ticketing.services.payload.TicketUpdateRequest;

import java.util.List;
@Path("ticket")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Ticket - Resource", description = "This will create a new ticket")

public class TicketController {
    @Inject
    TicketService service;
    @POST
    @Operation(summary = "create a new ticket", description = "This will create a new ticket.")
    public Response createTicket(TicketRequest request, @Context HttpServerRequest httpRequest, @Context SecurityContext ctx) {
        return Response.ok(new ResponseMessage("Created Successfully",service.create(request,httpRequest,ctx))).build();
    }
    @PUT
    @Path("{id}")
    @Operation(summary = "update a ticket", description = "This will update an existing ticket.")
    public Response updateTicket(@PathParam("id") Long id, TicketUpdateRequest request) {
        return Response.ok(new ResponseMessage("Updated Successfully",service.updateTicket(id,request))).build();
    }
    @GET
    @Operation(summary = "Filter tickets by userBusiness, email and applicationType", description = "This will update an existing ticket.")
    public Response filterTickets(TicketFilterRequest filterRequest) {
        return Response.ok(new ResponseMessage("filtered Successfully",service.findTicketsByFilter(filterRequest))).build();
    }

}
