<%@ page import="model.User" %><%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 2/17/2025
  Time: 4:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Hệ Thống Bán Vé Xe Buýt</title>
  <link rel="stylesheet" href="styles.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css">
  <style>
    .user-section {
      display: flex;
      align-items: center;
      gap: 15px;
    }

    .profile-menu {
      position: relative;
    }

    .profile-btn {
      background: white;
      color: #ff6200;
      border: none;
      padding: 10px 25px;
      border-radius: 25px;
      cursor: pointer;
      font-size: 16px;
      font-weight: bold;
    }

    .profile-dropdown {
      display: none;
      position: absolute;
      top: 40px;
      right: 0;
      background: white;
      border-radius: 8px;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
      width: 180px;
      overflow: hidden;
    }

    .profile-dropdown a {
      display: block;
      padding: 10px 15px;
      text-decoration: none;
      color: black;
      font-size: 16px;
    }

    .profile-dropdown a:hover {
      background: #ff6200;
      color: white;
    }



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
  </style>
</head>
<body>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>

<header class="header bg-dark text-white">
  <!-- Logo trên cùng -->
  <div class="text-center py-2">
    <img src="<%= request.getContextPath() %>/assets/images/logo.png" alt="G2 Bus Ticket" height="60">
  </div>

  <!-- Menu & Nút đăng nhập bên dưới -->
  <div class="container">
    <div class="d-flex justify-content-between align-items-center">
      <!-- Menu -->
      <nav class="nav">
        <a class="nav-link text-white fw-bold" href="index.jsp">TRANG CHỦ</a>
        <a class="nav-link text-white fw-bold" href="bus-schedule">LỊCH TRÌNH</a>
        <a class="nav-link text-white fw-bold" href="bookings">ĐẶT VÉ</a>
        <a class="nav-link text-white fw-bold" href="#">TRA CỨU VÉ</a>
        <a class="nav-link text-white fw-bold" href="#">TIN TỨC</a>
        <a class="nav-link text-white fw-bold" href="#">HÓA ĐƠN</a>
        <a class="nav-link text-white fw-bold" href="#">LIÊN HỆ</a>
        <a class="nav-link text-white fw-bold" href="#">VỀ CHÚNG TÔI</a>
      </nav>

      <!-- Nút đăng nhập / đăng ký -->
      <div>
        <%
          User userSession = (User) session.getAttribute("user");
          if (userSession != null) {
        %>
        <div class="dropdown">
          <button class="btn btn-outline-light dropdown-toggle" type="button" id="profileDropdown" data-bs-toggle="dropdown" aria-expanded="false">
            <i class="bi bi-person-circle"></i> <%= userSession.getUsername() %>
          </button>
          <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="profileDropdown">
            <li><a class="dropdown-item" href="userprofile.jsp"><i class="bi bi-person"></i> Thông tin cá nhân</a></li>
            <li><a class="dropdown-item" href="change-password.jsp"><i class="bi bi-key"></i> Đổi mật khẩu</a></li>
            <li><a class="dropdown-item text-danger" href="logout.jsp"><i class="bi bi-box-arrow-right"></i> Đăng xuất</a></li>
          </ul>
        </div>
        <%
        } else {
        %>
        <a href="login.jsp" class="btn btn-outline-light me-2">Đăng Nhập</a>
        <a href="register.jsp" class="btn btn-warning">Đăng Ký</a>
        <%
          }
        %>
      </div>
    </div>
  </div>
</header>


  <!-- content -->
  <%@ include file="./components/popularRoutes.jsp" %>


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

