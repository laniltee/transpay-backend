package lk.sliit.constants;

public class ControllerConstants {

    // Credit card types acceptable through incoming payload
    public static enum CardTypes {
        Visa, American, Discover
    }


    public static final String PAYLOAD_TYPE = "application/json";

    // API ENDPOINTS URLs

    public static final String API_BASE_URL = "/api/";

    public static final String API_ENDPOINT_PRIMARY_KEY = "id";

    public static final String CUSTOMER_CONTROLLER_URL = "customers/";

    // Common Database Column Names

    public static final String ID_COL = "id";

    public static final String UPDATED_AT_COL = "updatedAt";

    public static final String CREATED_AT_COL = "createdAt";

}
