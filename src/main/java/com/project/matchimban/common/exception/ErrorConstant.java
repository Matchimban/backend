package com.project.matchimban.common.exception;

public class ErrorConstant {

    /**
     * server
     */

    public final static String SERVER_ERROR_UNDEFINED = "SERVER_ERROR_UNDEFINED";
    public final static String SERVER_ERROR_JSON_PARSE = "SERVER_ERROR_JSON_PARSE";
    public final static String ACCESS_DENY = "ACCESS_DENY";

    /**
     * validation
     */

    public final static String INPUT_ERROR_VALIDATION = "INPUT_ERROR_VALIDATION";
    public final static String INPUT_ERROR_ENUM_VALIDATION = "INPUT_ERROR_ENUM_VALIDATION";

    public final static String TEST_COUPON_ERROR_NONE_PK = "TEST_COUPON_ERROR_NONE_PK";
    public final static String TEST_ETC = "TEST_ETC";

    /**
     * user
     */

    public final static String DUPLICATED_EMAIL = "DUPLICATED_EMAIL";
    public final static String NOT_FOUND_USER = "NOT_FOUND_USER";
    public final static String INVALID_TOKEN = "INVALID_TOKEN";
    public final static String INVALID_SIGNATURE = "INVALID_SIGNATURE";
    public final static String PASSWORD_MISMATCH = "PASSWORD_MISMATCH";
    public final static String NOT_ACTIVE_USER = "NOT_ACTIVE_USER";

    public final static String NOT_FOUND_TOKEN = "NOT_FOUND_TOKEN";
    public final static String INVALID_REFRESH_TOKEN = "INVALID_REFRESH_TOKEN";
    public final static String EXPIRED_TOKEN = "EXPIRED_TOKEN";
    public final static String NOT_OWNED_BY_THE_USER = "NOT_OWNED_BY_THE_USER";

    /**
     * restaurant_reservation
     */

    public final static String RESTAURANTRESERVATION_ERROR_RESTAURANT_NONE_PK = "RESTAURANTRESERVATION_ERROR_RESTAURANT_NONE_PK";
    public final static String RESTAURANTRESERVATION_ERROR_ALREADY_EXISTS = "RESTAURANTRESERVATION_ERROR_ALREADY_EXISTS";
    public final static String RESTAURANTRESERVATION_ERROR_NONE_PK = "RESTAURANTRESERVATION_ERROR_NONE_PK";

    /**
     * reservation
     */

    public final static String RESERVATION_ERROR_USER_NONE_PK = "RESERVATION_ERROR_USER_NONE_PK";
    public final static String RESERVATION_ERROR_RESTAURANTRESERVATION_NONE_PK = "RESERVATION_ERROR_RESTAURANTRESERVATION_NONE_PK";
    public final static String RESERVATION_ERROR_IAMPORT = "RESERVATION_ERROR_IAMPORT";
    public final static String RESERVATION_ERROR_INVALID_VERIFY = "RESERVATION_ERROR_INVALID_VERIFY";
    public final static String RESERVATION_ERROR_NONE_PK = "RESERVATION_ERROR_NONE_PK";
    public final static String RESERVATION_IMPOSSIBLE_REFUND = "RESERVATION_IMPOSSIBLE_REFUND";
    public final static String RESERVATION_NO_REMAIN_SEAT = "RESERVATION_NO_REMAIN_SEAT";
    public final static String RESERVATION_ERROR_RESTAURANT_NONE_PK = "RESERVATION_ERROR_RESTAURANT_NONE_PK";
    public final static String RESERVATION_ERROR_OWNER_GET_INVALID_VERIFY = "RESERVATION_ERROR_OWNER_GET_INVALID_VERIFY";
    public final static String RESERVATION_ERROR_OWNER_REFUND_INVALID_VERIFY = "RESERVATION_ERROR_OWNER_REFUND_INVALID_VERIFY";

    /**
     * email
     */

    public final static String FAILED_TO_SEND_EMAIL = "FAILED_TO_SEND_EMAIL";
    public final static String NOT_FOUND_EMAIL_AND_CODE = "NOT_FOUND_EMAIL_AND_CODE";

    /**
    * restaurant
    */
    public final static String RESTAURANT_ERROR_NOT_FOUND_RESTAURANT = "RESTAURANT_ERROR_NOT_FOUND_RESTAURANT";

    /**
     * file & image
     */
    public final static String FILE_ERROR_NULL_FILE = "FILE_ERROR_NULL_FILE";
    public final static String FILE_ERROR_UNKNOWN_EXTENSION = "FILE_ERROR_UNKNOWN_EXTENSION";

}
