<%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 2/17/2025
  Time: 4:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hệ Thống Bán Vé Xe Buýt</title>
    <link rel="stylesheet" href="styles.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css">
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
    </style>
    <script>
        function validatePassword() {
            let password = document.getElementById("password").value;
            let error = document.getElementById("passwordError");
            let regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[^\s]{8,}$/;

            if (!regex.test(password)) {
                error.innerHTML = "Mật khẩu phải có ít nhất 8 ký tự, bao gồm chữ hoa, chữ thường, số và không có khoảng trắng.";
                return false;
            } else {
                error.innerHTML = "";
                return true;
            }
        }
    </script>
    <script>
        function loginWithGoogle() {
            const clientId = "627788153739-pqbr1b10t2m0ggsrvfjihc5tacgi2jes.apps.googleusercontent.com";
            const redirectUri = encodeURIComponent("http://localhost:8080/SWP391_Group2_war_exploded/google-callback");
            const scope = encodeURIComponent("openid email profile");

            // Xây dựng URL đúng format
            const googleAuthUrl = "https://accounts.google.com/o/oauth2/auth"
                + "?client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + "&response_type=code"
                + "&scope=" + scope
                + "&access_type=offline"
                + "&prompt=consent";

            console.log("🔗 Google Auth URL:", googleAuthUrl); // Debug URL xem có lỗi không
            window.location.href = googleAuthUrl;
        }



    </script>
</head>
<body>
    <jsp:include page="/components/header.jsp"/>


<section class="login-section">
    <div class="login-container">
        <div class="login-info">
            <h2>G2 Bus Ticket</h2>
            <p>Cùng bạn trên mọi nẻo đường</p>
            <p>XE TRUNG CHUYỂN<br>ĐÓN - TRẢ TẬN NƠI</p>
        </div>

        <div class="login-form">
            <% String error = (String) request.getAttribute("error"); %>
            <% if (error != null) { %>
            <p style="color: red;"><%= error %></p>
            <% } %>

            <!-- ✅ Nút đăng ký bằng Google -->
<%--            <button onclick="loginWithGoogle()" data-mdb-button-init data-mdb-ripple-init class="btn btn-link btn-floating mx-1">--%>
<%--                Đăng nhập bằng Google--%>
<%--                <i class="fab fa-google"></i>--%>
<%--            </button>--%>
            <hr>

            <h3>Đăng Ký Tài Khoản Thành Viên</h3>
            <form action="register" method="post" onsubmit="return validatePassword();">
                <div class="input-group">
                    <input type="text" name="username" placeholder="Tên đăng nhập" required>
                </div>

                <div class="input-group">
                    <input type="password" id="password" name="password" placeholder="Mật khẩu" required oninput="validatePassword();">
                    <span id="passwordError" style="color:red;"></span>
                </div>

                <div class="input-group">
                    <input type="email" name="email" placeholder="Email" required>
                </div>

                <div class="input-group">
                    <input type="text" name="phone" placeholder="Số điện thoại">
                </div>

                <div class="input-group">
                    <input type="text" name="fullName" placeholder="Họ và tên" required>
                </div>

                <button type="submit">Đăng ký</button>
            </form>
        </div>
    </div>
</section>

<footer class="footer">
    <div class="footer-content">
        <div class="support-info">
            <h3>TRUNG TÂM TỔNG ĐÀI & CSKH</h3>
            <p class="hotline">1900 6067</p>
            <p>CÔNG TY CỔ PHẦN XE KHÁCH G2 Bus - G2 BUS LINES</p>
            <p>Địa chỉ: Hòa Lạc, Hà nội, Việt Nam.</p>
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

