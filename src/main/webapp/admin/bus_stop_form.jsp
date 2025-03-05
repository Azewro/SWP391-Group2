<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="model.BusStop, model.Route" %>
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
        <h1 class="mt-4">Quản lý Điểm Dừng</h1>
        <ol class="breadcrumb mb-4">
          <li class="breadcrumb-item"><a href="dashboard.jsp">Dashboard</a></li>
          <li class="breadcrumb-item active">Thêm/Sửa Điểm Dừng</li>
        </ol>

        <div class="card shadow-lg">
          <div class="card-header bg-primary text-white">
            <h3 class="mb-0"><i class="fas fa-map-marker-alt"></i> Điểm Dừng</h3>
          </div>
          <div class="card-body">
            <form action="bus-stops" method="POST">
              <input type="hidden" name="action" value="${busStop == null ? 'add' : 'update'}">
              <input type="hidden" name="stop_id" value="${busStop.stopId}">

              <div class="mb-3">
                <label class="form-label">Tên Điểm Dừng</label>
                <input type="text" name="stop_name" class="form-control" required value="${busStop.stopName}">
              </div>

              <div class="mb-3">
                <label class="form-label">Tuyến Đường</label>
                <select name="route_id" class="form-select" required>
                  <c:forEach var="route" items="${requestScope.routes}">
                    <option value="${route.routeId}" ${busStop.route.routeId == route.routeId ? 'selected' : ''}>
                        ${route.routeName}
                    </option>
                  </c:forEach>
                </select>
              </div>

              <div class="mb-3">
                <label class="form-label">Thứ Tự</label>
                <input type="number" name="stop_order" class="form-control" required value="${busStop.stopOrder}">
              </div>

              <div class="mb-3">
                <label class="form-label">Thời Gian Chờ (phút)</label>
                <input type="number" name="estimated_waiting_time" class="form-control" value="${busStop.estimatedWaitingTime}">
              </div>

              <div class="mb-3">
                <label class="form-label">Mô Tả</label>
                <textarea name="description" class="form-control">${busStop.description}</textarea>
              </div>

              <div class="form-check mb-3">
                <input type="checkbox" name="is_active" class="form-check-input" ${busStop.active ? 'checked' : ''}>
                <label class="form-check-label">Hoạt động</label>
              </div>

              <button type="submit" class="btn btn-success"><i class="fas fa-save"></i> Lưu</button>
              <a href="bus_stop_list.jsp" class="btn btn-secondary"><i class="fas fa-arrow-left"></i> Quay lại</a>
            </form>
          </div>
        </div>
      </div>
    </main>
  </div>
</div>

<%@ include file="footer.jsp" %>
