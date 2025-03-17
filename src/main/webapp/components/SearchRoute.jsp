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
    .bus-list {
        display: flex;
        flex-direction: column;
        gap: 12px;
        margin-top: 15px;
    }

    /* Card chứa thông tin từng xe buýt */
    .bus-card {
        display: flex;
        align-items: center;
        justify-content: space-between;
        background: #fff;
        padding: 16px 20px;
        border-radius: 10px;
        border: 1px solid #ddd;
        box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
        transition: transform 0.2s ease-in-out;
    }

    .bus-card:hover {
        transform: translateY(-3px);
    }

    /* Thiết lập flex container cho các cột */
    .bus-card > .ant-col {
        display: flex;
        align-items: center;
        justify-content: center;
    }

    /* Cột đầu tiên: Biển số + icon + loại xe */
    .bus-card .ant-col-6 {
        justify-content: flex-start;
        gap: 10px;
        font-size: 16px;
        font-weight: 500;
    }

    /* Số ghế, Trạng thái, Bảo trì cuối đều căn giữa */
    .bus-card .ant-col-2,
    .bus-card .ant-col-3,
    .bus-card .ant-col-4 {
        font-size: 14px;
        font-weight: 500;
    }

    /* Định dạng badge trạng thái */
    .badge {
        display: inline-block;
        padding: 6px 12px;
        border-radius: 12px;
        font-size: 14px;
        font-weight: bold;
        min-width: 120px;
        text-align: center;
    }

    /* Màu sắc badge */
    .bg-success {
        background-color: #28a745;
        color: white;
    }

    .bg-danger {
        background-color: #dc3545;
        color: white;
    }

    /* Nút button */
    .button-default {
        background-color: #007bff;
        color: white;
        border: none;
        padding: 8px 14px;
        border-radius: 20px;
        cursor: pointer;
        font-weight: bold;
        transition: background 0.3s ease-in-out;
    }

    .button-default:hover {
        background-color: #0056b3;
    }

    /* Responsive Design */
    @media (max-width: 768px) {
        .bus-card {
            flex-wrap: wrap;
            gap: 10px;
            padding: 12px;
        }

        .bus-card > .ant-col {
            width: 100%;
            justify-content: flex-start;
        }

        .bus-card .ant-col:last-child {
            justify-content: flex-end;
        }
    }


    .schedule-list {
        display: flex;
        flex-direction: column;
        gap: 12px;
        margin-top: 15px;
    }

    /* Card của mỗi tuyến */
    .schedule-card {
        background: #fff;
        padding: 16px 20px;
        border-radius: 10px;
        border: 1px solid #ddd;
        box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
        transition: transform 0.2s ease-in-out;
    }

    .schedule-card:hover {
        transform: translateY(-3px);
    }

    /* Dòng dữ liệu chính */
    .ant-row {
        display: flex;
        align-items: center;
        justify-content: space-evenly;
        flex-wrap: wrap;
    }

    /* Căn chỉnh các cột */
    .ant-col {
        display: flex;
        align-items: center;
    }

    /* Định dạng font chữ */
    .font-medium {
        font-weight: 600;
        font-size: 16px;
    }

    /* Định dạng màu chữ */
    .text-orange {
        color: #ff6600;
    }

    /* Button tìm tuyến */
    .button-default {
        background-color: #007bff;
        color: white;
        border: none;
        padding: 8px 14px;
        border-radius: 20px;
        cursor: pointer;
        font-weight: bold;
        transition: background 0.3s ease-in-out;
    }

    .button-default:hover {
        background-color: #0056b3;
    }

    /* Responsive Design */
    @media (max-width: 768px) {
        .schedule-card {
            padding: 12px;
        }

        .ant-row {
            flex-direction: column;
            gap: 8px;
        }

        .ant-col {
            width: 100%;
            justify-content: flex-start;
        }

        .ant-col:last-child {
            justify-content: flex-end;
        }
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
            <h2 class="mt-4">Danh sách tuyến đường</h2>

            <form method="get" action="SearchRoute">
                <div class="row mb-3">
                    <div class="col-md-4">
                        <input type="text" name="search" class="form-control" placeholder="Tìm theo tên tuyến...">
                    </div>
                    <div class="col-md-3">
                        <select name="location" class="form-control">
                            <option value="">Chọn địa điểm</option>
                            <!-- Lặp danh sách địa điểm -->
                            <c:forEach var="location" items="${locations}">
                                <option value="${location.locationId}">${location.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-2">
                        <button type="submit" class="btn btn-primary"><i class="fas fa-search"></i> Tìm kiếm</button>
                    </div>
                </div>
            </form>
            <!-- Tiêu đề của danh sách tuyến -->
            <div class="row bg-light p-3 rounded">
                <div class="col-md-4 fw-bold">Tuyến xe</div>
                <div class="col-md-2 fw-bold">Loại xe</div>
                <div class="col-md-2 fw-bold">Quãng đường</div>
                <div class="col-md-2 fw-bold">Thời gian hành trình</div>
                <div class="col-md-2 fw-bold">Giá vé</div>
            </div>

            <!-- Danh sách các tuyến -->
            <div class="container mt-3">
                <c:forEach var="route" items="${routes}">
                    <div class="row bg-white shadow-sm p-3 mb-3 rounded align-items-center">
                        <div class="col-md-4 d-flex align-items-center">
                            <span class="fw-semibold text-primary">${route.startLocation.name}</span>
                            <img src="https://futabus.vn/images/icons/ic_double_arrow.svg" alt="arrow" class="mx-2" width="20">
                            <span class="fw-semibold text-dark">${route.endLocation.name}</span>
                        </div>
                        <div class="col-md-2">Xe khách</div>
                        <div class="col-md-2">${route.distance} km</div>
                        <div class="col-md-2">${route.estimatedDuration}</div>
                        <div class="col-md-2 fw-bold text-danger">${route.basePrice} VNĐ</div>
                        <div class="col-md-2 text-end">
                            <a type="button" class="btn btn-primary btn-sm" href="./ListTrip?RouteId=${route.routeId}">
                                Tìm tuyến xe
                            </a>
                        </div>
                    </div>
                </c:forEach>
            </div>

        </div>


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
