<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Order" %>

<%
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lịch sử đặt vé</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .container {
            margin-top: 30px;
            max-width: 900px;
        }
        .card {
            border-radius: 10px;
            padding: 20px;
        }
        .no-order {
            text-align: center;
            color: #888;
            padding: 20px;
        }
    </style>
</head>
<body>
<jsp:include page="/components/header.jsp"/>

<!-- Header -->
<header class="bg-dark text-white text-center py-3">
    <h2>🚍 Lịch sử đặt vé</h2>
</header>

<!-- Nội dung chính -->
<div class="container">
    <div class="card shadow-sm">
        <table class="table table-hover">
            <thead class="table-dark">
            <tr>
                <th>Ngày đặt</th>
                <th>Tổng tiền</th>
                <th>Trạng thái</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <c:choose>
                <c:when test="${not empty orderList}">
                    <c:forEach var="order" items="${orderList}">
                        <tr>
                            <td>
                                <c:choose>
                                    <c:when test="${order.orderDate != null}">
                                        <%= formatter.format(((Order) pageContext.getAttribute("order")).getOrderDate()) %>
                                    </c:when>
                                    <c:otherwise>Chưa xác định</c:otherwise>
                                </c:choose>
                            </td>
                            <td>${order.totalAmount} VNĐ</td>
                            <td>${order.status}</td>
                            <td>
                                <a href="modify-booking?orderId=${order.orderId}" class="btn btn-primary btn-sm">Xem chi tiết</a>
                            </td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td colspan="4" class="no-order">Bạn chưa có đơn hàng nào.</td>
                    </tr>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
