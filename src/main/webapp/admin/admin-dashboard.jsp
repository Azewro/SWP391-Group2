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
            <div class="container mt-4">
                <!-- Tiêu đề -->
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h2 class="fw-bold"><i class="fas fa-chart-line"></i> Dashboard</h2>
                    <span class="text-muted">Chào mừng, Admin 👋</span>
                </div>

                <!-- Tổng quan -->
                <h5 class="text-muted mb-3">📊 Tổng quan</h5>
                <div class="row g-4">
                    <c:forEach var="stat" items="${dashboardStats}">
                        <div class="col-md-3">
                            <div class="card text-bg-${stat.color} shadow-sm text-center rounded-3">
                                <div class="card-body">
                                    <h6 class="card-title"><i class="${stat.icon}"></i> ${stat.label}</h6>
                                    <p class="card-text fs-4 fw-bold">${stat.value}</p>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>

                <!-- Biểu đồ -->
                <h5 class="text-muted mt-5 mb-3">📈 Doanh thu & Vé bán trong 7 ngày</h5>
                <div class="row">
                    <div class="col-md-6 mb-4">
                        <div class="card shadow-sm rounded-3">
                            <div class="card-body">
                                <h6 class="card-title"><i class="fas fa-coins"></i> Doanh thu 7 ngày</h6>
                                <canvas id="revenueChart" height="200"></canvas>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6 mb-4">
                        <div class="card shadow-sm rounded-3">
                            <div class="card-body">
                                <h6 class="card-title"><i class="fas fa-ticket-alt"></i> Vé bán 7 ngày</h6>
                                <canvas id="ticketChart" height="200"></canvas>
                            </div>
                        </div>
                    </div>
                </div>


                <h5 class="text-muted mt-5 mb-3">🧭 Phân bố đánh giá theo số sao</h5>
                <div class="card shadow-sm rounded-3 mb-4">
                    <div class="card-body">
                        <h6 class="card-title"><i class="fas fa-star"></i> Thống kê feedback theo sao</h6>
                        <canvas id="feedbackPieChart" height="250" style="max-width: 400px; margin: auto;"></canvas>
                    </div>
                </div>
                <!-- Phản hồi chờ duyệt -->
                <h5 class="text-muted mt-5 mb-3">💬 Phản hồi chờ duyệt</h5>
                <div class="card shadow-sm rounded-3">
                    <div class="card-body">
                        <c:choose>
                            <c:when test="${not empty pendingFeedbacks}">
                                <c:forEach var="fb" items="${pendingFeedbacks}" varStatus="loop" begin="0" end="2">
                                    <div class="mb-3 border-bottom pb-2">
                                        <strong>Người dùng:</strong> ${fb.userName}<br>
                                        <strong>Đánh giá:</strong> ${fb.rating}★<br>
                                        <strong>Bình luận:</strong> ${fb.comment}
                                    </div>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <p>Không có phản hồi chờ duyệt.</p>
                            </c:otherwise>
                        </c:choose>

                        <div class="text-end">
                            <a href="http://localhost:8080/SWP391_Group2_war_exploded/admin/feedback?action=pending" class="btn btn-outline-primary btn-sm">
                                Xem tất cả <i class="fas fa-arrow-right"></i>
                            </a>
                        </div>
                    </div>
                </div>
            </div>


            <!-- Chart.js CDN -->
            <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

            <!-- Biểu đồ Dashboard -->
            <script>
                const revenueCtx = document.getElementById('revenueChart').getContext('2d');
                const ticketCtx = document.getElementById('ticketChart').getContext('2d');

                const labels = ${chartLabels}; // ví dụ: ['"T2"', '"T3"', '"T4"', '"T5"', '"T6"', '"T7"', '"CN"']
                const revenueData = ${revenueData}; // ví dụ: [1200000, 1500000, 1100000, 1800000, 1400000, 2000000, 1700000]
                const ticketData = ${ticketData};   // ví dụ: [20, 25, 18, 30, 22, 35, 28]

                new Chart(revenueCtx, {
                    type: 'line',
                    data: {
                        labels: labels,
                        datasets: [{
                            label: 'Doanh thu (₫)',
                            data: revenueData,
                            borderColor: 'rgba(75, 192, 192, 1)',
                            backgroundColor: 'rgba(75, 192, 192, 0.1)',
                            tension: 0.4,
                            fill: true
                        }]
                    }
                });

                new Chart(ticketCtx, {
                    type: 'bar',
                    data: {
                        labels: labels,
                        datasets: [{
                            label: 'Số vé bán',
                            data: ticketData,
                            backgroundColor: 'rgba(54, 162, 235, 0.6)',
                        }]
                    }
                });

                const pieCtx = document.getElementById('feedbackPieChart').getContext('2d');
                const pieData = ${pieFeedbackData}; // ví dụ: [2, 5, 10, 15, 8] cho 1⭐ → 5⭐

                new Chart(pieCtx, {
                    type: 'pie',
                    data: {
                        labels: ['1 ⭐', '2 ⭐', '3 ⭐', '4 ⭐', '5 ⭐'],
                        datasets: [{
                            label: 'Số lượng',
                            data: pieData,
                            backgroundColor: [
                                '#dc3545', '#fd7e14', '#ffc107', '#0d6efd', '#198754'
                            ]
                        }]
                    }
                });

            </script>

        </main>
    </div>
</div>