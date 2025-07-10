package util;

import java.io.IOException;

import static util.ConsoleColor.*;

public class ConsoleUtil {
    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {}
    }

    public static void pause() {
        System.out.println(GREEN + "\nNhấn Enter để tiếp tục..." + RESET);
        try {
            System.in.read();
        } catch (Exception ignored) {}
    }
}
