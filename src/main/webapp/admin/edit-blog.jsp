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

                
                 <div class="container mt-5">
            <div class="row justify-content-center">
                <div class="col-md-8">
                    <div class="card shadow">
                        <div class="card-header bg-warning text-white text-center">
                            <h2>Edit Blog</h2>
                        </div>
                        <div class="card-body">
                            <form action="blog" method="post">
                                <input type="hidden" name="action" value="update">
                                <input type="hidden" name="id" value="${blog.id}">

                                <div class="mb-3">
                                    <label class="form-label">Title:</label>
                                    <input type="text" class="form-control" name="title" value="${blog.title}" required>
                                </div>

                                <div class="mb-3">
                                    <label class="form-label">Content:</label>
                                    <textarea class="form-control" name="content" id="content" required>${blog.content}</textarea>
                                </div>
                                <script>
                                    CKEDITOR.replace('content');
                                </script>

                                <div class="mb-3">
                                    <label class="form-label">Summary:</label>
                                    <input type="text" class="form-control" name="summary" value="${blog.summary}" required>
                                </div>

                                <div class="mb-3">
                                    <label class="form-label">Upload Image:</label>
                                    <input type="file" class="form-control" id="imgUpload" accept="image/*" onchange="convertToBase64()">
                                    <input type="hidden" name="img" id="imgBase64" value="${blog.img}">
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Category:</label>
                                    <select class="form-select" name="cateId" required>
                                        <c:forEach var="u" items="${cls}">
                                            <option value="${u.id}" ${blog.category.id == u.id?"selected":""}>${u.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="mb-3 text-center">
                                    <label class="form-label">Preview:</label>
                                    <br>
                                    <img id="imgPreview" src="${blog.img}" class="img-fluid border" style="max-width: 300px; height: auto;">
                                </div>



                                <div class="text-center">
                                    <button type="submit" class="btn btn-primary">Update</button>
                                    <a href="blog" class="btn btn-secondary">Cancel</a>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script>
            function convertToBase64() {
                let fileInput = document.getElementById("imgUpload");
                let imgBase64Input = document.getElementById("imgBase64");
                let imgPreview = document.getElementById("imgPreview");

                if (fileInput.files.length > 0) {
                    let file = fileInput.files[0];
                    let reader = new FileReader();

                    reader.onload = function (e) {
                        imgBase64Input.value = e.target.result; // Store Base64 in hidden input
                        imgPreview.src = e.target.result; // Show preview
                    };

                    reader.readAsDataURL(file); // Convert to Base64
                }
            }
        </script>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
				
                
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

