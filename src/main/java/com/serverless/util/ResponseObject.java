package com.serverless.util;

import java.io.Serializable;

public class ResponseObject implements Serializable {
    private static final long serialVersionUID = -4877824953428812786L;
    private boolean error;
    private String message;
    private Object data;

    public ResponseObject() {
        super();
    }

    public ResponseObject(boolean error, String message) {
        this();
        this.error = error;
        this.message = message;
    }

    public ResponseObject(boolean error, String message, Object data) {
        this();
        this.error = error;
        this.data = data;
        this.message = message;
    }

    public ResponseObject(boolean error, Object data) {
        this();
        this.error = error;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
