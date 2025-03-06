<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<style>
  /* Sidebar */
  .offcanvas {
    width: 260px;
    background-color: #121212; /* Đen chủ đạo */
    border-right: 2px solid rgba(255, 255, 255, 0.1); /* Viền phải trắng nhẹ */
  }

  .offcanvas-header {
    border-bottom: 1px solid rgba(255, 255, 255, 0.1);
  }

  .offcanvas-body {
    padding: 10px;
  }

  .nav-link {
    color: white;
    padding: 12px 15px;
    font-size: 16px;
    text-decoration: none; /* Giữ liên kết click được */
    display: flex;
    align-items: center;
    transition: 0.3s;
  }

  .nav-link i {
    margin-right: 10px;
  }

  .nav-link:hover {
    background-color: rgba(255, 255, 255, 0.1); /* Hiệu ứng hover */
    border-radius: 5px;
  }

  /* Active link */
  .nav-link.active {
    background-color: rgba(255, 255, 255, 0.2);
    border-radius: 5px;
  }
</style>

<!-- Sidebar -->
<div class="offcanvas offcanvas-start text-light" id="offcanvasSidebar">
  <div class="offcanvas-header">
    <h5 class="offcanvas-title">Menu</h5>
    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="offcanvas"></button>
  </div>
  <div class="offcanvas-body">
    <nav class="nav flex-column">
      <a class="nav-link ${pageName == 'dashboard' ? 'active' : ''}" href="dashboard.jsp">
        <i class="fas fa-tachometer-alt"></i> Dashboard
      </a>
      <a class="nav-link ${pageName == 'user_list' ? 'active' : ''}" href="user_list.jsp">
        <i class="fas fa-users"></i> Người dùng
      </a>
      <a class="nav-link ${pageName == 'route_list' ? 'active' : ''}" href="route_list.jsp">
        <i class="fas fa-map-marker-alt"></i> Tuyến đường
      </a>
      <a class="nav-link ${pageName == 'bus_stop_list' ? 'active' : ''}" href="bus_stop_list.jsp">
        <i class="fas fa-bus"></i> Điểm dừng
      </a>
      <a class="nav-link ${pageName == 'bus-list' ? 'active' : ''}" href="bus-list.jsp">
        <i class="fas fa-bus"></i> Danh Sách Xe Bus
      </a>

    </nav>
  </div>
</div>
