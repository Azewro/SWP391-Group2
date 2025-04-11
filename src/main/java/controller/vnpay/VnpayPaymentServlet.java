package controller.vnpay;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.vnpay.VnpayConfig;
import util.vnpay.VnpayHelper;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@WebServlet("/vnpay_payment")
public class VnpayPaymentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String amount = request.getParameter("amount"); // đơn vị VND
        String orderRef = "ORD" + System.currentTimeMillis(); // mã giao dịch tạm thời

        String vnp_CreateDate = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
        String vnp_IpAddr = request.getRemoteAddr();

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", VnpayConfig.vnp_Version);
        vnp_Params.put("vnp_Command", VnpayConfig.vnp_Command);
        vnp_Params.put("vnp_TmnCode", VnpayConfig.vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(Integer.parseInt(amount) * 100));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", orderRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan ve xe");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", VnpayConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);

        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        for (String name : fieldNames) {
            String value = vnp_Params.get(name);
            hashData.append(name).append('=').append(value);
            query.append(name).append('=').append(URLEncoder.encode(value, StandardCharsets.US_ASCII));
            if (!name.equals(fieldNames.get(fieldNames.size() - 1))) {
                hashData.append('&');
                query.append('&');
            }
        }

        String vnp_SecureHash = VnpayHelper.hmacSHA512(VnpayConfig.vnp_HashSecret, hashData.toString());
        query.append("&vnp_SecureHash=").append(vnp_SecureHash);

        String paymentUrl = VnpayConfig.vnp_PayUrl + "?" + query.toString();
        response.sendRedirect(paymentUrl);
    }
}
