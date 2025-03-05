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
    <div id="layoutSidenav_content"  style=" width: 83%;">
        <main class="content-wrapper">
            <div class="container mt-4">

                <h2 class="mb-4">Blog Categories</h2>
                <a href="blog-category?action=create" class="btn btn-success">Create new</a>
                <!-- Filter Form -->
                <form class="row mb-3" method="get" action="blog-category">
                    <input type="hidden" name="action" value="list">
                    <div class="col-md-4">
                        <input type="text" class="form-control" name="name" placeholder="Search by name" value="${param.name}">
                    </div>
                    <div class="col-md-2">
                        <button type="submit" class="btn btn-primary">Search</button>
                    </div>
                </form>

                <!-- Category Table -->
                <table class="table table-bordered table-striped">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Description</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="category" items="${categories}">
                            <tr>
                                <td>${category.id}</td>
                                <td>${category.name}</td>
                                <td>${category.description}</td>
                                <td>
                                    <a href="blog-category?action=edit&id=${category.id}" class="btn btn-warning btn-sm">Edit</a>
                                    <a href="blog-category?action=delete&id=${category.id}" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure?')">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <nav>
                    <ul class="pagination">
                        <c:if test="${currentPage > 1}">
                            <li class="page-item">
                                <a class="page-link" href="blog-category?page=${currentPage - 1}&name=${param.name}&categoryId=${param.categoryId}">Previous</a>
                            </li>
                        </c:if>

                        <c:forEach var="i" begin="1" end="${totalPages}">
                            <li class="page-item ${i == currentPage ? 'active' : ''}">
                                <a class="page-link" href="blog-category?page=${i}&name=${param.name}&categoryId=${param.categoryId}">${i}</a>
                            </li>
                        </c:forEach>

                        <c:if test="${currentPage < totalPages}">
                            <li class="page-item">
                                <a class="page-link" href="blog-category?page=${currentPage + 1}&name=${param.name}&categoryId=${param.categoryId}">Next</a>
                            </li>
                        </c:if>
                    </ul>
                </nav>



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
                                        "paging": true, // Bật phân trang
                                        "pageLength": 10, // Hiển thị 10 user mỗi trang
                                        "lengthChange": true, // Cho phép chọn số dòng hiển thị
                                        "searching": true, // Bật ô tìm kiếm
                                        "ordering": true, // Bật sắp xếp cột
                                        "info": true, // Hiển thị tổng số user
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

