package service;

import model.Account;
import util.JsonUtil;

import java.io.File;
import java.util.List;

/**
 * Xử lý đăng ký, đăng nhập và session.
 */
public class AuthService {
    private static final String ACCOUNT_FILE = "src/main/resources/data/accounts.json";
    private static final String SESSION_FILE = "src/main/resources/data/session.json";

    private List<Account> accounts;

    public AuthService() {
        // Load danh sách accounts từ file JSON
        this.accounts = JsonUtil.readListFromFile(ACCOUNT_FILE, Account.class);
    }

    /**
     * Đăng ký tài khoản mới.
     * @param account thông tin account cần đăng ký
     * @return true nếu thành công, false nếu username đã tồn tại
     */
    public boolean register(Account account) {
        // Kiểm tra username đã tồn tại chưa
        boolean exists = accounts.stream()
                .anyMatch(a -> a.getUsername().equals(account.getUsername()));
        if (exists) {
            return false;
        }
        // Gán ID (max + 1)
        int nextId = accounts.stream()
                .mapToInt(Account::getId)
                .max()
                .orElse(0) + 1;
        account.setId(nextId);
        accounts.add(account);
        // Lưu danh sách accounts mới
        JsonUtil.writeListToFile(ACCOUNT_FILE, accounts);
        return true;
    }

    /**
     * Đăng nhập.
     * @param username tên đăng nhập
     * @param password mật khẩu
     * @return Account nếu thành công, null nếu sai thông tin
     */
    public Account login(String username, String password) {
        return accounts.stream()
                .filter(a -> a.getUsername().equals(username)
                        && a.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    /**
     * Lưu session người dùng hiện tại vào file.
     * @param account user đang đăng nhập
     */
    public void saveSession(Account account) {
        JsonUtil.writeObjectToFile(SESSION_FILE, account);
    }

    /**
     * Xoá session hiện tại.
     */
    public void clearSession() {
        File f = new File(SESSION_FILE);
        if (f.exists()) {
            f.delete();
        }
    }

    /**
     * Đọc session nếu có.
     * @return Account đang đăng nhập hoặc null
     */
    public Account readSession() {
        return JsonUtil.readObjectFromFile(SESSION_FILE, Account.class);
    }
}
