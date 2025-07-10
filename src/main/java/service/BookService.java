package service;

import model.Book;
import util.JsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Xử lý dữ liệu sách: tìm kiếm, thêm, cập nhật, xóa, lưu.
 */
public class BookService {
    private static final String BOOK_FILE = "src/main/resources/data/books.json";
    private List<Book> books;

    public BookService() {
        this.books = JsonUtil.readListFromFile(BOOK_FILE, Book.class);
    }

    /**
     * Lấy toàn bộ danh sách sách.
     */
    public List<Book> getAllBooks() {
        return books;
    }

    /**
     * Tìm sách theo tiêu đề hoặc tác giả (không phân biệt hoa thường).
     */
    public List<Book> searchBooks(String keyword) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                    book.getAuthor().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    /**
     * Tìm sách theo ID.
     */
    public Book findById(int id) {
        return books.stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Tìm sách theo tiêu đề chính xác.
     */
    public Book findByTitle(String title) {
        return books.stream()
                .filter(b -> b.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }

    /**
     * Thêm sách mới.
     */
    public void addBook(Book book) {
        int nextId = books.stream().mapToInt(Book::getId).max().orElse(0) + 1;
        book.setId(nextId);
        books.add(book);
        saveBooks();
    }

    /**
     * Cập nhật sách dựa trên ID.
     */
    public void updateBook(Book updated) {
        Book existing = findById(updated.getId());
        if (existing != null) {
            existing.setTitle(updated.getTitle());
            existing.setAuthor(updated.getAuthor());
            existing.setYear(updated.getYear());
            existing.setCategory(updated.getCategory());
            existing.setFilePath(updated.getFilePath());
            saveBooks();
        }
    }

    /**
     * Xóa sách theo ID.
     */
    public void deleteBook(int id) {
        books.removeIf(b -> b.getId() == id);
        saveBooks();
    }

    /**
     * Ghi danh sách sách vào file JSON.
     */
    private void saveBooks() {
        JsonUtil.writeListToFile(BOOK_FILE, books);
    }
}
