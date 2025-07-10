package controller;

import model.Book;
import service.BookService;
import util.InputUtil;

import java.util.List;

import static util.ConsoleColor.*;

public class BookController {
    private final BookService bookService;

    public BookController() {
        this.bookService = new BookService();
    }

    /**
     * Hiển thị danh sách tất cả sách.
     */
    public void showAllBooks() {
        List<Book> books = bookService.getAllBooks();
        if (books.isEmpty()) {
            System.out.println(RED + "Không có sách nào." + RESET);
        } else {
            for (Book book : books) {
                System.out.println(book);
            }
        }
    }

    /**
     * Tìm kiếm sách theo tiêu đề hoặc tác giả.
     */
    public void searchBooks() {
        String keyword = InputUtil.readNonBlankString(YELLOW + "Nhập từ khóa tìm kiếm (tên hoặc tác giả)" + RESET);
        List<Book> result = bookService.searchBooks(keyword);
        if (result.isEmpty()) {
            System.out.println(RED + "Không tìm thấy sách nào phù hợp." + RESET);
        } else {
            for (Book book : result) {
                System.out.println(book);
            }
        }
    }

    /**
     * Thêm sách mới (Admin).
     */
    public void createBook() {
        String title = InputUtil.readNonBlankString(YELLOW + "Nhập tên sách" + RESET);
        String author = InputUtil.readNonBlankString(YELLOW + "Nhập tác giả" + RESET);
        int year = InputUtil.readInt(YELLOW + "Nhập năm xuất bản" + RESET);
        String category = InputUtil.readNonBlankString(YELLOW + "Nhập thể loại (Ebook, Tiểu thuyết, ...)" + RESET);
        String filePath = InputUtil.readString(YELLOW + "Nhập đường dẫn hoặc URL (nếu có)" + RESET);

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setYear(year);
        book.setCategory(category);
        book.setFilePath(filePath);

        bookService.addBook(book);
        System.out.println(CYAN + "Thêm sách thành công." + RESET);
    }

    /**
     * Cập nhật thông tin sách (Admin).
     */
    public void updateBook() {
        int id = InputUtil.readInt(YELLOW + "Nhập ID sách cần cập nhật" + RESET);
        Book book = bookService.findById(id);
        if (book == null) {
            System.out.println(RED + "Không tìm thấy sách với ID này." + RESET);
            return;
        }

        String title = InputUtil.readNonBlankString(YELLOW + "Tên mới (" + book.getTitle() + ")" + RESET);
        String author = InputUtil.readNonBlankString(YELLOW + "Tác giả mới (" + book.getAuthor() + ")" + RESET);
        int year = InputUtil.readInt(YELLOW + "Năm xuất bản mới (" + book.getYear() + ")" + RESET);
        String category = InputUtil.readNonBlankString(YELLOW + "Thể loại mới (" + book.getCategory() + ")" + RESET);
        String filePath = InputUtil.readString(YELLOW + "Đường dẫn mới (" + book.getFilePath() + ")" + RESET);

        book.setTitle(title);
        book.setAuthor(author);
        book.setYear(year);
        book.setCategory(category);
        book.setFilePath(filePath);

        bookService.updateBook(book);
        System.out.println(CYAN + "Cập nhật sách thành công." + RESET);
    }

    /**
     * Xoá sách (Admin).
     */
    public void deleteBook() {
        int id = InputUtil.readInt(YELLOW + "Nhập ID sách cần xoá" + RESET);
        Book book = bookService.findById(id);  // Kiểm tra ID

        if (book == null) {
            System.out.println(RED + "Không tìm thấy sách với ID này." + RESET);
        } else {
            bookService.deleteBook(id);
            System.out.println(GREEN + "Đã xoá sách: " + book.getTitle() + RESET);
        }
    }

    /**
     * Xem chi tiết một cuốn sách.
     */
    public void viewBookDetail() {
        int id = InputUtil.readInt(YELLOW + "Nhập ID sách cần xem" + RESET);
        Book book = bookService.findById(id);
        if (book == null) {
            System.out.println(RED + "Không tìm thấy sách với ID này." + RESET);
        } else {
            System.out.println(book);
        }
    }
}
