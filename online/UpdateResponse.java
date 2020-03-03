package online;

public class UpdateResponse implements Message {
    private boolean success;
    private String message;

    public UpdateResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}


