<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<style>
    #layoutSidenav {
        display: flex;
        height: 100vh;
    }

    #layoutSidenav_nav {
        width: 250px;
        min-height: 100vh;
        background-color: #343a40; /* Màu tối */
    }

    .sb-sidenav .nav-link {
        padding: 10px 15px;
        font-size: 16px;
    }

    .sb-sidenav .nav-link i {
        margin-right: 10px;
    }

</style>
<nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
    <!-- Nút Toggle Sidebar -->
    <button class="btn btn-link text-white" id="sidebarToggle">
        <i class="fas fa-bars"></i>
    </button>

    <!-- Logo -->
    <a class="navbar-brand ps-3 text-light fw-bold" href="dashboard.jsp">Bus Ticket System</a>

    <!-- Search Bar -->
    <form class="d-none d-md-inline-block form-inline ms-auto me-3">
        <div class="input-group">
            <input class="form-control" type="text" placeholder="Tìm kiếm...">
            <button class="btn btn-primary" type="button"><i class="fas fa-search"></i></button>
        </div>
    </form>

    <!-- User Dropdown -->
    <ul class="navbar-nav ms-auto ms-md-0 me-3 me-lg-4">
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle text-white" href="#" id="userDropdown" role="button" data-bs-toggle="dropdown">
                <i class="fas fa-user fa-fw"></i> Admin
            </a>
            <ul class="dropdown-menu dropdown-menu-end">
                <li><a class="dropdown-item" href="#">Cài đặt</a></li>
                <li><a class="dropdown-item" href="#">Nhật ký hoạt động</a></li>
                <li><hr class="dropdown-divider"></li>
                <li><a class="dropdown-item" href="logout.jsp">Đăng xuất</a></li>
            </ul>
        </li>
    </ul>
</nav>

