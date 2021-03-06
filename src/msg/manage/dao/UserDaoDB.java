package msg.manage.dao;

import javafx.beans.binding.When;
import msg.manage.modal.Pager;
import msg.manage.modal.Role;
import msg.manage.modal.Status;
import msg.manage.modal.User;
import msg.manage.util.DBUtil;
import msg.manage.util.MsgException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amysue on 2016/3/4.
 */
public class UserDaoDB implements IUserDao {
    private Connection        conn = null;
    private PreparedStatement ps   = null;
    private ResultSet         rs   = null;

    @Override
    public int add(User user) throws MsgException {
        conn = DBUtil.getConn();
        String sql = "select count(*) from " + DBUtil.tUser + " where USERNAME = ?";
        int    id  = -1;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getInt(1) >= 1) {
                    throw new MsgException("用户名 " + user.getUsername() + " 存在");
                }
            }
            sql = "insert into " + DBUtil.tUser + " values(NULL,?,?,?,?,?)";
            ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getNickname());
            if (user.getRole() != null) {
                ps.setInt(4, user.getRole().getCode());
            } else {
                ps.setInt(4, Role.NORMAL.getCode());
            }
            ps.setInt(5, Status.INUSE.getCode());
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new MsgException("Create User Failed, No Rows Affected");
            }

            ResultSet generatedKey = ps.getGeneratedKeys();
            if (generatedKey.next()) {
                id = generatedKey.getInt(1);
            } else {
                throw new MsgException("Creating user failed, no ID obtained.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        close();
        return id;
    }

    @Override
    public boolean delete(int id) throws MsgException {
        conn = DBUtil.getConn();
        String sql          = "delete from " + DBUtil.tUser + " where id = ?";
        int    affectedRows = 0;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            affectedRows = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        close();
        if (affectedRows == 0) {
            throw new MsgException("不能删除用户 id = " + id);
        }
        return true;
    }

    @Override
    public boolean update(User user) throws MsgException {
        User oldu = null;
        try {
            oldu = load(user.getId());
        } catch (MsgException e) {
            throw new MsgException("修改用户失败 " + e.getMessage());
        }
        conn = DBUtil.getConn();
        String sql = "update " + DBUtil.tUser + " set password = ?, nickname = ?, role = ?, status = ? where id = ?";
        String value;
        int    num;
        int    affectedRows;
        try {
            ps = conn.prepareStatement(sql);
            if (user.getPassword() != null) {
                value = user.getPassword();
            } else {
                value = oldu.getPassword();
            }
            ps.setString(1, value);
            if (user.getNickname() != null) {
                value = user.getNickname();
            } else {
                value = oldu.getNickname();
            }
            ps.setString(2, value);
            if (user.getRole() != null) {
                num = user.getRole().getCode();
            } else {
                num = oldu.getRole().getCode();
            }
            ps.setInt(3, num);
            if (user.getStatus() != null) {
                num = user.getStatus().getCode();
            } else {
                num = oldu.getStatus().getCode();
            }
            ps.setInt(4, num);
            ps.setInt(5, user.getId());
            affectedRows = ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

        close();
        if (affectedRows == 0) {
            throw new MsgException("更新用户失败 id = " + user.getId());
        }
        return true;
    }

    @Override
    public User load(int id) throws MsgException {
        conn = DBUtil.getConn();
        String sql = "select * from " + DBUtil.tUser + " where id = ?";
        User   u   = new User();
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    u = sql2User(rs);
                }
            } else {
                throw new MsgException("No id = " + id + " user in DB");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        close();
        return u;
    }

    @Override
    public Pager loadList(int pageIndex, int pageItems, String username, String nickname, int role, int status) {
        List<User> users = new ArrayList<>();
        conn = DBUtil.getConn();

        int   startItem = (pageIndex - 1) * pageItems;
        Pager pager     = new Pager();
        pager.setCurrentPage(pageIndex);
        String querySql = " where 1=1 " +
                "and username like ? " +
                "and nickname like ? " +
                "and role <> ? " +
                "and status <> ?";

        int iIndex = 1;

        String sql = "select count(*) from " + DBUtil.tUser + querySql;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + username + "%");
            ps.setString(2, "%" + nickname + "%");
            ps.setInt(3, role);
            ps.setInt(4, status);
            rs = ps.executeQuery();
            if (rs.next()) {
                pager.setItemCounts(rs.getInt(1));
            }
            int sumPages = (pager.getItemCounts() + pageItems - 1) / pageItems;
            if (sumPages > 0) {
                if (pageIndex > sumPages) {
                    startItem = (sumPages - 1) * pageItems;
                    pageIndex = sumPages;
                }
                sql = "select * from " + DBUtil.tUser + querySql + " limit " + startItem + "," + pageItems;
                ps = conn.prepareStatement(sql);
                ps.setString(1, "%" + username + "%");
                ps.setString(2, "%" + nickname + "%");
                ps.setInt(3, role);
                ps.setInt(4, status);
                rs = ps.executeQuery();
                while (rs.next()) {
                    users.add(sql2User(rs));
                }
            } else {
                pageIndex = 0;
            }
            pager.setCurrentPage(pageIndex);
            pager.setUsers(users);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        close();
        return pager;
    }

    private User sql2User(ResultSet rs) {
        User u = new User();
        try {
            u.setId(rs.getInt("id"));
            u.setUsername(rs.getString("username"));
            u.setPassword(rs.getString("password"));
            u.setNickname(rs.getString("nickname"));
            if (rs.getInt("role") == Role.ADMIN.getCode()) {
                u.setRole(Role.ADMIN.ADMIN);
            } else {
                u.setRole(Role.NORMAL);
            }
            if (rs.getInt("status") == Status.INUSE.getCode()) {
                u.setStatus(Status.INUSE);
            } else {
                u.setStatus(Status.OFFUSE);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return u;

    }

    private void close() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Close SQL Connection Fail", e);
        }
    }

    @Override
    public User login(String username, String password) throws MsgException {
        conn = DBUtil.getConn();
        String sql = "select * from " + DBUtil.tUser + " where username = ?";
        User   u   = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                u = sql2User(rs);
                if (!u.getPassword().equals(password)) {
                    throw new MsgException("密码不正确");
                }
            } else {
                throw new MsgException("用户名 " + username + " 不存在");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        close();
        return u;
    }
}
