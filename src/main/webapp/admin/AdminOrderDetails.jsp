<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Order, model.OrderDetail, model.Ticket" %>
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
        <h2 class="mb-4">Chi tiết đơn hàng</h2>
        <a href="orders" class="btn btn-secondary mb-3"><i class="fa fa-arrow-left"></i> Quay lại</a>

        <!-- Thông báo lỗi -->
        <c:if test="${not empty errorMessage}">
          <div class="alert alert-danger">${errorMessage}</div>
        </c:if>

        <!-- Thông tin đơn hàng -->
        <div class="card mb-4">
          <div class="card-header bg-primary text-white">
            <h5>Thông tin đơn hàng</h5>
          </div>
          <div class="card-body">
            <p><strong>ID Đơn hàng:</strong> ${order.orderId}</p>
            <p><strong>Tổng tiền:</strong>
              <c:choose>
                <c:when test="${not empty order.totalAmount}">
                  ${order.totalAmount} VNĐ
                </c:when>
                <c:otherwise>
                  Không có dữ liệu
                </c:otherwise>
              </c:choose>
            </p>

            <p><strong>Trạng thái:</strong>
              <span class="badge
                                ${order.status == 'Pending' ? 'bg-warning' :
                                  order.status == 'Completed' ? 'bg-success' :
                                  'bg-danger'}">
                ${order.status != null ? order.status : 'Không có dữ liệu'}
              </span>
            </p>
          </div>
        </div>

        <!-- Bảng danh sách vé trong đơn hàng -->
        <div class="table-responsive">
          <table class="table table-striped table-hover" id="orderDetailTable">
            <thead class="table-dark">
            <tr>
              <th>ID Vé</th>
              <th>Chuyến xe</th>
              <th>Ghế</th>
              <th>Giá</th>
              <th>Trạng thái</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="detail" items="${orderDetails}">
              <tr>
                <td>${detail.ticket.ticketId}</td>
                <td>${detail.ticket.trip != null ? detail.ticket.trip.tripId : 'Không có dữ liệu'}</td>
                <td>${detail.ticket.seat != null ? detail.ticket.seat.seatNumber : 'Không có dữ liệu'}</td>
                <td>${detail.price} VNĐ</td>
                <td>
                  <span class="badge
                      ${detail.ticket.status == 'Booked' ? 'bg-info' :
                        detail.ticket.status == 'Used' ? 'bg-success' :
                        'bg-danger'}">
                      ${detail.ticket.status != null ? detail.ticket.status : 'Không có dữ liệu'}
                  </span>
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
    let table = document.getElementById("orderDetailTable");
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
    let table = document.getElementById("orderDetailTable");
    let rows = table.getElementsByTagName("tbody")[0].getElementsByTagName("tr");
    let totalPages = Math.ceil(rows.length / rowsPerPage);

    if (currentPage < totalPages) {
      currentPage++;
      paginateTable();
    }
  }

  window.onload = paginateTable;
</script>
