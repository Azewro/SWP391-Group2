<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    model.Promotion promo = (model.Promotion) request.getAttribute("promotion");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chi tiết khuyến mãi</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css">
</head>
<body class="container py-5">

    <h2 class="mb-4">🎁 Chi tiết khuyến mãi</h2>

    <c:if test="${not empty promotion}">
        <table class="table table-bordered">
            <tr>
                <th>Mã khuyến mãi</th>
                <td>${promotion.promoCode}</td>
            </tr>
            <tr>
                <th>Số tiền giảm</th>
                <td>${promotion.discountAmount}</td>
            </tr>
            <tr>
                <th>Phần trăm giảm</th>
                <td>${promotion.discountPercentage}%</td>
            </tr>
            <tr>
                <th>Thời gian áp dụng</th>
                <td>${promotion.validFrom} → ${promotion.validTo}</td>
            </tr>
            <tr>
                <th>Trạng thái</th>
                <td>
                    <c:choose>
                        <c:when test="${promotion.active}">Hiệu lực</c:when>
                        <c:otherwise>Hết hạn</c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </table>
    </c:if>

    <a href="promotions" class="btn btn-secondary mt-3">← Quay về trang danh sách khuyến mãi</a>

</body>
</html>
