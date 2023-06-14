package support.ticketing.services.payload;

import io.smallrye.common.constraint.NotNull;
import support.ticket.domains.FileResource;

public class TicketUpdateRequest {

    @NotNull
    public String description;

    public String userBusiness;

    public FileResource file;

    public Long categoryId;

    public String version;

}