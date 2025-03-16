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

                <div class="card shadow">
                    <div class="card-header bg-primary text-white text-center">
                        <h2>Create Blog Category</h2>
                    </div>
                    <div class="card-body">
                        <form action="blog-category" method="post">
                            <input type="hidden" name="action" value="create">

                            <div class="mb-3">
                                <label class="form-label">Category Name:</label>
                                <input type="text" class="form-control" name="name" required>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Description:</label>
                                <textarea class="form-control" name="description" rows="3" required></textarea>
                            </div>

                            <div class="text-center">
                                <button type="submit" class="btn btn-success">Create Category</button>
                                <a href="./blog-category" class="btn btn-secondary">Cancel</a>
                            </div>
                        </form>
                    </div>
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

