package com.ding.airlinewebservice.exception;

import java.util.List;

public class ApiErrorResponseHandler {

    private static ApiErrorResponseHandler builder;

    private String errorId;
    private Integer status;
    private String message;
    private List<String> errors;
    private String path;

    private ApiErrorResponseHandler() {}

    public static synchronized ApiErrorResponseHandler getInstance() {
        if (builder == null) {
            builder = new ApiErrorResponseHandler();
        }
        return builder;
    }

    public ApiErrorResponseHandler withErrorId(String errorId) {
        builder.errorId = errorId;
        return builder;
    }

    public ApiErrorResponseHandler withStatus(Integer status) {
        builder.status = status;
        return builder;
    }

    public ApiErrorResponseHandler withMessage(String message) {
        builder.message = message;
        return builder;
    }

    public ApiErrorResponseHandler withErrors(List<String> errors) {
        builder.errors = errors;
        return builder;
    }

    public ApiErrorResponseHandler forPath(String path) {
        builder.path = path;
        return builder;
    }

    public ApiErrorResponse build() {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
        apiErrorResponse.setErrorId(builder.errorId);
        apiErrorResponse.setStatus(builder.status);
        apiErrorResponse.setMessage(builder.message);
        apiErrorResponse.setErrors(builder.errors);
        apiErrorResponse.setPath(builder.path);

        return apiErrorResponse;
    }


}
