package online;

public class CreateEntityResponse implements Message {
    private boolean success;
    private String message;

    public CreateEntityResponse(boolean success, String message) {
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
