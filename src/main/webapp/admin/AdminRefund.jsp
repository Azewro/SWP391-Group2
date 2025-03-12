<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Payment" %>
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
        <h2 class="mb-4">Quản lý hoàn tiền</h2>
        <a href="admin/orders" class="btn btn-secondary mb-3"><i class="fa fa-arrow-left"></i> Quay lại</a>

        <!-- Bảng danh sách hoàn tiền -->
        <div class="table-responsive">
          <table class="table table-striped table-hover" id="refundTable">
            <thead class="table-dark">
            <tr>
              <th>ID Thanh toán</th>
              <th>ID Đơn hàng</th>
              <th>Số tiền</th>
              <th>Phương thức</th>
              <th>Thời gian</th>
              <th>Trạng thái hoàn tiền</th>
              <th>Hành động</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="payment" items="${pendingRefunds}">
              <tr>
                <td>${payment.paymentId}</td>
                <td>${payment.order.orderId}</td>
                <td>${payment.amount} VNĐ</td>
                <td>${payment.paymentMethod}</td>
                <td>${payment.paymentTime}</td>
                <td>
                                        <span class="badge
                                            ${payment.refundStatus == 'Pending' ? 'bg-warning' :
                                              payment.refundStatus == 'Refunded' ? 'bg-success' :
                                              'bg-danger'}">
                                            ${payment.refundStatus}
                                        </span>
                </td>
                <td>
                  <button class="btn btn-success btn-sm" onclick="updateRefundStatus(${payment.paymentId}, 'Refunded')">
                    <i class="fa fa-check"></i> Xác nhận hoàn tiền
                  </button>
                  <button class="btn btn-danger btn-sm" onclick="updateRefundStatus(${payment.paymentId}, 'Rejected')">
                    <i class="fa fa-times"></i> Từ chối hoàn tiền
                  </button>
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
    let table = document.getElementById("refundTable");
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
    let table = document.getElementById("refundTable");
    let rows = table.getElementsByTagName("tbody")[0].getElementsByTagName("tr");
    let totalPages = Math.ceil(rows.length / rowsPerPage);

    if (currentPage < totalPages) {
      currentPage++;
      paginateTable();
    }
  }

  function updateRefundStatus(paymentId, newStatus) {
    if (confirm("Bạn có chắc muốn thay đổi trạng thái hoàn tiền này?")) {
      window.location.href = `admin/refunds?paymentId=${paymentId}&refundStatus=${newStatus}`;
    }
  }

  window.onload = paginateTable;
</script>
