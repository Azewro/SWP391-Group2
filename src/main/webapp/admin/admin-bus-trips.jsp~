<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<jsp:include page="header.jsp" />
<div id="layoutSidenav">
    <jsp:include page="sidebar.jsp" />
    <div id="layoutSidenav_content">
        <main class="content-wrapper">
            <div class="container mt-4">
                <h2>Quản lý chuyến xe</h2>

                <!-- Form tìm kiếm -->
                <form action="bus-trips" method="get" class="mb-3 d-flex">
                    <input type="text" name="search" class="form-control me-2" placeholder="Tìm kiếm tài xế, tuyến đường..." value="${param.search}">
                    <button type="submit" class="btn btn-primary">Tìm kiếm</button>
                </form>

                <a href="bus-trips?action=edit" class="btn btn-success mb-3">Thêm chuyến xe</a>

                <table border="1" class="table table-striped">
                    <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Tuyến đường</th>
                        <th>Xe</th>
                        <th>Tài xế</th>
                        <th>Thời gian khởi hành</th>
                        <th>Thời gian đến</th>
                        <th>Trạng thái</th>
                        <th>Số ghế còn</th>
                        <th>Giá vé</th>
                        <th>Hành động</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="busTrip" items="${busTrips}">
                        <tr>
                            <td>${busTrip.tripId}</td>
                            <td>${busTrip.route.routeId}</td>
                            <td>${busTrip.bus.busId}</td>
                            <td>${busTrip.driver.fullName}</td>
                            <td>${busTrip.departureTime}</td>
                            <td>${busTrip.arrivalTime}</td>
                            <td>${busTrip.status}</td>
                            <td>${busTrip.availableSeats}</td>
                            <td>${busTrip.currentPrice}</td>
                            <td>
                                <a href="bus-trips?action=edit&tripId=${busTrip.tripId}" class="btn btn-warning">Sửa</a>
                                <a href="bus-trips?action=delete&tripId=${busTrip.tripId}" class="btn btn-danger" onclick="return confirm('Xóa chuyến xe này?')">Xóa</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                <!-- Phân trang -->
                <nav>
                    <ul class="pagination">
                        <c:forEach var="i" begin="1" end="${totalPages}">
                            <li class="page-item ${i == currentPage ? 'active' : ''}">
                                <a class="page-link" href="bus-trips?page=${i}&search=${param.search}">${i}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </nav>
            </div>
        </main>
    </div>
</div>
