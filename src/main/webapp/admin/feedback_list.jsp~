<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.CustomerFeedback" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- Bootstrap và FontAwesome --%>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

<!-- Header và Sidebar -->
<%@ include file="header.jsp" %>

<div id="layoutSidenav">
    <jsp:include page="sidebar.jsp" />
    <div id="layoutSidenav_content">
        <main class="content-wrapper">
            <div class="container mt-4">
                <h2 class="text-center">Quản lý phản hồi khách hàng</h2>

                <!-- Form tìm kiếm theo điểm đánh giá -->
                <form action="feedback" method="get" class="d-flex mb-3">
                    <input type="hidden" name="action" value="search">
                    <label class="me-2" for="rating">Tìm theo điểm đánh giá:</label>
                    <select class="form-select w-auto" name="rating" id="rating">
                        <option value="1">1 Sao</option>
                        <option value="2">2 Sao</option>
                        <option value="3">3 Sao</option>
                        <option value="4">4 Sao</option>
                        <option value="5">5 Sao</option>
                    </select>
                    <button type="submit" class="btn btn-primary ms-2">Tìm kiếm</button>
                </form>

                <!-- Bảng danh sách phản hồi -->
                <table class="table table-bordered table-striped" id="feedbackTable">
                    <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Ticket ID</th>
                        <th>Đánh giá</th>
                        <th>Bình luận</th>
                        <th>Ngày tạo</th>
                        <th>Trạng thái</th>
                        <th>Hành động</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="feedback" items="${feedbackList}">
                        <tr>
                            <td>${feedback.feedbackId}</td>
                            <td>${feedback.ticketId}</td>
                            <td>${feedback.rating} ⭐</td>
                            <td>${feedback.comment}</td>
                            <td>${feedback.createdAt}</td>
                            <td>
                                    <span class="badge
                                        ${feedback.status eq 'Approved' ? 'bg-success' : feedback.status eq 'Rejected' ? 'bg-danger' : 'bg-warning'}">
                                            ${feedback.status}
                                    </span>
                            </td>
                            <td>
                                <c:if test="${feedback.status ne 'Approved'}">
                                    <form action="feedback" method="post" style="display:inline;">
                                        <input type="hidden" name="action" value="approve">
                                        <input type="hidden" name="feedbackId" value="${feedback.feedbackId}">
                                        <button type="submit" class="btn btn-success btn-sm">Duyệt</button>
                                    </form>
                                </c:if>

                                <c:if test="${feedback.status ne 'Rejected'}">
                                    <form action="feedback" method="post" style="display:inline;">
                                        <input type="hidden" name="action" value="reject">
                                        <input type="hidden" name="feedbackId" value="${feedback.feedbackId}">
                                        <button type="submit" class="btn btn-danger btn-sm">Từ chối</button>
                                    </form>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                <!-- Phân trang -->
                <nav>
                    <ul class="pagination justify-content-center">
                        <li class="page-item disabled" id="prevPage"><a class="page-link" href="#">Trước</a></li>
                        <li class="page-item disabled" id="nextPage"><a class="page-link" href="#">Tiếp</a></li>
                    </ul>
                </nav>

                <!-- Hiển thị thông báo -->
                <c:if test="${not empty message}">
                    <p class="alert alert-success">${message}</p>
                </c:if>
                <c:if test="${not empty error}">
                    <p class="alert alert-danger">${error}</p>
                </c:if>
            </div>
        </main>
    </div>
</div>

<!-- JavaScript để phân trang -->
<script>
    document.addEventListener("DOMContentLoaded", function () {
        let rowsPerPage = 5; // Số dòng trên mỗi trang
        let table = document.getElementById("feedbackTable").getElementsByTagName("tbody")[0];
        let rows = table.getElementsByTagName("tr");
        let totalPages = Math.ceil(rows.length / rowsPerPage);
        let currentPage = 1;

        function showPage(page) {
            let start = (page - 1) * rowsPerPage;
            let end = start + rowsPerPage;

            for (let i = 0; i < rows.length; i++) {
                rows[i].style.display = i >= start && i < end ? "" : "none";
            }

            document.getElementById("prevPage").classList.toggle("disabled", page === 1);
            document.getElementById("nextPage").classList.toggle("disabled", page === totalPages);
        }

        document.getElementById("prevPage").addEventListener("click", function (e) {
            e.preventDefault();
            if (currentPage > 1) {
                currentPage--;
                showPage(currentPage);
            }
        });

        document.getElementById("nextPage").addEventListener("click", function (e) {
            e.preventDefault();
            if (currentPage < totalPages) {
                currentPage++;
                showPage(currentPage);
            }
        });

        showPage(currentPage);
    });
</script>
