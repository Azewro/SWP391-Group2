<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Khuyến mãi của bạn</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        h2 { color: #ff6200; }
        .promo-container { margin-top: 20px; }
        .promo-card {
            border: 1px solid #ddd;
            padding: 15px;
            margin-bottom: 10px;
            border-radius: 5px;
            background: #f9f9f9;
        }
        .promo-card h3 { margin: 0; color: #27ae60; }
        .promo-card p { margin: 5px 0; }
    </style>
</head>
<body>

<h2>🎉 Khuyến mãi dành cho bạn</h2>

<c:choose>
    <c:when test="${empty activePromotions}">
        <p>🚫 Hiện tại không có khuyến mãi nào khả dụng.</p>
    </c:when>
    <c:otherwise>
        <div class="promo-container">
            <c:forEach var="promo" items="${activePromotions}">
                <div class="promo-card">
                    <h3>🎟 Mã: ${promo.promoCode}</h3>
                    <p>💰 Giảm giá: 
                        <c:choose>
                            <c:when test="${promo.discountAmount > 0}">
                                ${promo.discountAmount} đ
                            </c:when>
                            <c:otherwise>
                                ${promo.discountPercentage}% 
                            </c:otherwise>
                        </c:choose>
                    </p>
                    <p>📅 Hết hạn: ${promo.validTo}</p>
                </div>
            </c:forEach>
        </div>
    </c:otherwise>
</c:choose>

<a href="index.jsp">🔙 Quay lại trang chủ</a>

</body>
</html>
