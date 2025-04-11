package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;

import util.vnpay.Config;

@WebServlet("/vnpay_return")
public class VnpayReturnServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Map<String, String> fields = new HashMap<>();
        for (Enumeration<String> params = request.getParameterNames(); params.hasMoreElements(); ) {
            String fieldName = params.nextElement();
            String fieldValue = request.getParameter(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                fields.put(fieldName, fieldValue);
            }
        }

        // Lấy secure hash từ request
        String vnp_SecureHash = fields.remove("vnp_SecureHash");

        // Tạo lại chữ ký từ dữ liệu còn lại
        List<String> sortedKeys = new ArrayList<>(fields.keySet());
        Collections.sort(sortedKeys);
        StringBuilder hashData = new StringBuilder();
        for (int i = 0; i < sortedKeys.size(); i++) {
            String key = sortedKeys.get(i);
            String value = fields.get(key);
            hashData.append(key).append("=").append(value);
            if (i < sortedKeys.size() - 1) hashData.append("&");
        }

        String myHash = Config.hmacSHA512(Config.vnp_HashSecret, hashData.toString());

        if (myHash.equals(vnp_SecureHash)) {
            // ✅ Xác minh chữ ký thành công
            String responseCode = request.getParameter("vnp_ResponseCode");
            if ("00".equals(responseCode)) {
                request.setAttribute("message", "✅ Thanh toán thành công!");
            } else {
                request.setAttribute("message", "❌ Giao dịch thất bại. Mã lỗi: " + responseCode);
            }
        } else {
            request.setAttribute("message", "❌ Sai chữ ký. Không thể xác thực giao dịch.");
        }

        request.getRequestDispatcher("/payment-success.jsp").forward(request, response);
    }
}
