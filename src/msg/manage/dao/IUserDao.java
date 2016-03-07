package msg.manage.dao;

import msg.manage.modal.User;
import msg.manage.util.MsgException;

import java.util.List;

/**
 * Created by Amysue on 2016/3/4.
 */
public interface IUserDao {
    public int add(User user)throws MsgException;

    public boolean delete(int id);

    public boolean update(User user);

    public User load(int id) throws MsgException;

    public List<User> loadList();
}