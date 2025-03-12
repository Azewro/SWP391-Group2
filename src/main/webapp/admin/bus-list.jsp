<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Bus" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- Bootstrap và FontAwesome --%>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

<%@ include file="header.jsp" %>

<div id="layoutSidenav">
    <jsp:include page="sidebar.jsp"/>
    <div id="layoutSidenav_content">
        <main class="content-wrapper">
            <div class="container mt-4">
                <h2 class="mb-3">Quản lý Xe Buýt</h2>

                <%-- Thanh tìm kiếm --%>
                <form action="bus" method="GET" class="mb-3">
                    <input type="hidden" name="action" value="search">
                    <div class="input-group">
                        <input type="text" name="keyword" class="form-control" placeholder="Nhập biển số xe...">
                        <button type="submit" class="btn btn-primary"><i class="fas fa-search"></i> Tìm kiếm</button>
                    </div>
                </form>

                <%-- Nút thêm xe buýt --%>
                <a href="bus?action=edit" class="btn btn-success mb-3">
                    <i class="fas fa-plus"></i> Thêm xe buýt
                </a>

                <%-- Bảng danh sách xe buýt --%>
                <div class="table-responsive">
                    <table class="table table-bordered table-hover">
                        <thead class="table-dark text-center">
                        <tr>
                            <th>ID</th>
                            <th>Biển số</th>
                            <th>Số ghế</th>
                            <th>Loại xe</th>
                            <th>Trạng thái</th>
                            <th>Lần bảo trì cuối</th>
                            <th>Hành động</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="bus" items="${busList}">
                            <tr class="text-center">
                                <td>${bus.busId}</td>
                                <td>${bus.plateNumber}</td>
                                <td>${bus.capacity}</td>
                                <td>${bus.busType}</td>
                                <td>
                                        <span class="badge ${bus.active ? 'bg-success' : 'bg-danger'}">
                                                ${bus.active ? 'Hoạt động' : 'Ngừng hoạt động'}
                                        </span>
                                </td>
                                <td>${bus.lastMaintenance}</td>
                                <td>
                                    <a href="bus?action=edit&busId=${bus.busId}" class="btn btn-warning btn-sm">
                                        <i class="fas fa-edit"></i> Sửa
                                    </a>
                                    <a href="bus?action=delete&busId=${bus.busId}" class="btn btn-danger btn-sm" onclick="return confirm('Bạn có chắc chắn muốn xóa?');">
                                        <i class="fas fa-trash"></i> Xóa
                                    </a>
                                    <a href="bus-maintenance?action=list&busId=${bus.busId}" class="btn btn-info btn-sm">
                                        <i class="fas fa-wrench"></i> Lịch sử bảo trì
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>

                <%-- Phân trang --%>
                <nav>
                    <ul class="pagination justify-content-center">
                        <li class="page-item <c:if test='${currentPage == 1}'>disabled</c:if>'">
                            <a class="page-link" href="bus?action=list&page=${currentPage - 1}">&laquo; Trước</a>
                        </li>
                        <c:forEach var="i" begin="1" end="${totalPages}">
                            <li class="page-item <c:if test='${i == currentPage}'>active</c:if>'">
                                <a class="page-link" href="bus?action=list&page=${i}">${i}</a>
                            </li>
                        </c:forEach>
                        <li class="page-item <c:if test='${currentPage == totalPages}'>disabled</c:if>'">
                            <a class="page-link" href="bus?action=list&page=${currentPage + 1}">Sau &raquo;</a>
                        </li>
                    </ul>
                </nav>

            </div>
        </main>
    </div>
</div>

<%@ include file="footer.jsp" %>
