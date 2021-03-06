package msg.manage.dao;

import msg.manage.modal.Pager;
import msg.manage.modal.User;
import msg.manage.util.MsgException;

import java.util.List;

/**
 * Created by Amysue on 2016/3/4.
 */
public interface IUserDao {
    public int add(User user)throws MsgException;

    public boolean delete(int id) throws MsgException;

    public boolean update(User user) throws MsgException;

    public User load(int id) throws MsgException;

    public Pager loadList(int pageIndex, int pageItems, String usernmae, String nickname, int role, int status);

    public User login(String username, String password) throws MsgException;
}
