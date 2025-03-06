<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bus Ticket System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    <style>
        /* Header */
        .navbar {
            background-color: #121212; /* Đen đậm */
            border-bottom: 2px solid rgba(255, 255, 255, 0.1); /* Viền dưới */
        }
        .navbar-brand {
            font-weight: bold;
            color: white;
        }
        .navbar-toggler {
            border: none;
        }
        .navbar-toggler:focus {
            box-shadow: none;
        }

        /* Search bar */
        .search-bar input {
            background-color: #333;
            color: white;
            border: 1px solid rgba(255, 255, 255, 0.3);
        }
        .search-bar input::placeholder {
            color: rgba(255, 255, 255, 0.6);
        }

        /* Nút mở Sidebar */
        .sidebar-toggle-btn {
            background: none;
            border: none;
            color: white;
            font-size: 20px;
            cursor: pointer;
        }
    </style>
</head>
<body>

<!-- Header -->
<nav class="navbar navbar-expand-lg navbar-dark">
    <div class="container-fluid">
        <!-- Nút mở Sidebar -->
        <button class="sidebar-toggle-btn" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasSidebar">
            <i class="fas fa-bars"></i>
        </button>

        <!-- Logo -->
        <a class="navbar-brand ms-2" href="#">Bus Ticket System</a>

        <!-- Search Bar -->
        <form class="d-flex ms-auto search-bar">
            <input class="form-control me-2" type="search" placeholder="Tìm kiếm...">
            <button class="btn btn-outline-light" type="submit"><i class="fas fa-search"></i></button>
        </form>

        <!-- User Dropdown -->
        <ul class="navbar-nav ms-3">
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-bs-toggle="dropdown">
                    <i class="fas fa-user"></i> Admin
                </a>
                <ul class="dropdown-menu dropdown-menu-end">
                    <li><a class="dropdown-item" href="#">Cài đặt</a></li>
                    <li><a class="dropdown-item" href="#">Nhật ký hoạt động</a></li>
                    <li><hr class="dropdown-divider"></li>
                    <li><a class="dropdown-item" href="logout.jsp">Đăng xuất</a></li>
                </ul>
            </li>
        </ul>
    </div>
</nav>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
