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


                <h1 class="mb-3">Blog List</h1>
                <a class="btn btn-success" href="./blog?action=create">Create Blog</a>
                <form action="blog" method="get" class="mb-3 d-flex gap-2">
                    <input type="text" name="title" class="form-control w-35" placeholder="Search Title" value="${param.title}"/>
                    <select name="categoryId" class="form-select w-35">
                        <option value="">All</option>
                        <c:forEach var="c" items="${cls}">
                            <option value="${c.id}" ${param.categoryId == c.id ? 'selected' : ''}>${c.name}</option>
                        </c:forEach>
                    </select>
                    <button type="submit" class="btn btn-primary">Filter</button>
                </form>

                <table class="table table-striped table-bordered">
                    <thead class="table-dark">
                        <tr>
                            <th>Id</th>
                            <th>Title</th>
                            <th>Image</th>
                            <th>Category</th>
                            <th>Summary</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="blog" items="${blogs}">
                            <tr>
                                <td>${blog.id}</td>
                                <td>${blog.title}</td>
                                <td><img width="160px" src="${blog.img}" alt="alt"/></td>
                                <td>${blog.category.name}</td>
                                <td>${blog.summary}</td>
                                <td>
                                    <a href="blog?action=edit&id=${blog.id}" class="btn btn-warning btn-sm">Edit</a>
                                    <a href="blog?action=delete&id=${blog.id}" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure?')">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <nav>
                    <ul class="pagination">
                        <c:if test="${currentPage > 1}">
                            <li class="page-item">
                                <a class="page-link" href="blog?page=${currentPage - 1}&title=${param.title}&categoryId=${param.categoryId}">Previous</a>
                            </li>
                        </c:if>

                        <c:forEach var="i" begin="1" end="${totalPages}">
                            <li class="page-item ${i == currentPage ? 'active' : ''}">
                                <a class="page-link" href="blog?page=${i}&title=${param.title}&categoryId=${param.categoryId}">${i}</a>
                            </li>
                        </c:forEach>

                        <c:if test="${currentPage < totalPages}">
                            <li class="page-item">
                                <a class="page-link" href="blog?page=${currentPage + 1}&title=${param.title}&categoryId=${param.categoryId}">Next</a>
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

