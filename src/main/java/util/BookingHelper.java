package util;

import java.util.UUID;

public class BookingHelper {
    public static String generateTempOrderId() {
        return UUID.randomUUID().toString().substring(0, 8); // Ví dụ: "9f1c7a32"
    }
}
