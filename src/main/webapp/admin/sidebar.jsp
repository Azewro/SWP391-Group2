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
    text-decoration: none;
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

  /* Dropdown menu */
  .submenu {
    display: none;
    padding-left: 20px;
  }

  .submenu .nav-link {
    font-size: 14px;
  }

  .submenu-active {
    display: block;
  }
</style>

<!-- Sidebar -->
<div class="offcanvas offcanvas-start text-light" id="offcanvasSidebar">
  <div class="offcanvas-header">
    <h5 class="offcanvas-title">Quản lý hệ thống</h5>
    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="offcanvas"></button>
  </div>
  <div class="offcanvas-body">
    <nav class="nav flex-column">
      <a class="nav-link ${pageName == 'dashboard' ? 'active' : ''}" href="dashboard.jsp">
        <i class="fas fa-tachometer-alt"></i> Dashboard
      </a>
      <a class="nav-link ${pageName == 'user_list' ? 'active' : ''}" href="users">
        <i class="fas fa-users"></i> Người dùng
      </a>
      <a class="nav-link ${pageName == 'route_list' ? 'active' : ''}" href="routes">
        <i class="fas fa-map-marker-alt"></i> Tuyến đường
      </a>
      <a class="nav-link ${pageName == 'bus_stop_list' ? 'active' : ''}" href="bus_stops">
        <i class="fas fa-bus"></i> Điểm dừng
      </a>
      <a class="nav-link ${pageName == 'bus_list' ? 'active' : ''}" href="bus">
        <i class="fas fa-bus"></i> Danh sách xe bus
      </a>
      <a class="nav-link ${pageName == 'bus_trips' ? 'active' : ''}" href="admin-bus-trips.jsp">
        <i class="fas fa-route"></i> Danh sách Trips
      </a>

      <hr class="text-white">

      <!-- Quản lý đơn hàng -->
      <a class="nav-link" href="#" onclick="toggleMenu('orderMenu')">
        <i class="fas fa-shopping-cart"></i> Quản lý Đơn hàng <i class="fas fa-chevron-down ms-auto"></i>
      </a>
      <div id="orderMenu" class="submenu ${pageName.startsWith('order') || pageName.startsWith('ticket') || pageName.startsWith('refund') ? 'submenu-active' : ''}">
        <a class="nav-link ${pageName == 'order_list' ? 'active' : ''}" href="orders">
          <i class="fas fa-list"></i> Danh sách đơn hàng
        </a>
        <a class="nav-link ${pageName == 'ticket_list' ? 'active' : ''}" href="tickets">
          <i class="fas fa-ticket-alt"></i> Danh sách vé
        </a>
        <a class="nav-link ${pageName == 'refund_list' ? 'active' : ''}" href="refunds">
          <i class="fas fa-money-bill"></i> Hoàn tiền
        </a>
      </div>

      <hr class="text-white">

      <!-- Quản lý phản hồi khách hàng -->
      <a class="nav-link" href="#" onclick="toggleMenu('feedbackMenu')">
        <i class="fas fa-comments"></i> Quản lý Phản hồi <i class="fas fa-chevron-down ms-auto"></i>
      </a>
      <div id="feedbackMenu" class="submenu ${pageName.startsWith('feedback') ? 'submenu-active' : ''}">
        <a class="nav-link ${pageName == 'feedback_list' ? 'active' : ''}" href="feedback?action=list">
          <i class="fas fa-list"></i> Danh sách phản hồi
        </a>
        <a class="nav-link ${pageName == 'feedback_pending' ? 'active' : ''}" href="feedback?action=pending">
          <i class="fas fa-hourglass-half"></i> Phản hồi chờ duyệt
        </a>
        <a class="nav-link ${pageName == 'feedback_approved' ? 'active' : ''}" href="feedback?action=approved">
          <i class="fas fa-check-circle"></i> Phản hồi đã duyệt
        </a>
        <a class="nav-link ${pageName == 'feedback_rejected' ? 'active' : ''}" href="feedback?action=rejected">
          <i class="fas fa-times-circle"></i> Phản hồi bị từ chối
        </a>
      </div>


      <hr class="text-white">

      <a class="nav-link" href="logout.jsp">
        <i class="fas fa-sign-out-alt"></i> Đăng xuất
      </a>
    </nav>
  </div>
</div>

<!-- JavaScript -->
<script>
  function toggleMenu(menuId) {
    let menu = document.getElementById(menuId);
    if (menu.classList.contains('submenu-active')) {
      menu.classList.remove('submenu-active');
    } else {
      menu.classList.add('submenu-active');
    }
  }
</script>
