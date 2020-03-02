package online;

import model.entities.Entity;

public class LoginResponse implements Message {
private Entity entity;
    private boolean success;
    private  String message;

    public LoginResponse(Entity entity) {
        this.entity=entity;
        success= true;
        message="success";
    }

    public LoginResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Entity getEntity() {
        return entity;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
