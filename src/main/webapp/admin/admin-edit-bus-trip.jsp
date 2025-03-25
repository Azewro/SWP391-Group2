<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="header.jsp" />
<div id="layoutSidenav">
  <jsp:include page="sidebar.jsp" />
  <div id="layoutSidenav_content">
    <main class="content-wrapper">
      <div class="container mt-4">
        <h2><c:if test="${busTrip != null}">Chỉnh sửa</c:if><c:if test="${busTrip == null}">Thêm mới</c:if> chuyến xe</h2>

        <form action="bus-trips" method="post">
          <input type="hidden" name="tripId" value="${busTrip.tripId}"/>

          <!-- Tuyến -->
          <div class="mb-3">
            <label for="routeId">Tuyến đường:</label>
            <select name="routeId" id="routeId" class="form-control" required>
              <c:forEach var="r" items="${routes}">
                <option value="${r.routeId}" ${busTrip != null && busTrip.route.routeId == r.routeId ? "selected" : ""}>
                    ${r.routeId} - ${r.routeName}
                </option>
              </c:forEach>
            </select>
          </div>

          <!-- Xe -->
          <div class="mb-3">
            <label for="busId">Xe:</label>
            <select name="busId" id="busId" class="form-control" required>
              <c:forEach var="bus" items="${buses}">
                <option value="${bus.busId}" ${busTrip != null && busTrip.bus.busId == bus.busId ? "selected" : ""}>
                    ${bus.plateNumber} - ${bus.busType} (${bus.capacity} chỗ)
                </option>
              </c:forEach>
            </select>
          </div>

          <!-- Tài xế -->
          <div class="mb-3">
            <label for="driverId">Tài xế:</label>
            <select name="driverId" id="driverId" class="form-control" required>
              <c:forEach var="d" items="${drivers}">
                <option value="${d.userId}" ${busTrip != null && busTrip.driver.userId == d.userId ? "selected" : ""}>
                    ${d.fullName}
                </option>
              </c:forEach>
            </select>
          </div>

          <!-- Thời gian -->
          <div class="mb-3">
            <label for="departureTime">Thời gian khởi hành:</label>
            <input type="datetime-local" name="departureTime" id="departureTime" class="form-control" required
                   value="${busTrip.departureTime != null ? fn:substring(busTrip.departureTime, 0, 16) : ''}"/>
          </div>

          <div class="mb-3">
            <label for="arrivalTime">Thời gian đến (tuỳ chọn):</label>
            <input type="datetime-local" name="arrivalTime" id="arrivalTime" class="form-control"
                   value="${busTrip.arrivalTime != null ? fn:substring(busTrip.arrivalTime, 0, 16) : ''}"/>
          </div>

          <!-- Trạng thái -->
          <div class="mb-3">
            <label for="status">Trạng thái:</label>
            <select name="status" id="status" class="form-control">
              <c:forEach var="s" items="${['Scheduled','Ongoing','Completed','Cancelled']}">
                <option value="${s}" ${busTrip != null && busTrip.status == s ? "selected" : ""}>${s}</option>
              </c:forEach>
            </select>
          </div>

          <!-- Giá vé -->
          <div class="mb-3">
            <label for="currentPrice">Giá vé:</label>
            <input type="text" name="currentPrice" id="currentPrice" class="form-control"
                   value="${busTrip.currentPrice}" required/>
          </div>

          <!-- Ghế còn lại (hiển thị, không gửi) -->
          <c:if test="${busTrip != null}">
            <div class="mb-3">
              <label>Số ghế còn lại:</label>
              <input type="text" class="form-control" value="${busTrip.availableSeats}" disabled/>
            </div>
          </c:if>

          <p>🚍 Bus ID được gửi: <strong>${param.busId}</strong></p>

          <!-- Nút -->
          <div class="mb-3">
            <button type="submit" name="action" value="${busTrip != null ? 'update' : 'add'}"
                    class="btn btn-primary">
              ${busTrip != null ? 'Cập nhật' : 'Thêm mới'}
            </button>
            <a href="bus-trips" class="btn btn-secondary">Hủy</a>
          </div>
        </form>
      </div>
    </main>
  </div>
</div>
