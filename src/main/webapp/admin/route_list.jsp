<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Route, model.Location" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



<div class="container mt-4">
  <!-- Card Tìm kiếm -->
  <div class="card shadow-lg">
    <div class="card-header bg-primary text-white">
      <h3 class="mb-0"><i class="fas fa-route"></i> Quản lý Tuyến Đường</h3>
    </div>
    <div class="card-body">
      <form action="routes" method="GET" class="row g-3 align-items-end">
        <div class="col-md-4">
          <label class="form-label"><i class="fas fa-search"></i> Tìm theo tên tuyến</label>
          <input type="text" name="search" class="form-control" placeholder="Nhập tên tuyến..." value="${param.search}">
        </div>
        <div class="col-md-3">
          <label class="form-label"><i class="fas fa-map-marker-alt"></i> Lọc theo khu vực</label>
          <select name="location" class="form-select">
            <option value="">📍 Tất cả khu vực</option>
            <c:forEach var="location" items="${locations}">
              <option value="${location.locationId}" ${param.location == location.locationId ? 'selected' : ''}>
                  ${location.name}
              </option>
            </c:forEach>
          </select>
        </div>
        <div class="col-md-2">
          <button type="submit" class="btn btn-success w-100"><i class="fas fa-search"></i> Tìm kiếm</button>
        </div>
        <div class="col-md-3 text-end">
          <a href="routes?action=add" class="btn btn-success"><i class="fas fa-plus"></i> Thêm Tuyến Đường</a>
        </div>
      </form>
    </div>
  </div>

  <!-- Bảng danh sách tuyến đường -->
  <div class="card shadow-lg mt-4">
    <div class="card-body">
      <div class="table-responsive">
        <table id="routeTable" class="table table-hover table-bordered align-middle">
          <thead class="table-dark">
          <tr>
            <th>ID</th>
            <th>Tên tuyến</th>
            <th>Điểm xuất phát</th>
            <th>Điểm kết thúc</th>
            <th>Khoảng cách (km)</th>
            <th>Thời gian dự kiến</th>
            <th>Giá vé cơ bản</th>
            <th>Hành động</th>
          </tr>
          </thead>
          <tbody>
          <c:choose>
            <c:when test="${not empty routes}">
              <c:forEach var="route" items="${routes}">
                <tr>
                  <td>${route.routeId}</td>
                  <td>${route.routeName}</td>
                  <td>${route.startLocation.name}</td>
                  <td>${route.endLocation.name}</td>
                  <td>${route.distance}</td>
                  <td>${route.estimatedDuration} phút</td>
                  <td>${route.basePrice} VND</td>
                  <td>
                    <div class="d-flex gap-2">
                      <a href="routes?action=edit&id=${route.routeId}" class="btn btn-warning btn-sm">
                        <i class="fas fa-edit"></i> Sửa
                      </a>
                      <a href="routes?action=delete&id=${route.routeId}" class="btn btn-danger btn-sm"
                         onclick="return confirm('Bạn có chắc muốn xóa tuyến đường này?');">
                        <i class="fas fa-trash"></i> Xóa
                      </a>
                      <a href="route-stops?routeId=${route.routeId}" class="btn btn-info btn-sm">
                        <i class="fas fa-map-marker-alt"></i> Điểm Dừng
                      </a>
                    </div>
                  </td>
                </tr>
              </c:forEach>
            </c:when>
            <c:otherwise>
              <tr>
                <td colspan="8" class="text-center text-muted">
                  <i class="fas fa-exclamation-circle text-danger"></i> Không có tuyến đường nào!
                </td>
              </tr>
            </c:otherwise>
          </c:choose>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</div>

