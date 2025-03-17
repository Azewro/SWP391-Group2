<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Promotion" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- Bootstrap và FontAwesome --%>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

<%@ include file="header.jsp" %>

<div id="layoutSidenav">
    <jsp:include page="sidebar.jsp" />
    <div id="layoutSidenav_content">
        <main class="content-wrapper">
            <div class="container mt-4">
                <h2 class="mb-4">Quản lý Khuyến mãi</h2>

                <!-- Nút quản lý Cron Job -->
                <div class="mb-3">
                    <form action="${pageContext.request.contextPath}/admin/cronjob" method="post">
                        <button type="submit" name="action" value="start" class="btn btn-success">Bật Cron Job</button>
                        <button type="submit" name="action" value="stop" class="btn btn-danger">Dừng Cron Job</button>
                        <button type="button" class="btn btn-info" onclick="checkCronJobStatus()">Kiểm tra trạng thái</button>
                    </form>
                </div>

                <!-- Bảng danh sách khuyến mãi -->
                <div class="card">
                    <div class="card-header bg-primary text-white">Danh sách khuyến mãi</div>
                    <div class="card-body">
                        <table class="table table-bordered table-hover">
                            <thead class="table-dark">
                            <tr>
                                <th>ID</th>
                                <th>Mã khuyến mãi</th>
                                <th>Giảm giá (VNĐ)</th>
                                <th>Giảm giá (%)</th>
                                <th>Ngày bắt đầu</th>
                                <th>Ngày kết thúc</th>
                                <th>Trạng thái</th>
                                <th>Hành động</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="promo" items="${promotions}">
                                <tr>
                                    <td>${promo.promotionId}</td>
                                    <td>${promo.promoCode}</td>
                                    <td>${promo.discountAmount}</td>
                                    <td>${promo.discountPercentage}%</td>
                                    <td>${promo.validFrom}</td>
                                    <td>${promo.validTo}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${promo.active}"><span class="badge bg-success">Hoạt động</span></c:when>
                                            <c:otherwise><span class="badge bg-secondary">Vô hiệu</span></c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <form action="${pageContext.request.contextPath}/admin/promotions" method="post" class="d-inline">
                                            <input type="hidden" name="promotion_id" value="${promo.promotionId}">
                                            <button type="submit" class="btn btn-warning btn-sm">Chỉnh sửa</button>
                                        </form>
                                        <button class="btn btn-danger btn-sm" onclick="deletePromotion(${promo.promotionId})">Xóa</button>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>

                <!-- 🔹 Form Thêm Khuyến Mãi -->
                <div class="card mt-4">
                    <div class="card-header bg-success text-white">Thêm khuyến mãi mới</div>
                    <div class="card-body">
                        <form action="${pageContext.request.contextPath}/admin/promotions" method="post">
                            <div class="mb-3">
                                <label class="form-label">Mã khuyến mãi</label>
                                <input type="text" name="promo_code" class="form-control" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Giảm giá (VNĐ)</label>
                                <input type="number" name="discount_amount" class="form-control" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Giảm giá (%)</label>
                                <input type="number" name="discount_percentage" class="form-control" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Ngày bắt đầu</label>
                                <input type="datetime-local" name="valid_from" class="form-control" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Ngày kết thúc</label>
                                <input type="datetime-local" name="valid_to" class="form-control" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Trạng thái</label>
                                <select name="is_active" class="form-control">
                                    <option value="true">Hoạt động</option>
                                    <option value="false">Vô hiệu</option>
                                </select>
                            </div>
                            <button type="submit" class="btn btn-primary">Thêm khuyến mãi</button>
                        </form>
                    </div>
                </div>


            </div>
        </main>
    </div>
</div>

<script>
    function checkCronJobStatus() {
        fetch('${pageContext.request.contextPath}/admin/cronjob')
            .then(response => response.json())
            .then(data => {
                alert(data.running ? "Cron Job đang chạy!" : "Cron Job đã dừng.");
            });
    }

    function deletePromotion(promotionId) {
        if (confirm("Bạn có chắc chắn muốn xóa khuyến mãi này không?")) {
            fetch('${pageContext.request.contextPath}/admin/promotions', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: new URLSearchParams({ promotion_id: promotionId, _method: "DELETE" })
            }).then(() => window.location.reload());
        }
    }
</script>

<%@ include file="footer.jsp" %>
