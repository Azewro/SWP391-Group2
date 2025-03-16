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
    .seat {
        display: flex;
        flex-direction: column;
        align-items: center;
        margin: 5px;
        position: relative;
    }

    .seat-icon {
        position: relative;
        display: inline-block;
    }

    .seat img {
        width: 32px;
        height: auto;
    }

    .seat-number {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        font-size: 12px;
        font-weight: bold;
        color: white; /* Để dễ nhìn khi nằm trên icon */
        padding: 2px 5px;
        border-radius: 3px;
        cursor: pointer;
    }

    .seat span {
        font-size: 12px;
        color: #A2ABB3;
    }
    .available {
        cursor: pointer;
    }

    .selected {
        background-color: pink !important;
        border-radius: 5px;
        padding: 5px;
    }


</style>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css">
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
    </head>
    <body>
        <jsp:include page="/components/header.jsp"/>

        <div class="container">
            <div class="container mt-4">
                <h2 class="mb-4">Thông tin chuyến</h2>
                <div class="col-md-4 d-flex align-items-center">
                    <span class="fw-semibold text-primary">${route.startLocation.name}</span>
                    <img src="https://futabus.vn/images/icons/ic_double_arrow.svg" alt="arrow" class="mx-2" width="20">
                    <span class="fw-semibold text-dark">${route.endLocation.name}</span>
                </div>
                <p><b>Xe: </b>${bus.busType}</p>
                <p><b>Tài xế: </b>${driver.fullName}</p>
                <p><b>Thời gian xuất bến: </b>${busTrip.departureTime}</p>
                <p><b>Thời gian đến dự kiến: </b>${busTrip.arrivalTime}</p>
                <p><b>Giá vé: </b>${busTrip.currentPrice}VNĐ</p>
                <hr>
                <h2 class="mb-4">Chọn ghế</h2>
                <div style=" display: flex; justify-content: space-evenly;">

                    <table style="width: 70%">
                        <tbody>
                            <c:forEach var="seat" items="${seats}" varStatus="status">
                                <c:if test="${status.index % 3 == 0}">
                                    <tr>
                                    </c:if>

                                    <td>
                                        <div class="seat ${seat.available ? 'available' : 'disabled'}" 
                                             title="${seat.seatType}" 
                                             onclick="toggleSeatSelection(this)"> 
                                            <div class="seat-icon">
                                                <img class="seat-img"
                                                     width="32"
                                                     src="${seat.available ? 'https://futabus.vn/images/icons/seat_active.svg' : 'https://futabus.vn/images/icons/seat_disabled.svg'}"
                                                     alt="seat icon">
                                                <span class="seat-number">${seat.seatNumber}</span> 
                                            </div>
                                        </div>
                                    </td>

                                    <c:if test="${status.index % 3 == 2 or status.last}">
                                    </tr>
                                </c:if>
                            </c:forEach>
                        </tbody>


                    </table>

                    <div class="ml-4 mt-5 flex flex-col gap-4 text-[13px] font-normal" style="width: 18%">
                        <span style="display: flex; align-items: center; justify-content: center;"><div class="" style="background-color: #D5D9DD; height: 14px; width:  14px; border:#C0C6CC; margin-right: 5px; "></div> Đã bán</span>
                        <span style="display: flex; align-items: center; justify-content: center;"><div class="" style="background-color: #FDEDE8; height: 14px; width:  14px; border:#F8BEAB; margin-right: 5px; "></div> Đang chọn</span>
                        <span style="display: flex; align-items: center; justify-content: center;"><div class="" style="background-color: #DEF3FF; height: 14px; width:  14px; border:#96C5E7; margin-right: 5px; "></div> Còn trống</span>
                    </div>
                </div>
                <hr>
                <div class="row">
                    <!-- Cột Form -->
                    <div class="col-md-6">
                        <div class="card shadow-sm">
                            <div class="card-header bg-primary text-white">Nhập Thông Tin</div>
                            <div class="card-body">
                                <form>
                                    <div class="mb-3">
                                        <label class="form-label">Họ và Tên</label>
                                        <input type="text" class="form-control" placeholder="Nhập họ tên">
                                    </div>
                                    <div class="mb-3">
                                        <label class="form-label">Email</label>
                                        <input type="email" class="form-control" placeholder="Nhập email">
                                    </div>
                                    <div class="mb-3">
                                        <label class="form-label">Số Điện Thoại</label>
                                        <input type="tel" class="form-control" placeholder="Nhập số điện thoại">
                                    </div>
                                    <button type="submit" class="btn btn-primary w-100">Gửi</button>
                                </form>
                            </div>
                        </div>
                    </div>

                    <!-- Cột Thông Tin -->
                    <div class="col-md-6">
                        <div class="card shadow-sm">
                            <div class="card-header bg-success text-white">ĐIỀU KHOẢN & LƯU Ý</div>
                            <div class="card-body">
                                (*) Quý khách vui lòng có mặt tại bến xuất phát của xe trước ít nhất 30 phút giờ xe khởi hành, mang theo thông báo đã thanh toán vé thành công có chứa mã vé được gửi từ hệ thống FUTA BUS LINES. Vui lòng liên hệ Trung tâm tổng đài 1900 6067 để được hỗ trợ.
                                <br>                                <br>

                                (*) Nếu quý khách có nhu cầu trung chuyển, vui lòng liên hệ Tổng đài trung chuyển 1900 6918 trước khi đặt vé. Chúng tôi không đón/trung chuyển tại những điểm xe trung chuyển không thể tới được.
                            </div>
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
                <script>
                    function toggleSeatSelection(seatElement) {
                        let img = seatElement.querySelector(".seat-img"); // Lấy ảnh trong seat

                        // Kiểm tra nếu ghế đang ở trạng thái "active" thì chuyển thành "selecting" và ngược lại
                        if (img.src.includes("seat_active.svg")) {
                            img.src = "https://futabus.vn/images/icons/seat_selecting.svg";
                        } else if (img.src.includes("seat_selecting.svg")) {
                            img.src = "https://futabus.vn/images/icons/seat_active.svg";
                        }
                    }


                </script>
                </body>
                </html>
