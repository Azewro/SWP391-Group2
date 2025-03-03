<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="model.RouteStop, model.BusStop" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="header.jsp" />

<div class="container mt-4">
  <h2 class="mb-3">
    <c:choose>
      <c:when test="${routeStop != null}">Chỉnh sửa Điểm Dừng</c:when>
      <c:otherwise>Thêm Điểm Dừng Mới</c:otherwise>
    </c:choose>
  </h2>

  <form action="route-stops" method="POST">
    <input type="hidden" name="action" value="${routeStop != null ? 'update' : 'add'}">
    <input type="hidden" name="routeId" value="${routeId}">
    <input type="hidden" name="stopId" value="${routeStop.id}">

    <div class="mb-3">
      <label for="busStopId" class="form-label">Chọn Điểm Dừng</label>
      <select name="busStopId" id="busStopId" class="form-select" required>
        <c:forEach var="busStop" items="${busStops}">
          <option value="${busStop.stopId}" ${routeStop.stop.stopId == busStop.stopId ? 'selected' : ''}>
              ${busStop.stopName}
          </option>
        </c:forEach>
      </select>
    </div>

    <div class="mb-3">
      <label for="stopOrder" class="form-label">Thứ Tự Điểm Dừng</label>
      <input type="number" name="stopOrder" id="stopOrder" class="form-control"
             value="${routeStop.stopOrder}" required>
    </div>

    <div class="mb-3">
      <label for="distanceFromStart" class="form-label">Khoảng Cách Từ Điểm Bắt Đầu (km)</label>
      <input type="number" name="distanceFromStart" id="distanceFromStart" class="form-control"
             value="${routeStop.distanceFromStart}" step="0.1" required>
    </div>

    <div class="mb-3">
      <label for="estimatedTimeFromStart" class="form-label">Thời Gian Đến Dự Kiến (phút)</label>
      <input type="number" name="estimatedTimeFromStart" id="estimatedTimeFromStart" class="form-control"
             value="${routeStop.estimatedTimeFromStart}" required>
    </div>

    <button type="submit" class="btn btn-primary">
      <i class="fas fa-save"></i> Lưu Điểm Dừng
    </button>
    <a href="route-stops?routeId=${routeId}" class="btn btn-secondary">
      <i class="fas fa-arrow-left"></i> Quay lại
    </a>
  </form>
</div>

<jsp:include page="footer.jsp" />
