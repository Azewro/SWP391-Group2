<%--
  Created by IntelliJ IDEA.
  User: LONG
  Date: 3/4/2025
  Time: 11:03 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="container mt-5">
  <h2>User Profile</h2>

  <!-- Success message -->
  <c:if test="${not empty successMessage}">
    <div class="alert alert-success">${successMessage}</div>
  </c:if>

  <!-- Update Profile Form -->
  <form action="profile" method="post">
    <div class="mb-3">
      <label class="form-label">Full Name</label>
      <input type="text" class="form-control" name="fullName" value="${user.fullName}" required>
    </div>
    <div class="mb-3">
      <label class="form-label">Email</label>
      <input type="email" class="form-control" name="email" value="${user.email}" required>
    </div>
    <div class="mb-3">
      <label class="form-label">Phone Number</label>
      <input type="text" class="form-control" name="phoneNumber" value="${user.phone}" required>
    </div>
    <button type="submit" class="btn btn-primary">Update Profile</button>
  </form>

  <hr>

  <!-- Change Password Form -->
  <h4>Change Password</h4>
  <form action="profile" method="post">
    <input type="hidden" name="action" value="changePassword">
    <div class="mb-3">
      <label class="form-label">Current Password</label>
      <input type="password" class="form-control" name="currentPassword" required>
    </div>
    <div class="mb-3">
      <label class="form-label">New Password</label>
      <input type="password" class="form-control" name="newPassword" required>
    </div>
    <div class="mb-3">
      <label class="form-label">Confirm New Password</label>
      <input type="password" class="form-control" name="confirmPassword" required>
    </div>
    <button type="submit" class="btn btn-warning">Change Password</button>
  </form>
</div>
</body>
</html>
