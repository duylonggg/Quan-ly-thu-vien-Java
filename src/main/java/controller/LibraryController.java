package controller;

import controller.AuthController;
import controller.AccountController;
import controller.BookController;
import static util.ConsoleColor.*;
import static util.ConsoleUtil.*;
import model.Account;
import model.Role;
import util.InputUtil;

import java.util.Scanner;

public class LibraryController {
    private final AuthController authController;
    private final AccountController accountController;
    private final BookController bookController;
    private final Scanner scanner;

    public LibraryController() {
        this.authController = new AuthController();
        this.accountController = new AccountController();
        this.bookController = new BookController();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Chạy menu chính của thư viện.
     */
    public void run() {
        while (true) {
            clearScreen();
            if (!authController.isLoggedIn()) {
                System.out.println(GREEN + "==== THƯ VIỆN ====" + RESET);
                System.out.println("1. Đăng nhập");
                System.out.println("2. Đăng ký");
                System.out.println("0. Thoát");
                int choice = InputUtil.readMenuChoice(2);
                switch (choice) {
                    case 1:
                        authController.login();
                        break;
                    case 2:
                        authController.register();
                        break;
                    case 0:
                        System.out.println(GREEN + "Tạm biệt!" + RESET);
                        System.exit(0);
                        return;
                }
            } else {
                Account currentUser = authController.getCurrentUser();
                if (currentUser.getRole() == Role.ADMIN) {
                    adminMenu(currentUser);
                } else {
                    userMenu(currentUser);
                }
            }
        }
    }

    // Menu dành cho Admin
    private void adminMenu(Account admin) {
        while (true) {
            clearScreen();
            System.out.println(GREEN + "\n==== MENU ADMIN ====" + RESET);
            System.out.println("1. Quản lý tài khoản");
            System.out.println("2. Quản lý sách");
            System.out.println("3. Đăng xuất");
            System.out.println("0. Thoát");
            int choice = InputUtil.readMenuChoice(3);
            switch (choice) {
                case 1:
                    accountManagementMenu();
                    break;
                case 2:
                    bookManagementMenu();
                    break;
                case 3:
                    authController.logout();
                    break;
                case 0:
                    System.out.println(GREEN + "Tạm biệt!" + RESET);
                    System.exit(0);
                    break;
            }
            if (!authController.isLoggedIn()) break;
        }
    }

    // Menu dành cho User
    private void userMenu(Account user) {
        while (true) {
            clearScreen();
            System.out.println(GREEN + "\n==== MENU USER ====" + RESET);
            System.out.println("1. Xem tất cả sách");
            System.out.println("2. Tìm kiếm sách");
            System.out.println("3. Xem chi tiết sách");
            System.out.println("4. Đăng xuất");
            System.out.println("0. Thoát");
            int choice = InputUtil.readMenuChoice(4);
            switch (choice) {
                case 1:
                    bookController.showAllBooks();
                    break;
                case 2:
                    bookController.searchBooks();
                    break;
                case 3:
                    bookController.viewBookDetail();
                    break;
                case 4:
                    authController.logout();
                    break;
                case 0:
                    System.out.println(GREEN + "Tạm biệt!" + RESET);
                    System.exit(0);
                    break;
            }
            if (!authController.isLoggedIn()) break;
        }
    }

    // Quản lý tài khoản (Admin)
    private void accountManagementMenu() {
        while (true) {
            System.out.println(GREEN + "\n-- Quản lý tài khoản --" + RESET);
            System.out.println("1. Xem tất cả tài khoản");
            System.out.println("2. Tạo tài khoản");
            System.out.println("3. Cập nhật tài khoản");
            System.out.println("4. Xóa tài khoản");
            System.out.println("0. Quay lại");
            int choice = InputUtil.readMenuChoice(4);
            switch (choice) {
                case 1:
                    accountController.showAllAccounts();
                    break;
                case 2:
                    accountController.createAccount();
                    break;
                case 3:
                    accountController.updateAccount();
                    break;
                case 4:
                    accountController.deleteAccount();
                    break;
                case 0:
                    return;
            }
        }
    }

    // Quản lý sách (Admin)
    private void bookManagementMenu() {
        while (true) {
            System.out.println(GREEN + "\n-- Quản lý sách --" + RESET);
            System.out.println("1. Xem tất cả sách");
            System.out.println("2. Tìm kiếm sách");
            System.out.println("3. Thêm sách");
            System.out.println("4. Cập nhật sách");
            System.out.println("5. Xóa sách");
            System.out.println("6. Xem chi tiết sách");
            System.out.println("0. Quay lại");
            int choice = InputUtil.readMenuChoice(6);
            switch (choice) {
                case 1:
                    bookController.showAllBooks();
                    break;
                case 2:
                    bookController.searchBooks();
                    break;
                case 3:
                    bookController.createBook();
                    break;
                case 4:
                    bookController.updateBook();
                    break;
                case 5:
                    bookController.deleteBook();
                    break;
                case 6:
                    bookController.viewBookDetail();
                    break;
                case 0:
                    return;
            }
        }
    }
}
