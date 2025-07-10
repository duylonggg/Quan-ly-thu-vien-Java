package service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Utility class để đọc & ghi danh sách đối tượng từ/đến file JSON.
 */
public class JsonFileService {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Đọc danh sách đối tượng từ file JSON.
     * @param <T> kiểu đối tượng
     * @param filePath đường dẫn file JSON
     * @param clazz class của đối tượng
     * @return danh sách đối tượng
     */
    public static <T> List<T> readListFromFile(String filePath, Class<T> clazz) {
        try {
            return objectMapper.readValue(
                    new File(filePath),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, clazz)
            );
        } catch (IOException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    /**
     * Ghi danh sách đối tượng vào file JSON.
     * @param <T> kiểu đối tượng
     * @param filePath đường dẫn file JSON
     * @param list danh sách đối tượng
     */
    public static <T> void writeListToFile(String filePath, List<T> list) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(filePath), list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
