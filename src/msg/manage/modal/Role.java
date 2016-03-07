package msg.manage.modal;

/**
 * Created by Amysue on 2016/3/4.
 */
public enum Role {
    ADMIN(0), NORMAL(1);
    private int code;

    private Role(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
