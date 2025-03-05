<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="model.Route, model.Location" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Bootstrap 5 -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<!-- Font Awesome -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

<%@ include file="header.jsp" %>

<div id="layoutSidenav">
  <jsp:include page="sidebar.jsp"/>
  <div id="layoutSidenav_content">
    <main class="content-wrapper">
      <div class="container-fluid px-4">
        <h1 class="mt-4">Quản lý Tuyến Đường</h1>
        <ol class="breadcrumb mb-4">
          <li class="breadcrumb-item"><a href="dashboard.jsp">Dashboard</a></li>
          <li class="breadcrumb-item active">Thêm/Sửa Tuyến Đường</li>
        </ol>

        <div class="card shadow-lg">
          <div class="card-header bg-primary text-white">
            <h3 class="mb-0"><i class="fas fa-route"></i> Tuyến Đường</h3>
          </div>
          <div class="card-body">
            <form action="routes" method="POST">
              <input type="hidden" name="action" value="${route == null ? 'add' : 'update'}">
              <input type="hidden" name="route_id" value="${route.routeId}">

              <div class="mb-3">
                <label class="form-label">Tên Tuyến</label>
                <input type="text" name="route_name" class="form-control" required value="${route.routeName}">
              </div>

              <div class="mb-3">
                <label class="form-label">Điểm Xuất Phát</label>
                <select name="start_location_id" class="form-select" required>
                  <c:forEach var="location" items="${requestScope.locations}">
                    <option value="${location.locationId}" ${route.startLocation.locationId == location.locationId ? 'selected' : ''}>
                        ${location.name}
                    </option>
                  </c:forEach>
                </select>
              </div>

              <div class="mb-3">
                <label class="form-label">Điểm Kết Thúc</label>
                <select name="end_location_id" class="form-select" required>
                  <c:forEach var="location" items="${requestScope.locations}">
                    <option value="${location.locationId}" ${route.endLocation.locationId == location.locationId ? 'selected' : ''}>
                        ${location.name}
                    </option>
                  </c:forEach>
                </select>
              </div>

              <div class="mb-3">
                <label class="form-label">Khoảng Cách (km)</label>
                <input type="number" step="0.1" name="distance" class="form-control" required value="${route.distance}">
              </div>

              <div class="mb-3">
                <label class="form-label">Thời Gian Dự Kiến (phút)</label>
                <input type="number" name="estimated_duration" class="form-control" required value="${route.estimatedDuration}">
              </div>

              <div class="mb-3">
                <label class="form-label">Giá Vé Cơ Bản (VNĐ)</label>
                <input type="number" name="base_price" class="form-control" required value="${route.basePrice}">
              </div>

              <button type="submit" class="btn btn-success"><i class="fas fa-save"></i> Lưu</button>
              <a href="route_list.jsp" class="btn btn-secondary"><i class="fas fa-arrow-left"></i> Quay lại</a>
            </form>
          </div>
        </div>
      </div>
    </main>
  </div>
</div>

<%@ include file="footer.jsp" %>
