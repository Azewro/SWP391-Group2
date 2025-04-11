<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Thanh toán thành công</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            padding: 50px;
            text-align: center;
            background-color: #f2f2f2;
        }
        .success-box {
            background: #ffffff;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.15);
            display: inline-block;
        }
        h2 {
            color: #28a745;
        }
        p {
            font-size: 16px;
            margin: 10px 0;
        }
        a.button {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            background: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 6px;
        }
        a.button:hover {
            background: #0056b3;
        }
    </style>
</head>
<body>
<div class="success-box">
    <h2>🎉 Thanh toán thành công!</h2>
    <p>Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi.</p>
    <p>Đơn hàng của bạn đã được xác nhận.</p>
    <a href="index.jsp" class="button">Quay về trang chủ</a>
</div>
</body>
</html>
