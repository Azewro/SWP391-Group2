<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.User" %>
<%
    User user = (User) request.getAttribute("user");
    boolean isEdit = (user != null);
%>
<!DOCTYPE html>
<html lang="vi">


<head>
    <meta charset="UTF-8">
    <title><%= isEdit ? "Chỉnh sửa người dùng" : "Thêm người dùng" %></title>
    <link rel="stylesheet" href="assets/css/admin.css">
</head>
<body>



<div class="content">
    <h2><%= isEdit ? "Chỉnh sửa người dùng" : "Thêm người dùng" %></h2>

    <form action="users" method="post">
        <input type="hidden" name="action" value="<%= isEdit ? "update" : "add" %>">
        <% if (isEdit) { %>
        <input type="hidden" name="user_id" value="<%= user.getUserId() %>">
        <% } %>

        <label for="full_name">Họ và Tên:</label>
        <input type="text" name="full_name" id="full_name" required value="<%= isEdit ? user.getFullName() : "" %>">

        <label for="email">Email:</label>
        <input type="email" name="email" id="email" required value="<%= isEdit ? user.getEmail() : "" %>">

        <label for="phone">Số điện thoại:</label>
        <input type="text" name="phone" id="phone" value="<%= isEdit ? user.getPhone() : "" %>">

        <label for="role_id">Vai trò:</label>
        <select name="role_id" id="role_id">
            <option value="1" <%= isEdit && user.getRoleId() == 1 ? "selected" : "" %>>Admin</option>
            <option value="2" <%= isEdit && user.getRoleId() == 2 ? "selected" : "" %>>Nhân viên</option>
            <option value="3" <%= isEdit && user.getRoleId() == 3 ? "selected" : "" %>>Khách hàng</option>
        </select>

        <label for="is_active">Trạng thái:</label>
        <select name="is_active" id="is_active">
            <option value="true" <%= isEdit && user.isActive() ? "selected" : "" %>>Hoạt động</option>
            <option value="false" <%= isEdit && !user.isActive() ? "selected" : "" %>>Bị khóa</option>
        </select>

        <button type="submit" class="btn btn-success">Lưu</button>
        <a href="users" class="btn btn-secondary">Quay lại</a>
    </form>
</div>

</body>
</html>
