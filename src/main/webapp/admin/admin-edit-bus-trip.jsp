<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="header.jsp" />
<div id="layoutSidenav">
  <jsp:include page="sidebar.jsp" />
  <div id="layoutSidenav_content">
    <main class="content-wrapper">
      <div class="container mt-4">
        <h2><c:if test="${busTrip != null}">Chỉnh sửa chuyến xe</c:if><c:if test="${busTrip == null}">Thêm chuyến xe</c:if></h2>

        <form action="bus-trips" method="post" class="needs-validation" novalidate>
          <input type="hidden" name="tripId" value="${busTrip.tripId}">

          <!-- Chọn tuyến đường (Dropdown) -->
          <div class="mb-3">
            <label for="routeId" class="form-label">Tuyến đường:</label>
            <select name="routeId" id="routeId" class="form-control" required>
              <c:forEach var="route" items="${routes}">
                <option value="${route.routeId}"
                  ${busTrip != null && busTrip.route.routeId == route.routeId ? "selected" : ""}>
                    ${route.routeName}
                </option>
              </c:forEach>
            </select>
          </div>

          <!-- Chọn xe (Dropdown) -->
          <div class="mb-3">
            <label for="busId" class="form-label">Xe:</label>
            <select name="busId" id="busId" class="form-control" required>
              <c:forEach var="bus" items="${buses}">
                <option value="${bus.busId}"
                  ${busTrip != null && busTrip.bus.busId == bus.busId ? "selected" : ""}>
                    ${bus.plateNumber} - ${bus.busType} (${bus.capacity} chỗ)
                </option>
              </c:forEach>
            </select>
          </div>

          <!-- Chọn tài xế (Dropdown) -->
          <div class="mb-3">
            <label for="driverId" class="form-label">Tài xế:</label>
            <select name="driverId" id="driverId" class="form-control" required>
              <c:forEach var="driver" items="${drivers}">
                <option value="${driver.userId}"
                  ${busTrip != null && busTrip.driver.userId == driver.userId ? "selected" : ""}>
                    ${driver.fullName}
                </option>
              </c:forEach>
            </select>
          </div>

          <!-- Thời gian khởi hành -->
          <div class="mb-3">
            <label for="departureTime" class="form-label">Thời gian khởi hành:</label>
            <input type="datetime-local" name="departureTime" id="departureTime" class="form-control" required
                   value="${busTrip.departureTime}">
          </div>

          <!-- Thời gian đến -->
          <div class="mb-3">
            <label for="arrivalTime" class="form-label">Thời gian đến (tùy chọn):</label>
            <input type="datetime-local" name="arrivalTime" id="arrivalTime" class="form-control"
                   value="${busTrip.arrivalTime}">
          </div>

          <!-- Trạng thái chuyến xe -->
          <div class="mb-3">
            <label for="status" class="form-label">Trạng thái:</label>
            <select name="status" id="status" class="form-control">
              <option value="Scheduled" ${busTrip.status == 'Scheduled' ? 'selected' : ''}>Đã lên lịch</option>
              <option value="Ongoing" ${busTrip.status == 'Ongoing' ? 'selected' : ''}>Đang chạy</option>
              <option value="Completed" ${busTrip.status == 'Completed' ? 'selected' : ''}>Hoàn thành</option>
              <option value="Cancelled" ${busTrip.status == 'Cancelled' ? 'selected' : ''}>Hủy</option>
            </select>
          </div>

          <!-- Số ghế còn lại (chỉ hiển thị, không cho nhập) -->
          <div class="mb-3">
            <label for="availableSeats" class="form-label">Số ghế còn lại:</label>
            <input type="text" id="availableSeats" class="form-control" value="${busTrip.availableSeats}" disabled>
          </div>


          <!-- Giá vé -->
          <div class="mb-3">
            <label for="currentPrice" class="form-label">Giá vé:</label>
            <input type="text" name="currentPrice" id="currentPrice" class="form-control" required value="${busTrip.currentPrice}">
          </div>

          <!-- Nút hành động -->
          <div class="mb-3">
            <button type="submit" class="btn btn-primary" name="action" value="<c:if test='${busTrip != null}'>update</c:if><c:if test='${busTrip == null}'>add</c:if>">
              <c:if test="${busTrip != null}">Cập nhật</c:if><c:if test="${busTrip == null}">Thêm mới</c:if>
            </button>
            <a href="bus-trips" class="btn btn-secondary">Quay lại</a>
          </div>
        </form>
      </div>
    </main>
  </div>
</div>
