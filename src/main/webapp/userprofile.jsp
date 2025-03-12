<%@ page import="model.User" %>
<%--
  Created by IntelliJ IDEA.
  User: LONG
  Date: 3/5/2025
  Time: 11:12 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>User Profile</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body {
      background-color: #f8f9fa;
    }
    .profile-container {
      max-width: 900px;
      margin: 50px auto;
    }
    .card {
      border-radius: 10px;
    }
    .profile-img {
      width: 120px;
      height: 120px;
      border-radius: 50%;
      margin: auto;
      display: block;
    }
    .password-fields {
      display: none; /* Ẩn phần đổi mật khẩu ban đầu */
    }
  </style>
</head>
<body>
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
        <a class="nav-link text-white fw-bold" href="index.jsp">TRANG CHỦ</a>
        <a class="nav-link text-white fw-bold" href="bus-schedule">LỊCH TRÌNH</a>
        <a class="nav-link text-white fw-bold" href="#">TRA CỨU VÉ</a>
        <a class="nav-link text-white fw-bold" href="#">TIN TỨC</a>
        <a class="nav-link text-white fw-bold" href="#">HÓA ĐƠN</a>
        <a class="nav-link text-white fw-bold" href="#">LIÊN HỆ</a>
        <a class="nav-link text-white fw-bold" href="#">VỀ CHÚNG TÔI</a>
      </nav>

      <!-- Nút đăng nhập / đăng ký -->
      <div>
        <div class="dropdown">
          <button class="btn btn-outline-light dropdown-toggle" type="button" id="profileDropdown" data-bs-toggle="dropdown" aria-expanded="false">
          </button>
          <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="profileDropdown">
            <li><a class="dropdown-item" href="userprofile.jsp"><i class="bi bi-person"></i> Thông tin cá nhân</a></li>
            <li><a class="dropdown-item" href="change-password.jsp"><i class="bi bi-key"></i> Đổi mật khẩu</a></li>
            <li><a class="dropdown-item text-danger" href="logout.jsp"><i class="bi bi-box-arrow-right"></i> Đăng xuất</a></li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</header>
<div class="container profile-container">
  <div class="card p-4">
    <div class="text-center">
      <img src="https://bootdey.com/img/Content/avatar/avatar7.png" class="profile-img" alt="User Avatar">
    </div>
  </div>

  <div class="card mt-4 p-4">
    <h5>Profile Information</h5>
    <form id="profileForm" action="profile" method="post">
      <div class="row mt-3">
        <div class="col-md-6">
          <label class="form-label">Full Name</label>
          <input type="text" class="form-control" id="fullName" name="fullName" value="${user.fullName}" readonly>
        </div>
        <div class="col-md-6">
          <label class="form-label">Email</label>
          <input type="text" class="form-control" id="email" name="email" value="${user.email}" readonly>
        </div>
      </div>
      <div class="row mt-3">
        <div class="col-md-6">
          <label class="form-label">Phone</label>
          <input type="text" class="form-control" id="phone" name="phone" value="${user.phone}" readonly>
        </div>
      </div>

      <!-- Trường đổi mật khẩu (ẩn mặc định) -->
      <div class="password-fields mt-4">
        <h5>Change Password</h5>
        <div class="row">
          <div class="col-md-12">
            <label class="form-label">Current Password</label>
            <input type="password" class="form-control" id="currentPassword">
          </div>
          <div class="col-md-6 mt-3">
            <label class="form-label">New Password</label>
            <input type="password" class="form-control" id="newPassword">
          </div>
          <div class="col-md-6 mt-3">
            <label class="form-label">Confirm New Password</label>
            <input type="password" class="form-control" id="confirmPassword">
          </div>
        </div>
      </div>

      <div class="text-center mt-3">
        <button type="button" class="btn btn-primary" id="editBtn">Edit</button>
        <button type="button" class="btn btn-success d-none" id="saveBtn">Save</button>
        <button type="button" class="btn btn-secondary d-none" id="cancelBtn">Cancel</button>
      </div>
    </form>
  </div>
</div>

<script>
  document.getElementById("editBtn").addEventListener("click", function() {
    // Bật chế độ chỉnh sửa
    let inputs = document.querySelectorAll("#profileForm input");
    inputs.forEach(input => input.removeAttribute("readonly"));

    // Hiển thị phần đổi mật khẩu
    document.querySelector(".password-fields").style.display = "block";

    // Hiển thị nút Save & Cancel, ẩn nút Edit
    document.getElementById("saveBtn").classList.remove("d-none");
    document.getElementById("cancelBtn").classList.remove("d-none");
    this.classList.add("d-none");
  });

  document.getElementById("cancelBtn").addEventListener("click", function() {
    location.reload(); // Tải lại trang để khôi phục dữ liệu cũ
  });

  document.getElementById("saveBtn").addEventListener("click", function() {
    // Lấy dữ liệu từ form
    let form = document.getElementById("profileForm");
    let currentPassword = document.getElementById("currentPassword").value.trim();
    let newPassword = document.getElementById("newPassword").value.trim();
    let confirmPassword = document.getElementById("confirmPassword").value.trim();

    // Kiểm tra nếu người dùng muốn đổi mật khẩu
    if (newPassword.length > 0 || confirmPassword.length > 0) {
      if (!/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$/.test(newPassword)) {
        alert("Mật khẩu phải có ít nhất 8 ký tự, bao gồm ít nhất 1 chữ số, 1 chữ in hoa và 1 chữ thường.");
        event.preventDefault(); // Ngăn form submit
        return;
      }
      if (newPassword !== confirmPassword) {
        alert("Mật khẩu mới và xác nhận mật khẩu không khớp.");
        event.preventDefault();
        return;
      }
      if (currentPassword.length === 0) {
        alert("Bạn cần nhập mật khẩu hiện tại để đổi mật khẩu.");
        event.preventDefault();
        return;
      }
    }

    console.log("Gửi dữ liệu lên server...");
    form.submit();


    alert("Cập nhật thông tin thành công!");

    // Ẩn nút Save & Cancel, hiện lại nút Edit
    document.getElementById("editBtn").classList.remove("d-none");
    this.classList.add("d-none");
    document.getElementById("cancelBtn").classList.add("d-none");

    // Đặt lại input về readonly
    let inputs = document.querySelectorAll("#profileForm input:not([type=password])");
    inputs.forEach(input => input.setAttribute("readonly", "true"));

    // Ẩn phần đổi mật khẩu sau khi lưu
    document.querySelector(".password-fields").style.display = "none";
  });
</script>

</body>
</html>
