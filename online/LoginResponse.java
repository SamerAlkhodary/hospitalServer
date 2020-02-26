package online;
public class LoginResponse implements Message {
    private String role,division;
    private boolean success;
    private  String message;

    public LoginResponse(String role, String division) {
        this.role = role;
        this.division = division;
        success= true;
        message="success";
    }

    public LoginResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public String getRole() {
        return role;
    }

    public String getDivision() {
        return division;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
