package util;

import model.Role;

import java.util.InputMismatchException;
import java.util.Scanner;

import static util.ConsoleColor.*;

/**
 * Utility class để đọc dữ liệu từ người dùng với validation cơ bản.
 */
public class InputUtil {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Đọc một chuỗi (String) từ người dùng.
     */
    public static String readString(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.nextLine().trim();
    }

    public static String readUsername(String prompt) {
        String username;
        do {
            username = readString(prompt);
            if (username.trim().isEmpty()) {
                System.out.println(RED + "Tên đăng nhập không được để trống." + RESET);
            }
        } while (username.trim().isEmpty());
        return username;
    }

    public static String readPassword(String prompt) {
        String password;
        do {
            password = readString(prompt);
            if (password.length() < 6) {
                System.out.println(RED + "Mật khẩu phải có ít nhất 6 ký tự." + RESET);
            }
        } while (password.length() < 6);
        return password;
    }

    public static String readNonBlankString(String prompt) {
        String input;
        do {
            input = readString(prompt);
            if (input.trim().isEmpty()) {
                System.out.println(RED + "Trường này không được để trống!" + RESET);
            }
        } while (input.trim().isEmpty());
        return input;
    }

    /**
     * Đọc một số nguyên (int) từ người dùng, với thông báo và kiểm tra lỗi.
     */
    public static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt + " (số nguyên): ");
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println(RED + "Giá trị không hợp lệ. Vui lòng nhập lại số nguyên." + RESET);
            }
        }
    }

    /**
     * Đọc một số nguyên trong khoảng [min, max].
     */
    public static int readInt(String prompt, int min, int max) {
        while (true) {
            int value = readInt(prompt);
            if (value >= min && value <= max) {
                return value;
            }
            System.out.printf(RED + "Vui lòng nhập số trong khoảng từ %d đến %d.%n" + RESET, min, max);
        }
    }

    /**
     * Đọc lựa chọn (int) với giới hạn maxOption.
     */
    public static int readMenuChoice(int maxOption) {
        return readInt("Chọn (0-" + maxOption + ")", 0, maxOption);
    }

    /**
     * Đọc quyền ROLE từ người dùng (ADMIN hoặc USER).
     */
    public static Role readRole(String prompt) {
        while (true) {
            System.out.print(prompt + " (ADMIN/USER): ");
            String input = scanner.nextLine().trim().toUpperCase();
            try {
                return Role.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println(RED + "Giá trị role không hợp lệ. Nhập ADMIN hoặc USER." + RESET);
            }
        }
    }
}
