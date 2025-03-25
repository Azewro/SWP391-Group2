<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- Bootstrap CSS -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.3/css/bootstrap.min.css">

<!-- Bootstrap Icons -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">

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

<!-- Bootstrap JS Bundle (bao gồm Popper.js) -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.3/js/bootstrap.bundle.min.js"
        integrity="sha512-kdE0UPv1zjhGIXlv2rGTecrRmW2UejfoIL2ZGMSM4zYHp1XbPOEF8C8Q4PobIqyy9KX7T/3d29+Yd2ZBB8YOpg=="
        crossorigin="anonymous" referrerpolicy="no-referrer"></script>

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
                <a class="nav-link text-white fw-bold" href="home">TRANG CHỦ</a>
                <a class="nav-link text-white fw-bold" href="bus-schedule">LỊCH TRÌNH</a>
                <a class="nav-link text-white fw-bold" href="SearchBus">TÌM BUS</a>
                <a class="nav-link text-white fw-bold" href="#">TRA CỨU VÉ</a>
                <a class="nav-link text-white fw-bold" href="#">TIN TỨC</a>
                <a class="nav-link text-white fw-bold" href="promotions">KHUYẾN MÃI</a>
                <a class="nav-link text-white fw-bold" href="#">HÓA ĐƠN</a>
                <a class="nav-link text-white fw-bold" href="SearchRoute">TUYẾN ĐƯỜNG</a>
            </nav>

            <!-- Nút đăng nhập / đăng ký -->
            <div>
                <c:if test="${sessionScope.user != null}">
                    <div class="dropdown">
                        <button class="btn btn-outline-light dropdown-toggle" type="button" id="profileDropdown"
                                data-bs-toggle="dropdown" aria-expanded="false">
                            <i class="bi bi-person-circle"></i> ${sessionScope.user.getUsername()}
                        </button>
                        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="profileDropdown">
                            <li><a class="dropdown-item" href="userprofile.jsp"><i class="bi bi-person"></i> Thông tin cá nhân</a></li>
                            <li><a class="dropdown-item" href="CustomerOrder"><i class="bi bi-ticket"></i> Đơn hàng của tôi</a></li>
                            <li><a class="dropdown-item" href="change-password.jsp"><i class="bi bi-key"></i> Đổi mật khẩu</a></li>
                            <li><a class="dropdown-item text-danger" href="logout.jsp"><i class="bi bi-box-arrow-right"></i> Đăng xuất</a></li>
                        </ul>
                    </div>
                </c:if>

                <c:if test="${sessionScope.user == null}">
                    <a href="login.jsp" class="btn btn-outline-light me-2">Đăng Nhập</a>
                    <a href="register.jsp" class="btn btn-warning">Đăng Ký</a>
                </c:if>
            </div>
        </div>
    </div>
</header>
