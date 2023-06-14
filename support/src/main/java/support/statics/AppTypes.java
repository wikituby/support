package support.statics;

import jakarta.ws.rs.WebApplicationException;

import java.util.Arrays;

public enum AppTypes {

    BANKING("banking.arxcess.com"),
    ERP("erp.arxcess.com"),
    HR("hr.arxcess.com");

    public final String label;

    AppTypes(String label){
        this.label = label;
    }

    public static AppTypes getEnum(String value){
        return Arrays.stream(AppTypes.values())
                .filter(enumValue -> enumValue.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new WebApplicationException("Application type not found"));
    }
}
