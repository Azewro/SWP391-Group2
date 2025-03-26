<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Order" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Bootstrap 5 & FontAwesome -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

<%@ include file="header.jsp" %>

<div id="layoutSidenav">
    <jsp:include page="sidebar.jsp"/>
    <div id="layoutSidenav_content">
        <main class="content-wrapper">
            <div class="container mt-4">
                <h2 class="mb-4">Danh sách đơn hàng</h2>
                <!-- Thanh tìm kiếm theo tên khách hàng -->
                <form method="get" action="orders" class="mb-3 d-flex justify-content-end">
                    <input type="text" name="search" class="form-control me-2 w-25" placeholder="Tìm theo tên khách hàng" value="${param.search}">
                    <button type="submit" class="btn btn-outline-primary">Tìm kiếm</button>
                </form>

                <!-- Bảng hiển thị danh sách đơn hàng -->
                <div class="table-responsive">
                    <table class="table table-striped table-hover" id="orderTable">
                        <thead class="table-dark">
                        <tr>
                            <th>ID</th>
                            <th>Khách hàng</th>
                            <th>Ngày đặt</th>
                            <th>Tổng tiền</th>
                            <th>Trạng thái</th>
                            <th>Hành động</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="order" items="${orders}">
                            <tr>
                                <td>${order.orderId}</td>
                                <td>${order.user.fullName}</td>
                                <td>${order.orderDate}</td>
                                <td>${order.totalAmount} VNĐ</td>
                                <td>
                                        <span class="badge
                                            ${order.status == 'Pending' ? 'bg-warning' :
                                              order.status == 'Completed' ? 'bg-success' :
                                              'bg-danger'}">
                                                ${order.status}
                                        </span>
                                </td>
                                <td>
                                    <a href="order-details?orderId=${order.orderId}" class="btn btn-primary btn-sm">
                                        <i class="fa fa-eye"></i> Chi tiết
                                    </a>
<%--                                    <button class="btn btn-danger btn-sm" onclick="cancelOrder(${order.orderId})">--%>
<%--                                        <i class="fa fa-times"></i> Hủy--%>
<%--                                    </button>--%>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>

                <!-- Phân trang -->
                <nav>
                    <ul class="pagination justify-content-center">
                        <li class="page-item"><a class="page-link" href="#" onclick="prevPage()">Trước</a></li>
                        <li class="page-item"><span class="page-link" id="pageNumber">1</span></li>
                        <li class="page-item"><a class="page-link" href="#" onclick="nextPage()">Sau</a></li>
                    </ul>
                </nav>
            </div>
        </main>
    </div>
</div>

<!-- JavaScript -->
<script>
    let currentPage = 1;
    const rowsPerPage = 5;

    function paginateTable() {
        let table = document.getElementById("orderTable");
        let rows = table.getElementsByTagName("tbody")[0].getElementsByTagName("tr");
        let totalPages = Math.ceil(rows.length / rowsPerPage);

        for (let i = 0; i < rows.length; i++) {
            rows[i].style.display = (i >= (currentPage - 1) * rowsPerPage && i < currentPage * rowsPerPage) ? "" : "none";
        }

        document.getElementById("pageNumber").innerText = currentPage;
    }

    function prevPage() {
        if (currentPage > 1) {
            currentPage--;
            paginateTable();
        }
    }

    function nextPage() {
        let table = document.getElementById("orderTable");
        let rows = table.getElementsByTagName("tbody")[0].getElementsByTagName("tr");
        let totalPages = Math.ceil(rows.length / rowsPerPage);

        if (currentPage < totalPages) {
            currentPage++;
            paginateTable();
        }
    }

    function cancelOrder(orderId) {
        if (confirm("Bạn có chắc muốn hủy đơn hàng này?")) {
            window.location.href = `orders?orderId=${orderId}&status=Cancelled`;
        }
    }

    window.onload = paginateTable;
</script>
