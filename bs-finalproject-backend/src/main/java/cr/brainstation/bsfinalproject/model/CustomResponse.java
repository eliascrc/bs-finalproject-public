package cr.brainstation.bsfinalproject.model;

public class CustomResponse {

    private String messages;
    private String errorMessages;
    private Object response;

    public CustomResponse() {
    }

    public CustomResponse(Object response) {
        this.response = response;
    }

    public CustomResponse(String errorMessages) {
        this.errorMessages = errorMessages;
    }

    public CustomResponse(String messages, String errorMessages) {
        this.messages = messages;
        this.errorMessages = errorMessages;
    }

    public CustomResponse(String messages, String errorMessages, Object response) {
        this.messages = messages;
        this.errorMessages = errorMessages;
        this.response = response;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public String getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(String errorMessages) {
        this.errorMessages = errorMessages;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }
}
