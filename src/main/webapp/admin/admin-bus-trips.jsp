<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="header.jsp" />
<div id="layoutSidenav">
    <jsp:include page="sidebar.jsp" />
    <div id="layoutSidenav_content">
        <main class="content-wrapper">
            <div class="container mt-4">
                <h2>Quản lý chuyến xe</h2>

                <!-- Tìm kiếm -->
                <form action="bus-trips" method="get" class="mb-3 row g-2">
                    <div class="col-md-4">
                        <input type="text" name="searchRoute" class="form-control" placeholder="ID tuyến đường"
                               value="${searchRoute}">
                    </div>
                    <div class="col-md-4">
                        <input type="text" name="searchDriver" class="form-control" placeholder="Tên tài xế"
                               value="${searchDriver}">
                    </div>
                    <div class="col-md-2">
                        <button type="submit" class="btn btn-primary">Tìm kiếm</button>
                    </div>
                </form>

                <a href="bus-trips?action=edit" class="btn btn-success mb-3">Thêm chuyến xe</a>

                <table class="table table-bordered table-striped">
                    <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Tuyến</th>
                        <th>Xe</th>
                        <th>Tài xế</th>
                        <th>Khởi hành</th>
                        <th>Đến</th>
                        <th>Trạng thái</th>
                        <th>Số ghế còn</th>
                        <th>Giá</th>
                        <th>Hành động</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="trip" items="${busTrips}">
                        <tr>
                            <td>${trip.tripId}</td>
                            <td>${trip.route.routeId}</td>
                            <td>${trip.bus.busId}</td>
                            <td>${trip.driver.fullName}</td>
                            <td>${trip.departureTime}</td>
                            <td>${trip.arrivalTime}</td>
                            <td>${trip.status}</td>
                            <td>${trip.availableSeats}</td>
                            <td>${trip.currentPrice}</td>
                            <td>
                                <a href="bus-trips?action=edit&tripId=${trip.tripId}" class="btn btn-warning btn-sm">Sửa</a>
                                <a href="bus-trips?action=delete&tripId=${trip.tripId}" class="btn btn-danger btn-sm"
                                   onclick="return confirm('Bạn chắc chắn muốn xóa chuyến xe này?')">Xóa</a>
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
                                <a class="page-link"
                                   href="bus-trips?page=${i}&searchRoute=${searchRoute}&searchDriver=${searchDriver}">${i}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </nav>
            </div>
        </main>
    </div>
</div>
