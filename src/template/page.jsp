<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>G2Bus</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css"/>
</head>
<body class="font-sans">
    <!-- HEADER -->
    <jsp:include page="header.html" />

    <!-- CONTENT -->
    <main class="p-12 text-center">
        <h1 class="text-2xl font-bold">Chào mừng đến với G2Bus</h1>
        <p class="text-gray-600">Hệ thống đặt vé xe khách hàng đầu Việt Nam.</p>
        <div style="height: 150vh;"></div> <!-- Tạo nội dung dài để có thể cuộn -->
    </main>
    
    <!-- FOOTER -->
    <jsp:include page="footer.html" />
</body>
</html>
