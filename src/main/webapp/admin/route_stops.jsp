<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="model.RouteStop, model.BusStop" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



<div class="container mt-4">
  <h2 class="mb-3">Quản lý Điểm Dừng - Tuyến ID: ${routeId}</h2>

  <!-- Nút thêm điểm dừng -->
  <div class="my-3">
    <a href="route-stops?action=add&routeId=${routeId}" class="btn btn-success">
      <i class="fas fa-plus"></i> Thêm Điểm Dừng
    </a>
    <a href="routes" class="btn btn-secondary">
      <i class="fas fa-arrow-left"></i> Quay lại danh sách tuyến
    </a>
  </div>

  <!-- Bảng danh sách điểm dừng -->
  <div class="table-responsive">
    <table id="routeStopsTable" class="table table-hover table-striped align-middle">
      <thead class="table-dark">
      <tr>
        <th>Thứ Tự</th>
        <th>Tên Điểm Dừng</th>
        <th>Khoảng Cách (km)</th>
        <th>Thời Gian Đến Dự Kiến (phút)</th>
        <th>Hành động</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="routeStop" items="${routeStops}">
        <tr>
          <td>${routeStop.stopOrder}</td>
          <td>${routeStop.stop.stopName}</td>
          <td>${routeStop.distanceFromStart}</td>
          <td>${routeStop.estimatedTimeFromStart}</td>
          <td>
            <div class="d-flex gap-2">
              <a href="route-stops?action=edit&id=${routeStop.id}" class="btn btn-warning btn-sm">
                <i class="fas fa-edit"></i> Sửa
              </a>
              <a href="route-stops?action=delete&id=${routeStop.id}&routeId=${routeId}" class="btn btn-danger btn-sm"
                 onclick="return confirm('Bạn có chắc muốn xóa điểm dừng này?');">
                <i class="fas fa-trash"></i> Xóa
              </a>
            </div>
          </td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </div>
</div>



<!-- DataTables CSS & JS -->
<link rel="stylesheet" href="https://cdn.datatables.net/1.13.4/css/jquery.dataTables.min.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js"></script>

<!-- Kích hoạt DataTables -->
<script>
  $(document).ready(function () {
    $('#routeStopsTable').DataTable({
      "paging": true,
      "pageLength": 10,
      "lengthChange": true,
      "searching": true,
      "ordering": true,
      "info": true,
      "autoWidth": false,
      "language": {
        "lengthMenu": "Hiển thị _MENU_ điểm dừng mỗi trang",
        "zeroRecords": "Không tìm thấy điểm dừng nào",
        "info": "Hiển thị _START_ đến _END_ của _TOTAL_ điểm dừng",
        "infoEmpty": "Không có dữ liệu",
        "infoFiltered": "(lọc từ tổng số _MAX_ điểm dừng)",
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
