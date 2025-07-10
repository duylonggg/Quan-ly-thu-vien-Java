package model;

import java.awt.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class Ebook extends Book {
    private String downloadUrl;
    private long fileSize;  // in bytes

    public Ebook() {
        super();
        this.setCategory("Ebook");
    }

    public Ebook(int id, String title, String author, int year, String downloadUrl, long fileSize) {
        super(id, title, author, year, "Ebook", downloadUrl);
        this.downloadUrl = downloadUrl;
        this.fileSize = fileSize;
    }

    /**
     * Tải file ebook từ URL về thư mục 'downloads' trong project.
     * @param destinationFolder thư mục đích (ví dụ "downloads/")
     * @throws IOException khi có lỗi I/O
     */
    public void download(String destinationFolder) throws IOException {
        // Tạo thư mục nếu chưa tồn tại
        File dir = new File(destinationFolder);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String fileName = this.getTitle().replaceAll("\\s+", "_") + ".pdf";
        File outputFile = new File(dir, fileName);

        try (BufferedInputStream in = new BufferedInputStream(new URL(downloadUrl).openStream());
             FileOutputStream fileOut = new FileOutputStream(outputFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            long totalRead = 0;
            while ((bytesRead = in.read(buffer, 0, 1024)) != -1) {
                fileOut.write(buffer, 0, bytesRead);
                totalRead += bytesRead;
            }
            this.fileSize = totalRead;
            this.setFilePath(outputFile.getAbsolutePath());
        } catch (MalformedURLException e) {
            throw new IOException("URL không hợp lệ: " + downloadUrl, e);
        }
    }

    /**
     * Mở file ebook đã tải về hoặc đọc trực tiếp từ URL nếu chưa tải.
     * Sử dụng Desktop API để mở file hoặc trình duyệt.
     * @throws IOException khi không thể mở file hoặc URL
     */
    public void readOnline() throws IOException, URISyntaxException {
        Desktop desktop = Desktop.getDesktop();
        File localFile = new File(this.getFilePath());
        if (localFile.exists()) {
            desktop.open(localFile);
        } else {
            // Mở URL trong trình duyệt
            desktop.browse(new URL(downloadUrl).toURI());
        }
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
        this.setFilePath(downloadUrl);
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    @Override
    public String toString() {
        return super.toString() + ", fileSize=" + fileSize + " bytes}";
    }
}
