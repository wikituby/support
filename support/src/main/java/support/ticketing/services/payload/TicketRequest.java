package support.ticketing.services.payload;

import io.smallrye.common.constraint.NotNull;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import support.ticket.domains.FileResource;
import support.ticket.fileresource.service.payload.FileRequest;

public class TicketRequest {

    @NotNull
    @Schema(required = true, example = "I cant Login")
    public String description;

    @Schema(required = true, example = "Arxcess Banking")
    public String userBusiness;

    public FileResource file;
    public FileRequest fileRequest;

    @Schema(required = true, example = "1")
    public Long categoryId;

    @Schema(required = true, example = "version2")
    public String version;

}

