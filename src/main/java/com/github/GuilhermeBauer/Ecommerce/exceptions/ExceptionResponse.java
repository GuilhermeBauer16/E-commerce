package com.github.GuilhermeBauer.Ecommerce.exceptions;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

public class ExceptionResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String message;
    private final String details;
    private final Date timeStamp;

    public ExceptionResponse(String message, String details, Date timeStamp) {
        this.message = message;
        this.details = details;
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }
}
