package msg.manage.modal;

import java.util.List;

/**
 * Created by Amysue on 2016/3/10.
 */
public class Pager {
    private List<User> users;
    private int        itemCounts;
    private int        currentPage;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getItemCounts() {
        return itemCounts;
    }

    public void setItemCounts(int itemCounts) {
        this.itemCounts = itemCounts;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
