package support.ticketing.services.payload;

import io.smallrye.common.constraint.NotNull;
import support.ticket.domains.FileResource;
import support.ticket.fileresource.service.payload.FileRequest;

public class TicketRequest {

    @NotNull
    public String description;

    public String userBusiness;

    public FileResource file;
    public FileRequest fileRequest;

    public Long categoryId;

    public String version;

}

