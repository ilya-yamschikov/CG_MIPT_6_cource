package bspline;

public class ValidationResult{
    public enum ValidationCode {
        SUCCESS, FAIL
    }

    private ValidationCode code;
    private String msg;

    public ValidationResult(ValidationCode code) {
        this.code = code;
    }

    public ValidationResult(ValidationCode code, String msg) {
        this(code);
        this.msg = msg;
    }

    public void setMsg(String msg) {this.msg = msg;}
    public String getMsg() {return msg;}

    public boolean passed() {
        return this.code == ValidationCode.SUCCESS;
    }
}