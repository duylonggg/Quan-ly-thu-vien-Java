package controller;

import model.Account;
import model.Role;
import static util.ConsoleColor.*;
import service.AccountService;
import util.InputUtil;

import java.util.List;

/**
 * Quản lý các thao tác với tài khoản (Admin).
 */
public class AccountController {
    private final AccountService accountService;

    public AccountController() {
        this.accountService = new AccountService();
    }

    /**
     * Hiển thị tất cả tài khoản.
     */
    public void showAllAccounts() {
        List<Account> list = accountService.getAllAccounts();
        if (list.isEmpty()) {
            System.out.println(RED + "Không có tài khoản nào." + RESET);
        } else {
            for (Account acc : list) {
                System.out.println(acc);
            }
        }
    }

    /**
     * Tạo tài khoản mới (chỉ Admin).
     */
    public void createAccount() {
        String username = InputUtil.readUsername(YELLOW + "Nhập username" + RESET);
        String password = InputUtil.readPassword(YELLOW + "Nhập password" + RESET);
        Role role = InputUtil.readRole(YELLOW + "Chọn quyền (ADMIN/USER)" + RESET);

        Account acc = new Account();
        acc.setUsername(username);
        acc.setPassword(password);
        acc.setRole(role);

        try {
            accountService.addAccount(acc);
            System.out.println(CYAN + "Tạo tài khoản thành công." + RESET);
        } catch (IllegalArgumentException e) {
            System.out.println(RED + "Lỗi: " + e.getMessage() + RESET);
        }
    }

    /**
     * Cập nhật tài khoản (chỉ Admin).
     */
    public void updateAccount() {
        int id = InputUtil.readInt(YELLOW + "Nhập ID tài khoản cần sửa" + RESET);
        Account acc = accountService.findById(id);
        if (acc == null) {
            System.out.println(RED + "Không tìm thấy tài khoản với ID này." + RESET);
            return;
        }

        String username = InputUtil.readUsername(YELLOW + "Tên đăng nhập mới (" + acc.getUsername() + ")" + RESET);
        String password = InputUtil.readPassword(YELLOW + "Mật khẩu mới (" + acc.getPassword() + ")" + RESET);
        Role role = InputUtil.readRole(YELLOW + "Quyền mới (" + acc.getRole() + ")" + RESET);

        acc.setUsername(username);
        acc.setPassword(password);
        acc.setRole(role);

        try {
            accountService.updateAccount(acc);
            System.out.println(CYAN + "Cập nhật tài khoản thành công." + RESET);
        } catch (IllegalArgumentException e) {
            System.out.println(RED + "Lỗi: " + e.getMessage() + RESET);
        }
    }

    /**
     * Xoá tài khoản (chỉ Admin).
     */
    public void deleteAccount() {
        int id = InputUtil.readInt(YELLOW + "Nhập ID tài khoản cần xoá" + RESET);
        try {
            accountService.deleteAccount(id);
            System.out.println(CYAN + "Xoá tài khoản thành công." + RESET);
        } catch (IllegalArgumentException e) {
            System.out.println(RED + "Lỗi: " + e.getMessage() + RESET);
        }
    }
}
