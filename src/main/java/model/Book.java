package model;

public class Book {
    private int id;
    private String title;
    private String author;
    private int year;
    private String category;  // e.g., "Printed" or "Ebook"
    private String filePath;  // nếu là Ebook, đường dẫn đến file hoặc URL

    public Book() {}

    public Book(int id, String title, String author, int year, String category, String filePath) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.category = category;
        this.filePath = filePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                ", category='" + category + '\'' +
                (category.equalsIgnoreCase("Ebook") ? ", filePath='" + filePath + '\'' : "") +
                '}';
    }
}
