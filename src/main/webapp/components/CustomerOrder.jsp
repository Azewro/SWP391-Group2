<%-- 
    Document   : busSchedule
    Created on : 26 thg 2, 2025, 23:21:16
    Author     : ktleg
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<style>
    body {
        font-family: Arial, sans-serif;
        margin: 0;
        padding: 0;
        background-color: #fff;
    }
    .header {
        background: #333;
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 15px 50px;
        color: white;
    }
    .logo img {
        height: 60px;
    }
    .nav-menu ul {
        list-style: none;
        display: flex;
        gap: 25px;
        padding: 0;
    }
    .nav-menu ul li a {
        text-decoration: none;
        color: white;
        font-weight: bold;
        font-size: 18px;
        text-transform: uppercase;
    }
    .login-btn button {
        background: white;
        color: #ff6200;
        border: none;
        padding: 10px 25px;
        border-radius: 25px;
        cursor: pointer;
        font-size: 16px;
        font-weight: bold;
    }
    .login-section {
        display: flex;
        justify-content: center;
        align-items: center;
        height: 90vh;
        background: linear-gradient(180deg, #ff6200, #ff7e29);
        padding: 50px;
    }
    .login-container {
        background: white;
        padding: 50px;
        border-radius: 15px;
        display: flex;
        gap: 50px;
        box-shadow: 0 0 15px rgba(0,0,0,0.2);
        max-width: 850px;
    }
    .login-form form {
        display: flex;
        flex-direction: column;
        gap: 15px;
    }
    .input-group input {
        border: 1px solid #ccc;
        padding: 12px;
        font-size: 16px;
        width: 100%;
        border-radius: 5px;
    }
    .login-form button {
        background: #ff6200;
        color: white;
        padding: 14px;
        border: none;
        cursor: pointer;
        border-radius: 5px;
        font-size: 18px;
        font-weight: bold;
    }
    .footer {
        background: #f8f8f8;
        padding: 30px;
        text-align: center;
    }
    .footer-content {
        display: flex;
        justify-content: space-around;
        padding: 20px;
    }
    .footer-links ul {
        list-style: none;
        padding: 0;
    }
    .footer-links ul li a {
        text-decoration: none;
        color: black;
        font-size: 16px;
    }
    .search-bar {
        display: flex;
        justify-content: center;
        margin: 20px 0;
    }
    .search-bar input {
        width: 30%;
        margin-right: 10px;
        padding: 10px;
        border: 1px solid #ddd;
        border-radius: 5px;
    }
    .search-bar button {
        background-color: #ff7e29;
        color: white;
        border: none;
        padding: 10px 20px;
        border-radius: 5px;
        cursor: pointer;
    }
    table {
        width: 80%;
        margin: 20px auto;
        border-collapse: collapse;
    }
    th, td {
        padding: 10px;
        text-align: center;
        border-bottom: 1px solid #ddd;
    }
    th {
        background-color: #f2f2f2;
    }
    .route-row {
        background-color: #ffefef;
        font-weight: bold;
        color: #e74c3c;
    }
    .search-button {
        background-color: #ffe6e6;
        color: #e74c3c;
        border: none;
        padding: 5px 10px;
        border-radius: 5px;
    }
    .pagination {
        display: flex;
        justify-content: center;
        list-style: none;
        padding: 0;
        margin-top: 20px;
    }

    .page-item {
        margin: 0 5px;
    }

    .page-link {
        display: block;
        padding: 8px 12px;
        text-decoration: none;
        color: #007bff;
        border: 1px solid #ddd;
        border-radius: 4px;
        background-color: #fff;
        transition: background-color 0.3s, color 0.3s;
    }

    .page-link:hover {
        background-color: #007bff;
        color: #fff;
    }

    .page-item.active .page-link {
        background-color: #007bff;
        color: #fff;
        font-weight: bold;
        border-color: #007bff;
    }

    .page-item.disabled .page-link {
        color: #ccc;
        pointer-events: none;
        border-color: #ddd;
        background-color: #f8f9fa;
    }

</style>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="/components/header.jsp"/>

        <div class="container">
            <div class="container mt-4">
                <h2 class="mb-4">Danh sách đơn hàng</h2>

                <!-- Bảng hiển thị danh sách đơn hàng -->
                <!-- Include jQuery and DataTables CSS & JS -->
                <link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css">
                <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
                <script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>

                <div class="table-responsive">
                    <table class="table table-striped table-hover" id="orderTable">
                        <thead class="table-dark">
                            <tr>
                                <th>ID</th>
                                <th>Khách hàng</th>
                                <th>Ngày đặt</th>
                                <th>Tổng tiền</th>
                                <th>Trạng thái</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="order" items="${orders}">
                                <tr>
                                    <td>${order.orderId}</td>
                                    <td>${order.user.fullName}</td>
                                    <td>${order.orderDate}</td>
                                    <td>${order.totalAmount} VNĐ</td>
                                    <td>
                                        <span class="badge
                                              ${order.status == 'Pending' ? 'bg-warning' :
                                                order.status == 'Completed' ? 'bg-success' :
                                                'bg-danger'}">
                                                  ${order.status}
                                              </span>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>

                    <!-- Initialize DataTable -->
                    <script>
                        $(document).ready(function () {
                            $('#orderTable').DataTable({
                                "paging": true, // Enable pagination
                                "searching": true, // Enable search box
                                "ordering": true, // Enable sorting
                                "info": true, // Show info about table
                                "lengthMenu": [5, 10, 25, 50], // Change number of records per page
                                "language": {
                                    "search": "Tìm kiếm:",
                                    "lengthMenu": "Hiển thị _MENU_ mục",
                                    "info": "Hiển thị _START_ đến _END_ trong _TOTAL_ mục",
                                    "paginate": {
                                        "first": "Đầu",
                                        "last": "Cuối",
                                        "next": "Sau",
                                        "previous": "Trước"
                                    }
                                }
                            });
                        });
                    </script>


                </div>

                <footer class="footer">
                    <div class="footer-content">
                        <div class="support-info">
                            <h3>TRUNG TÂM TỔNG ĐÀI & CSKH</h3>
                            <p class="hotline">1900 6067</p>
                            <p>CÔNG TY CỔ PHẦN XE KHÁCH G2 Bus - G2 BUS LINES</p>
                            <p>Địa chỉ: Hòa Lạc, Hà Nội, Việt Nam.</p>
                            <p>Email: <a href="mailto:GPT@fpt.edu.vn">GPT@fpt.edu.vn</a></p>
                            <p>Điện thoại: 0979605489</p>
                        </div>
                        <div class="footer-links">
                            <ul>
                                <li><a href="#">Về chúng tôi</a></li>
                                <li><a href="#">Lịch trình</a></li>
                                <li><a href="#">Tuyển dụng</a></li>
                                <li><a href="#">Tin tức & Sự kiện</a></li>
                                <li><a href="#">Mạng lưới văn phòng</a></li>
                            </ul>
                        </div>
                    </div>
                </footer>
        </body>
    </html>
