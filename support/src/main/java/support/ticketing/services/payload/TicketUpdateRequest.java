package support.ticketing.services.payload;

import io.smallrye.common.constraint.NotNull;
import support.ticket.domains.FileResource;
import support.ticket.fileresource.service.payload.FileRequest;

public class TicketUpdateRequest {

    @NotNull
    public String description;

    public String userBusiness;

    public FileResource file;

    public Long categoryId;

    public FileRequest fileRequest;
}