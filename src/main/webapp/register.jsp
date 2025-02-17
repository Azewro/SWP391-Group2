<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Đăng ký tài khoản</title>
    <script>
        function redirectToOAuth(provider) {
            window.location.href = "OAuthServlet?provider=" + provider;
        }

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
</head>
<body>
<h2>Đăng ký tài khoản</h2>

<% String error = (String) request.getAttribute("error"); %>
<% if (error != null) { %>
<p style="color: red;"><%= error %></p>
<% } %>

<!-- Đăng ký bằng Google & Facebook -->
<button onclick="redirectToOAuth('google')">Đăng ký bằng Google</button>
<button onclick="redirectToOAuth('facebook')">Đăng ký bằng Facebook</button>

<h3>Hoặc đăng ký thông thường:</h3>
<form action="register" method="post" onsubmit="return validatePassword();">
    <label>Tên đăng nhập:</label>
    <input type="text" name="username" required><br>

    <label>Mật khẩu:</label>
    <input type="password" id="password" name="password" required oninput="validatePassword();"><br>
    <span id="passwordError" style="color:red;"></span><br>

    <label>Email:</label>
    <input type="email" name="email" required><br>

    <label>Số điện thoại:</label>
    <input type="text" name="phone"><br>

    <label>Họ và tên:</label>
    <input type="text" name="fullName" required><br>

    <button type="submit">Đăng ký</button>
</form>
</body>
</html>
