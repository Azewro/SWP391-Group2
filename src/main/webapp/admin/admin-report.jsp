<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.User, model.Role" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Bootstrap 5 -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

<!-- Font Awesome (icon đẹp hơn) -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

<%@ include file="header.jsp" %>


<div id="layoutSidenav">
    <jsp:include page="sidebar.jsp"/>
    <div id="layoutSidenav_content">
        <main class="content-wrapper">

            <div class="row mb-4 text-center" style="max-width: 700px; margin: auto;">
                <div class="col-md-4">
                    <div class="card border-success">
                        <div class="card-body py-2 text-center">
                            <h5 class="card-title">Tổng doanh thu</h5>
                            <h3 class="text-success">${totalRevenue} đ</h3>
                        </div>
                    </div>
                </div>
                <div class="col-md-4" style="max-width: 700px; margin: auto;">
                    <div class="card border-primary">
                        <div class="card-body py-2 text-center">
                            <h5 class="card-title">Vé đã bán</h5>
                            <h3 class="text-primary">${totalTickets}</h3>
                        </div>
                    </div>
                </div>
                <div class="col-md-4" style="max-width: 700px; margin: auto;">
                    <div class="card border-warning">
                        <div class="card-body py-2 text-center">
                            <h5 class="card-title">Đơn hàng</h5>
                            <h3 class="text-warning">${totalOrders}</h3>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 2. Biểu đồ vé bán theo ngày -->
            <div class="card mb-4" style="max-width: 700px; margin: auto;">
                <div class="card-header">Số vé bán theo ngày</div>
                <div class="card-body py-2 text-center">
                    <canvas id="ticketsLineChart" height="180" style="max-width: 400px; margin: auto;"></canvas>
                </div>
            </div>

            <!-- 3. Biểu đồ phân phối số sao -->
            <div class="card mb-4" style="max-width: 700px; margin: auto;">
                <div class="card-header">Phân phối đánh giá (1-5 sao)</div>
                <div class="card-body py-2 text-center">
                    <canvas id="ratingBarChart" height="180" style="max-width: 400px; margin: auto;"></canvas>
                </div>
            </div>

            <!-- 4. Biểu đồ doanh thu theo tuyến -->
            <div class="card mb-4" style="max-width: 700px; margin: auto;">
                <div class="card-header">Doanh thu theo tuyến</div>
                <div class="card-body py-2 text-center">
                    <canvas id="routePieChart" height="180" style="max-width: 400px; margin: auto;"></canvas>
                </div>
            </div>

            <!-- 5. Top khách hàng -->
            <div class="card mb-4" style="max-width: 700px; margin: auto;">
                <div class="card-header">Top khách hàng chi tiêu nhiều nhất</div>
                <div class="card-body py-2 text-center">
                    <canvas id="topCustomerChart" height="180" style="max-width: 400px; margin: auto;"></canvas>
                </div>
            </div>

            <!-- 6. Phản hồi chờ duyệt -->
            <div class="card mb-4" style="max-width: 700px; margin: auto;">
                <div class="card-header">Phản hồi chờ duyệt</div>
                <div class="card-body py-2 text-center">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>Mã vé</th>
                            <th>Đánh giá</th>
                            <th>Bình luận</th>
                            <th>Ngày gửi</th>
                            <th>Trạng thái</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="fb" items="${topFeedbacks}">
                            <tr>
                                <td>${fb.ticketId}</td>
                                <td>${fb.rating} ⭐</td>
                                <td>${fb.comment}</td>
                                <td>${fb.createdAt}</td>
                                <td>${fb.status}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>


        </main>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script>
        // 1. Vé bán theo ngày
        const ticketLabels = [<c:forEach var="e" items="${ticketsPerDate}">"${e.key}",</c:forEach>];
        const ticketData = [<c:forEach var="e" items="${ticketsPerDate}">${e.value},</c:forEach>];
        new Chart(document.getElementById("ticketsLineChart"), {
            type: "line",
            data: {
                labels: ticketLabels,
                datasets: [{ label: "Vé đã bán", data: ticketData, tension: 0.4 }]
            }
        });

        // 2. Phân phối đánh giá
        const ratingLabels = ["1⭐", "2⭐", "3⭐", "4⭐", "5⭐"];
        const ratingData = [<c:forEach var="r" items="${ratingDist}">${r},</c:forEach>];
        new Chart(document.getElementById("ratingBarChart"), {
            type: "bar",
            data: {
                labels: ratingLabels,
                datasets: [{ label: "Số lượt", data: ratingData }]
            }
        });

        // 3. Doanh thu tuyến
        const routeLabels = [<c:forEach var="e" items="${revenueByRoute}">"${e.key}",</c:forEach>];
        const routeData = [<c:forEach var="e" items="${revenueByRoute}">${e.value},</c:forEach>];
        new Chart(document.getElementById("routePieChart"), {
            type: "pie",
            data: {
                labels: routeLabels,
                datasets: [{ label: "Doanh thu", data: routeData }]
            },
            options: {
                plugins: {
                    legend: {
                        position: 'right'
                    }
                }
            }
        });

        // 4. Top khách hàng
        const topUserLabels = [<c:forEach var="e" items="${topCustomers}">"${e.key}",</c:forEach>];
        const topUserData = [<c:forEach var="e" items="${topCustomers}">${e.value},</c:forEach>];
        new Chart(document.getElementById("topCustomerChart"), {
            type: "bar",
            data: {
                labels: topUserLabels,
                datasets: [{ label: "Chi tiêu (đ)", data: topUserData }]
            },
            options: {
                indexAxis: 'y'
            }
        });
    </script>


</div>