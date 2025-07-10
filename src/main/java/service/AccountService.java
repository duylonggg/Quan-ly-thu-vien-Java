package service;

import model.Account;
import util.JsonUtil;
import java.util.List;

/**
 * Xử lý CRUD cho Account thông qua file JSON.
 */
public class AccountService {
    private static final String ACCOUNT_FILE = "src/main/resources/data/accounts.json";
    private List<Account> accounts;

    public AccountService() {
        // Load danh sách accounts từ file JSON
        this.accounts = JsonUtil.readListFromFile(ACCOUNT_FILE, Account.class);
    }

    /**
     * Lấy danh sách tất cả tài khoản.
     */
    public List<Account> getAllAccounts() {
        return accounts;
    }

    /**
     * Tìm account theo ID.
     */
    public Account findById(int id) {
        return accounts.stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Tìm account theo username.
     */
    public Account findByUsername(String username) {
        return accounts.stream()
                .filter(a -> a.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    /**
     * Thêm account mới.
     * @throws IllegalArgumentException nếu username đã tồn tại
     */
    public void addAccount(Account account) {
        if (findByUsername(account.getUsername()) != null) {
            throw new IllegalArgumentException("Username đã tồn tại.");
        }
        // Gán ID tự động (maxID + 1)
        int nextId = accounts.stream()
                .mapToInt(Account::getId)
                .max()
                .orElse(0) + 1;
        account.setId(nextId);
        accounts.add(account);
        saveAccounts();
    }

    /**
     * Cập nhật thông tin account.
     * @throws IllegalArgumentException nếu account không tồn tại
     */
    public void updateAccount(Account updated) {
        Account existing = findById(updated.getId());
        if (existing == null) {
            throw new IllegalArgumentException("Account không tồn tại.");
        }
        existing.setUsername(updated.getUsername());
        existing.setPassword(updated.getPassword());
        existing.setRole(updated.getRole());
        saveAccounts();
    }

    /**
     * Xoá account theo ID.
     * @throws IllegalArgumentException nếu account không tồn tại
     */
    public void deleteAccount(int id) {
        boolean removed = accounts.removeIf(a -> a.getId() == id);
        if (!removed) {
            throw new IllegalArgumentException("Không tìm thấy account với ID: " + id);
        }
        saveAccounts();
    }

    /**
     * Ghi danh sách accounts trở lại file JSON.
     */
    private void saveAccounts() {
        JsonUtil.writeListToFile(ACCOUNT_FILE, accounts);
    }
}
