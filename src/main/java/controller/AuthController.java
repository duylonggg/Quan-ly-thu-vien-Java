package controller;

import model.Account;
import model.Role;
import service.AuthService;
import util.InputUtil;

import static util.ConsoleColor.*;

/**
 * Controller xử lý đăng nhập, đăng ký, đăng xuất.
 */
public class AuthController {
    private final AuthService authService;
    private Account currentUser;

    public AuthController() {
        this.authService = new AuthService();
        this.currentUser = authService.readSession();
    }

    /**
     * Đăng nhập người dùng.
     */
    public void login() {
        String username = InputUtil.readUsername(YELLOW + "Nhập username" + RESET);
        String password = InputUtil.readPassword(YELLOW + "Nhập password" + RESET);
        Account account = authService.login(username, password);
        if (account != null) {
            currentUser = account;
            authService.saveSession(account);
            System.out.println(CYAN + "Đăng nhập thành công với quyền: " + account.getRole() + RESET);
        } else {
            System.out.println(RED + "Sai username hoặc password." + RESET);
        }
    }

    /**
     * Đăng ký tài khoản mới.
     */
    public void register() {
        String username = InputUtil.readUsername(YELLOW + "Nhập username" + RESET);
        String password = InputUtil.readPassword(YELLOW + "Nhập password" + RESET);
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        account.setRole(Role.USER);

        boolean success = authService.register(account);
        if (success) {
            System.out.println(CYAN + "Đăng ký thành công." + RESET);
        } else {
            System.out.println(RED + "Username đã tồn tại. Vui lòng chọn tên khác." + RESET);
        }
    }

    /**
     * Đăng xuất.
     */
    public void logout() {
        if (currentUser != null) {
            currentUser = null;
            authService.clearSession();
            System.out.println(CYAN + "Đã đăng xuất." + RESET);
        } else {
            System.out.println(RED + "Không có người dùng nào đang đăng nhập." + RESET);
        }
    }

    /**
     * Kiểm tra đăng nhập.
     */
    public boolean isLoggedIn() {
        return currentUser != null;
    }

    /**
     * Lấy user hiện tại.
     */
    public Account getCurrentUser() {
        return currentUser;
    }
}
