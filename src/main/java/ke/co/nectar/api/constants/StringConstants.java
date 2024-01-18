package ke.co.nectar.api.constants;

public interface StringConstants {

    int SUCCESS_CODE = 200;
    int BAD_REQUEST = 400;
    int INVALID_REQUEST = 405;
    int INTERNAL_SERVER_ERROR = 500;
    int EXCEPTION_CODE = 600;
    int ALREADY_EXIST_CODE = 700;

    String SUCCESS_OBTAINED_TOKEN_DETAILS = "Successfully obtained token details";
    String SUCCESS_CREATED_PUBLIC_KEY = "Successfully created public key";
    String SUCCESS_ACTIVATED_PUBLIC_KEY = "Successfully activated public key";
    String SUCCESS_DEACTIVATED_PUBLIC_KEY = "Successfully deactivated public key";
    String SUCCESS_GENERATED_TOKEN = "Successfully generated token";
    String SUCCESS_DECODED_TOKEN = "Successfully decoded token";
    String SUCCESS_OBTAINED_PUBLIC_KEYS_DETAILS = "Successfully obtained public key details";
    String SUCCESS_DELETED_TOKEN = "Successfully deleted token";
    String SUCCESS_OBTAINED_USER_DETAILS = "Successfully obtained user details";
    String SUCCESS_CREATED_USER = "Successfully created user";
    String SUCCESS_DELETED_USER = "Successfully deleted user";
    String INCOMPATIBLE_USER_FOR_REQUEST = "Incompatible user for request";
    String SUCCESS_SET_NOTIFICATION_READ_STATUS = "Set notification read status";
    String INVALID_TOKEN = "Invalid token";
    String FORWARD_CALLBACK_RESPONSE_RECEIVED = "Forward callback response received";
    String SUCCESS_OBTAINED_CREDENTIALS = "Successfully obtained credentials";
    String SUCCESS_ACTIVATED_CREDENTIALS = "Successfully activated credentials";
    String SUCCESS_DEACTIVATED_CREDENTIALS = "Successfully deactivated credentials";
    String SUCCESS_CREATED_CONFIGURATION = "Successfully created configuration";
    String SUCCESS_OBTAINED_CONFIGURATION = "Successfully obtained configuration";
    String SUCCESS_ACTIVATED_CONFIGURATION = "Successfully activated configuration";
    String SUCCESS_DEACTIVATED_CONFIGURATION = "Successfully deactivated configuration";
    String INSUFFICIENT_CREDITS = "Insufficient credits";

}
