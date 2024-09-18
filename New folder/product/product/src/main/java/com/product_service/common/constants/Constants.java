package com.product_service.common.constants;
public final class Constants {
    // Private constructor to prevent instantiation
    private Constants() {
        throw new UnsupportedOperationException("This is a constants class and cannot be instantiated");
    }

    // Response Codes
    public static final int SUCCESS_CODE = 0;
    public static final int VALIDATION_ERROR_CODE = 1;
    public static final int INTERNAL_ERROR_CODE = 2;

    // Response Messages
    public static final String SUCCESS_MESSAGE = "Product created successfully.";
    public static final String VALIDATION_ERROR_MESSAGE = "Invalid data provided";
    public static final String INTERNAL_ERROR_MESSAGE = "Failed to create product. Please try again later.";


}

