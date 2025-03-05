<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<div id="layoutSidenav">
    <div id="layoutSidenav_nav" class="sidebar bg-dark vh-100">
        <nav class="sb-sidenav accordion sb-sidenav-dark vh-100">
            <div class="sb-sidenav-menu">
                <div class="nav flex-column">
                    <!-- Tiêu đề -->
                    <div class="sb-sidenav-menu-heading text-light fs-5 fw-bold"> I am the bone of my sword</div>

                    <!-- Dashboard -->
                    <a class="nav-link text-light" href="dashboard.jsp">
                        <i class="fas fa-tachometer-alt"></i> Dashboard
                    </a>

                    <!-- Tuyến đường -->
                    <a class="nav-link text-light" href="route_list.jsp">
                        <i class="fas fa-map-marker-alt"></i> Tuyến đường
                    </a>

                    <!-- Người dùng -->
                    <a class="nav-link text-light" href="user_list.jsp">
                        <i class="fas fa-users"></i> Người dùng
                    </a>
                    <a class="nav-link text-light" href="blog">
                        <i class="fas fa-users"></i> Bài đăng
                    </a>
                    <a class="nav-link text-light" href="blog-category">
                        <i class="fas fa-users"></i> Danh mục bài đăng
                    </a>
                </div>
            </div>
        </nav>
    </div>
</div>


<!-- JavaScript Toggle Sidebar -->
<script>
    document.addEventListener("DOMContentLoaded", function () {
        const sidebarToggle = document.getElementById("sidebarToggle");
        const sidebar = document.getElementById("layoutSidenav_nav");
        sidebarToggle.addEventListener("click", function () {
            sidebar.classList.toggle("d-none");
        });
    });
</script>
