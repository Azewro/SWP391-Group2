<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Hoàn tất đăng ký</title>
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
</head>
<body>
<h2>Hoàn tất đăng ký</h2>

<%
    String email = (String) session.getAttribute("oauth_email");
    String fullName = (String) session.getAttribute("oauth_fullName");
%>

<form action="registerOAuth" method="post" onsubmit="return validatePassword();">
    <label>Email:</label>
    <input type="email" name="email" value="<%= email %>" readonly><br>

    <label>Họ và tên:</label>
    <input type="text" name="fullName" value="<%= fullName %>" required><br>

    <label>Tên đăng nhập:</label>
    <input type="text" name="username" required><br>

    <label>Mật khẩu:</label>
    <input type="password" id="password" name="password" required oninput="validatePassword();"><br>
    <span id="passwordError" style="color:red;"></span><br>

    <button type="submit">Hoàn tất đăng ký</button>
</form>
</body>
</html>
