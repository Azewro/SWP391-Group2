<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Order" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Lịch sử đơn hàng</title>
    <style>
        table { width: 80%; margin: auto; border-collapse: collapse; margin-top: 30px; }
        th, td { border: 1px solid #ccc; padding: 10px; text-align: center; }
        th { background: #f2f2f2; }
        h2 { text-align: center; margin-top: 20px; }
        .btn {
            padding: 5px 10px;
            background: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 4px;
        }
    </style>
</head>
<body>
    <jsp:include page="/components/header.jsp"/>

    <h2>Lịch sử đơn hàng của bạn</h2>

    <c:choose>
        <c:when test="${empty orders}">
            <p style="text-align:center;">Bạn chưa đặt đơn hàng nào.</p>
        </c:when>
        <c:otherwise>
            <table>
                <tr>
                    <th>Mã đơn</th>
                    <th>Ngày đặt</th>
                    <th>Tổng tiền</th>
                    <th>Chi tiết</th>
                </tr>
                <c:forEach var="order" items="${orders}">
                    <tr>
                        <td>#${order.orderId}</td>
                        <td>${order.orderDate}</td>
                        <td>${order.totalAmount} đ</td>
                        <td>
                            <a class="btn" href="confirmBooking.jsp?orderId=${order.orderId}">Xem vé</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>
</body>
</html>
