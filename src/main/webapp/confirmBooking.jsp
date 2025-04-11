<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, java.math.BigDecimal" %>
<%@ page import="dao.OrderDAO" %>
<%@ page import="model.Ticket, model.Seat, model.BusTrip, model.Route" %>

<%
    int orderId = Integer.parseInt(request.getParameter("orderId"));
    List<Ticket> tickets = OrderDAO.getTicketsByOrder(orderId);

    BigDecimal total = BigDecimal.ZERO;
    for (Ticket t : tickets) {
        total = total.add(t.getPrice());
    }

    String status = tickets.size() > 0 ? "Paid" : "Pending";
%>

<html>
<head>
    <title>Xác nhận đặt vé</title>
    <style>
        body { font-family: Arial, sans-serif; }
        h2 { text-align: center; margin-top: 30px; }
        table { width: 80%; margin: auto; border-collapse: collapse; margin-top: 20px; }
        th, td { padding: 10px; border: 1px solid #ccc; text-align: center; }
        th { background-color: #f2f2f2; }
        .success { color: green; text-align: center; font-size: 18px; }
        .back { text-align: center; margin-top: 30px; }
        .btn {
            background: #007bff; color: white; padding: 8px 16px;
            border: none; border-radius: 4px; text-decoration: none;
        }
    </style>
</head>
<body>
<jsp:include page="/components/header.jsp"/>
<h2>🎫 Xác nhận đặt vé thành công</h2>
<p class="success">Mã đơn hàng: <strong>#<%= orderId %></strong></p>
<p class="success">Trạng thái thanh toán: <strong><%= status %></strong></p>

<table>
    <tr>
        <th>Ghế</th>
        <th>Tuyến</th>
        <th>Khởi hành</th>
        <th>Giá vé</th>
        <th>Trạng thái vé</th>
    </tr>

    <% for (Ticket ticket : tickets) {
        String seat = ticket.getSeat().getSeatNumber() + "";
        String route = ticket.getTrip().getRoute().getRouteName();
        String time = ticket.getTrip().getDepartureTime().toString();
        BigDecimal price = ticket.getPrice();
        String ticketStatus = ticket.getStatus();
    %>
    <tr>
        <td><%= seat %></td>
        <td><%= route %></td>
        <td><%= time %></td>
        <td><%= price %> đ</td>
        <td><%= ticketStatus %></td>
    </tr>
    <% } %>

    <tr>
        <th colspan="3">Tổng cộng</th>
        <th colspan="2"><%= total %> đ</th>
    </tr>
</table>

<div class="back">
    <br/>
    <a class="btn" href="index.jsp">⬅ Về trang chủ</a>
    <a class="btn" href="order-history">🧾 Xem lịch sử đơn hàng</a>
</div>

</body>
</html>
