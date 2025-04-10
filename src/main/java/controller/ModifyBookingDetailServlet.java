package controller;

import dao.BookingDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.BusTrip;
import model.OrderDetail;
import model.Seat;
import model.Ticket;

import java.io.IOException;

@WebServlet("/modify-booking-detail")
public class ModifyBookingDetailServlet extends HttpServlet {
    private BookingDAO bookingDAO = new BookingDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int orderDetailId = Integer.parseInt(request.getParameter("orderDetailId"));
            // Lấy OrderDetail theo ID
            OrderDetail orderDetail = bookingDAO.getOrderDetailById(orderDetailId);
            if (orderDetail == null) {
                response.getWriter().println("Không tìm thấy chi tiết đặt vé!");
                return;
            }

            // Đưa orderDetail lên JSP để hiển thị
            request.setAttribute("orderDetail", orderDetail);
            request.getRequestDispatcher("modify-booking-detail.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Đã xảy ra lỗi: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderDetail orderDetail = null; // Đẩy lên ngoài try để không lỗi scope

        try {
            // Lấy tham số từ form
            int orderDetailId = Integer.parseInt(request.getParameter("orderDetailId"));
            int newSeatId = Integer.parseInt(request.getParameter("seatId"));
            int newTripId = Integer.parseInt(request.getParameter("tripId"));

            // Lấy OrderDetail theo ID
            orderDetail = bookingDAO.getOrderDetailById(orderDetailId);
            if (orderDetail == null) {
                response.getWriter().println("Không tìm thấy chi tiết đặt vé!");
                return;
            }

            // Lấy Ticket từ OrderDetail
            Ticket ticket = orderDetail.getTicket();
            if (ticket == null) {
                response.getWriter().println("Không tìm thấy vé để cập nhật!");
                return;
            }

            // Cập nhật ghế mới
            Seat newSeat = new Seat();
            newSeat.setSeatId(newSeatId);
            ticket.setSeat(newSeat);

            // Cập nhật chuyến xe mới
            BusTrip newTrip = new BusTrip();
            newTrip.setTripId(newTripId);
            ticket.setTrip(newTrip);

            // Gọi DAO để update Ticket
            bookingDAO.updateTicket(ticket);

            // Redirect về trang danh sách vé của booking
            response.sendRedirect("modify-booking?orderId=" + orderDetail.getOrder().getOrderId());
            return; // Dừng luôn sau redirect

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Đã xảy ra lỗi: " + e.getMessage());
        }

        // Nếu lỗi hoặc cần quay lại view, forward về trang chi tiết
        request.setAttribute("orderDetail", orderDetail);
        request.getRequestDispatcher("modify-booking-detail.jsp").forward(request, response);
    }
}
