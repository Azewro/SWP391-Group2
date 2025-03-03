<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.User, model.Role" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Bootstrap 5 -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

<!-- Font Awesome (icon đẹp hơn) -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

<%@ include file="header.jsp" %>


<div id="layoutSidenav">
    <jsp:include page="sidebar.jsp"/>
    <div id="layoutSidenav_content">
        <main class="content-wrapper">
<div class="container mt-4">
    <h2 class="mb-3">Quản lý Người Dùng</h2>

    <!-- Form tìm kiếm -->
    <form action="users" method="GET" class="row g-3">
        <div class="col-md-4">
            <input type="text" name="search" class="form-control" placeholder="Tìm theo username hoặc email" value="${param.search}">
        </div>
        <div class="col-md-3">
            <select name="status" class="form-select">
                <option value="">Tất cả</option>
                <option value="true" ${param.status == 'true' ? 'selected' : ''}>Hoạt động</option>
                <option value="false" ${param.status == 'false' ? 'selected' : ''}>Bị vô hiệu hóa</option>
            </select>
        </div>
        <div class="col-md-2">
            <button type="submit" class="btn btn-primary"><i class="fas fa-search"></i> Tìm kiếm</button>
        </div>
    </form>

    <!-- Bảng danh sách người dùng -->
    <div class="table-responsive mt-3">
        <table id="userTable" class="table table-hover table-striped align-middle">
            <thead class="table-dark">
            <tr>
                <th>ID</th>
                <th>Username</th>
                <th>Họ và Tên</th>
                <th>Email</th>
                <th>Điện thoại</th>
                <th>Vai trò</th>
                <th>Trạng thái</th>
                <th>Hành động</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="user" items="${users}">
                <tr>
                    <td>${user.userId}</td>
                    <td>${user.username}</td>
                    <td>${user.fullName}</td>
                    <td>${user.email}</td>
                    <td>${user.phone}</td>
                    <td>
                        <c:forEach var="role" items="${roles}">
                            <c:if test="${role.roleId == user.roleId}">
                                <span class="badge bg-info">${role.roleName}</span>
                            </c:if>
                        </c:forEach>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${user.active}">
                                <span class="badge bg-success"><i class="fas fa-check-circle"></i> Hoạt động</span>
                            </c:when>
                            <c:otherwise>
                                <span class="badge bg-danger"><i class="fas fa-times-circle"></i> Bị vô hiệu hóa</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <div class="d-flex gap-2">
                            <a href="users?action=edit&id=${user.userId}" class="btn btn-warning btn-sm">
                                <i class="fas fa-edit"></i> Sửa
                            </a>
                            <c:choose>
                                <c:when test="${user.active}">
                                    <a href="users?action=delete&id=${user.userId}" class="btn btn-danger btn-sm"
                                       onclick="return confirm('Bạn có chắc muốn vô hiệu hóa người dùng này?');">
                                        <i class="fas fa-user-slash"></i> Vô hiệu hóa
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <a href="users?action=restore&id=${user.userId}" class="btn btn-success btn-sm"
                                       onclick="return confirm('Bạn có muốn khôi phục người dùng này?');">
                                        <i class="fas fa-user-check"></i> Khôi phục
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

        </main>
    </div>
</div>

<!-- DataTables CSS & JS -->
<link rel="stylesheet" href="https://cdn.datatables.net/1.13.4/css/jquery.dataTables.min.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js"></script>

<!-- Kích hoạt DataTables -->
<script>
    $(document).ready(function () {
        $('#userTable').DataTable({
            "paging": true,         // Bật phân trang
            "pageLength": 10,       // Hiển thị 10 user mỗi trang
            "lengthChange": true,   // Cho phép chọn số dòng hiển thị
            "searching": true,      // Bật ô tìm kiếm
            "ordering": true,       // Bật sắp xếp cột
            "info": true,           // Hiển thị tổng số user
            "autoWidth": false,
            "language": {
                "lengthMenu": "Hiển thị _MENU_ người dùng mỗi trang",
                "zeroRecords": "Không tìm thấy người dùng nào",
                "info": "Hiển thị _START_ đến _END_ của _TOTAL_ người dùng",
                "infoEmpty": "Không có dữ liệu",
                "infoFiltered": "(lọc từ tổng số _MAX_ người dùng)",
                "search": "Tìm kiếm:",
                "paginate": {
                    "first": "Đầu",
                    "last": "Cuối",
                    "next": "Tiếp",
                    "previous": "Trước"
                }
            }
        });
    });
</script>


<%@ include file="footer.jsp" %>

