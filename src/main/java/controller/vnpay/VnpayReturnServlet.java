package controller.vnpay;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/vnpay_return")
public class VnpayReturnServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");
        String vnp_TxnRef = request.getParameter("vnp_TxnRef");
        String vnp_Amount = request.getParameter("vnp_Amount");

        if ("00".equals(vnp_ResponseCode)) {
            request.setAttribute("message", "✅ Thanh toán thành công!<br>Mã giao dịch: " + vnp_TxnRef +
                    "<br>Số tiền: " + (Integer.parseInt(vnp_Amount)/100) + " VND");
        } else {
            request.setAttribute("message", "❌ Thanh toán thất bại hoặc bị hủy.");
        }

        request.getRequestDispatcher("/payment-success.jsp").forward(request, response);
    }
}

