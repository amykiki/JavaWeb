package msg.manage.modal;

/**
 * Created by Amysue on 2016/3/4.
 */
public enum Status {
    INUSE(0), OFFUSE(1);

    private int code;
    private Status(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
