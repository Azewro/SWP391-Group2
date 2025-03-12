<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Ticket" %>
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
        <h2 class="mb-4">Danh sách vé đã bán</h2>
        <a href="admin/orders" class="btn btn-secondary mb-3"><i class="fa fa-arrow-left"></i> Quay lại</a>

        <!-- Bảng danh sách vé -->
        <div class="table-responsive">
          <table class="table table-striped table-hover" id="ticketTable">
            <thead class="table-dark">
            <tr>
              <th>ID Vé</th>
              <th>Khách hàng</th>
              <th>Chuyến xe</th>
              <th>Ghế</th>
              <th>Giá</th>
              <th>Trạng thái</th>
              <th>Hành động</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="ticket" items="${tickets}">
              <tr>
                <td>${ticket.ticketId}</td>
                <td>${ticket.user.fullName}</td>
                <td>${ticket.trip.tripId}</td>
                <td>${ticket.seat.seatNumber}</td>
                <td>${ticket.price} VNĐ</td>
                <td>
                                        <span class="badge
                                            ${ticket.status == 'Booked' ? 'bg-info' :
                                              ticket.status == 'Used' ? 'bg-success' :
                                              'bg-danger'}">
                                            ${ticket.status}
                                        </span>
                </td>
                <td>
                  <button class="btn btn-danger btn-sm" onclick="updateTicketStatus(${ticket.ticketId}, 'Cancelled')">
                    <i class="fa fa-times"></i> Hủy vé
                  </button>
                  <button class="btn btn-success btn-sm" onclick="updateTicketStatus(${ticket.ticketId}, 'Used')">
                    <i class="fa fa-check"></i> Đánh dấu đã sử dụng
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
    let table = document.getElementById("ticketTable");
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
    let table = document.getElementById("ticketTable");
    let rows = table.getElementsByTagName("tbody")[0].getElementsByTagName("tr");
    let totalPages = Math.ceil(rows.length / rowsPerPage);

    if (currentPage < totalPages) {
      currentPage++;
      paginateTable();
    }
  }

  function updateTicketStatus(ticketId, newStatus) {
    if (confirm("Bạn có chắc muốn thay đổi trạng thái vé này?")) {
      window.location.href = `admin/tickets?ticketId=${ticketId}&status=${newStatus}`;
    }
  }

  window.onload = paginateTable;
</script>
