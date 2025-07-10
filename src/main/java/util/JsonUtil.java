package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Utility class để đọc & ghi dữ liệu JSON cho các object và list.
 */
public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Đọc một object từ file JSON.
     */
    public static <T> T readObjectFromFile(String filePath, Class<T> clazz) {
        try {
            return objectMapper.readValue(new File(filePath), clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Đọc danh sách đối tượng từ file JSON.
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
     * Ghi object vào file JSON.
     */
    public static <T> void writeObjectToFile(String filePath, T object) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ghi danh sách đối tượng vào file JSON.
     */
    public static <T> void writeListToFile(String filePath, List<T> list) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
