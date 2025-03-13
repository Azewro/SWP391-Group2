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
  </style>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <header class="header">
  <div class="logo">
    <img src="<%= request.getContextPath() %>/assets/images/logo.png" alt="FUTA Bus Lines">
  </div>
  <nav class="nav-menu">
    <ul>
      <li><a href="home">TRANG CHỦ</a></li>
      <li><a href="bus-schedule">LỊCH TRÌNH</a></li>
      <li><a href="#">TRA CỨU VÉ</a></li>
      <li><a href="#">TIN TỨC</a></li>
      <li><a href="#">HÓA ĐƠN</a></li>
      <li><a href="#">LIÊN HỆ</a></li>
      <li><a href="#">VỀ CHÚNG TÔI</a></li>
    </ul>
  </nav>
  <div class="login-btn">
    <button class="login-btn" onclick="window.location.href='login.jsp'">
      Đăng Nhập
    </button>
  </div>
  <div class="login-btn" >
    <button class="login-btn" onclick="window.location.href='register.jsp'">
      Đăng Ký
    </button>
  </div>
</header>
  
        <div class="container">
    <h1 class="text-center my-4">Lịch trình xe buýt</h1>

    <!-- Search Form -->
    <form action="bus-schedule" method="get" class="search-bar">
        <input type="text" name="startLocation" placeholder="Nhập điểm đi" value="${startLocation}" />
        <span>⇄</span>
        <input type="text" name="endLocation" placeholder="Nhập điểm đến" value="${endLocation}" />
        <button type="submit">Tìm kiếm</button>
    </form>

    <!-- Bus Schedule Table -->
    <table class="table">
        <thead>
        <tr>
            <th>Tuyến xe</th>
            <th>Quãng đường</th>
            <th>Thời gian hành trình</th>
            <th>Giá vé</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="route" items="${busSchedules}">
            <tr class="route-row">
                <td>${route.startLocation.name} ⇄ ${route.endLocation.name}</td>
                <td>${route.distance} km</td>
                <td>
                    <c:choose>
                        <c:when test="${route.estimatedDuration >= 60}">
                            ${route.estimatedDuration / 60} giờ ${route.estimatedDuration % 60} phút
                        </c:when>
                        <c:otherwise>
                            ${route.estimatedDuration} phút
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${route.basePrice != null}">
                            ${route.basePrice} đ
                        </c:when>
                        <c:otherwise>
                            ---
                        </c:otherwise>
                    </c:choose>
                </td>
                <td><button class="search-button">Tìm tuyến xe</button></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
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
