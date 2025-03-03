<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="model.Route, model.Location" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



<div class="container mt-4">
  <h2 class="mb-3">
    <c:choose>
      <c:when test="${route != null}">Chỉnh sửa Tuyến Đường</c:when>
      <c:otherwise>Thêm Tuyến Đường Mới</c:otherwise>
    </c:choose>
  </h2>

  <form action="routes" method="POST">
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="routeId" value="${route.routeId}">

    <div class="mb-3">
      <label for="routeName" class="form-label">Tên Tuyến Đường</label>
      <input type="text" name="routeName" id="routeName" class="form-control"
             value="${route.routeName}" required>
    </div>

    <div class="mb-3">
      <label for="startLocation" class="form-label">Điểm Xuất Phát</label>
      <select name="startLocation" id="startLocation" class="form-select" required>
        <c:forEach var="location" items="${locations}">
          <option value="${location.locationId}" ${route.startLocation.locationId == location.locationId ? 'selected' : ''}>
              ${location.name}
          </option>
        </c:forEach>
      </select>
    </div>

    <div class="mb-3">
      <label for="endLocation" class="form-label">Điểm Kết Thúc</label>
      <select name="endLocation" id="endLocation" class="form-select" required>
        <c:forEach var="location" items="${locations}">
          <option value="${location.locationId}" ${route.endLocation.locationId == location.locationId ? 'selected' : ''}>
              ${location.name}
          </option>
        </c:forEach>
      </select>
    </div>

    <div class="mb-3">
      <label for="distance" class="form-label">Khoảng Cách (km)</label>
      <input type="number" name="distance" id="distance" class="form-control"
             value="${route.distance}" step="0.1" required>
    </div>

    <div class="mb-3">
      <label for="estimatedDuration" class="form-label">Thời Gian Dự Kiến (phút)</label>
      <input type="number" name="estimatedDuration" id="estimatedDuration" class="form-control"
             value="${route.estimatedDuration}" required>
    </div>

    <div class="mb-3">
      <label for="basePrice" class="form-label">Giá Vé Cơ Bản (VND)</label>
      <input type="number" name="basePrice" id="basePrice" class="form-control"
             value="${route.basePrice}" step="1000" required>
    </div>

    <button type="submit" class="btn btn-primary">
      <i class="fas fa-save"></i> Lưu Tuyến Đường
    </button>
    <a href="routes" class="btn btn-secondary">
      <i class="fas fa-arrow-left"></i> Quay lại
    </a>
  </form>
</div>


