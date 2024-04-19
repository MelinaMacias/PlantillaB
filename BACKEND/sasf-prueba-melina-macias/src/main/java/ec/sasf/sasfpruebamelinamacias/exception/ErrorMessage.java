package ec.sasf.sasfpruebamelinamacias.exception;

import java.util.Date;
import java.util.List;


public class ErrorMessage {

    private Date date;
    private String message;
    private List<String> details;
    private Integer statusCode;
    private Exception exception;
    private String path;

    public ErrorMessage(String message, List<String> details, Integer statusCode, String path) {
        super();
        this.date = new Date();
        this.message = message;
        this.details = details;
        this.statusCode = statusCode;
        //this.exception = exception;
        this.path = path;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
