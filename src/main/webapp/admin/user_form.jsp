<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="model.User, model.Role" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Bootstrap 5 -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

<!-- Font Awesome (icon đẹp hơn) -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

<div class="container mt-4">
    <h2 class="mb-3">
        <c:choose>
            <c:when test="${user != null}">Chỉnh sửa người dùng</c:when>
            <c:otherwise>Thêm người dùng mới</c:otherwise>
        </c:choose>
    </h2>

    <form action="users" method="POST">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="userId" value="${user.userId}">

        <div class="mb-3">
            <label for="username" class="form-label">Username</label>
            <input type="text" name="username" id="username" class="form-control"
                   value="${user.username}" required ${user != null ? 'readonly' : ''}>
        </div>

        <div class="mb-3">
            <label for="fullName" class="form-label">Họ và Tên</label>
            <input type="text" name="fullName" id="fullName" class="form-control"
                   value="${user.fullName}" required>
        </div>

        <div class="mb-3">
            <label for="email" class="form-label">Email</label>
            <input type="email" name="email" id="email" class="form-control"
                   value="${user.email}" required>
        </div>

        <div class="mb-3">
            <label for="phone" class="form-label">Số điện thoại</label>
            <input type="text" name="phone" id="phone" class="form-control"
                   value="${user.phone}">
        </div>

        <div class="mb-3">
            <label for="roleId" class="form-label">Vai trò</label>
            <select name="roleId" id="roleId" class="form-select">
                <c:forEach var="role" items="${roles}">
                    <option value="${role.roleId}" ${user.roleId == role.roleId ? 'selected' : ''}>
                            ${role.roleName}
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="mb-3">
            <label class="form-label">Trạng thái</label>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="isActive" value="true"
                       id="active" ${user.active ? 'checked' : ''}>
                <label class="form-check-label" for="active">Hoạt động</label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="isActive" value="false"
                       id="inactive" ${!user.active ? 'checked' : ''}>
                <label class="form-check-label" for="inactive">Bị vô hiệu hóa</label>
            </div>
        </div>

        <button type="submit" class="btn btn-primary">
            <i class="fas fa-save"></i> Lưu
        </button>
        <a href="users" class="btn btn-secondary">
            <i class="fas fa-arrow-left"></i> Quay lại
        </a>
    </form>
</div>


