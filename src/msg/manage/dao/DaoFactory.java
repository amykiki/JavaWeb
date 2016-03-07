package msg.manage.dao;

/**
 * Created by Amysue on 2016/3/6.
 */
public class DaoFactory {
    public static IUserDao getUserDao() {
        IUserDao udao = new UserDaoDB();
        return udao;
    }
}
