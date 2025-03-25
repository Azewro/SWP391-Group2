<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách khuyến mãi</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css">
</head>
<body>   
    <jsp:include page="/components/header.jsp" />
    <a href="home" class="btn btn-secondary mt-3">← Quay về trang chủ</a>
    <div class =" container py-5">
        <h2 class="mb-4">🎁 Danh sách khuyến mãi đang áp dụng</h2>

<%-- Phân trang: xác định vị trí bắt đầu/kết thúc --%>
<c:set var="pageSize" value="10" />
<c:set var="pageParam" value="${param.page}" />
<c:set var="currentPage" value="${empty pageParam ? 1 : pageParam}" />
<c:set var="startIndex" value="${(currentPage - 1) * pageSize}" />
<c:set var="endIndex" value="${startIndex + pageSize}" />
<c:set var="totalItems" value="${fn:length(promotions)}" />
<c:set var="totalPages" value="${(totalItems + pageSize - 1) / pageSize}" />

<table class="table table-bordered table-striped">
    <thead>
        <tr>
            <th>Mã khuyến mãi</th>
            <th>Giảm tiền</th>
            <th>Giảm %</th>
            <th>Thời gian áp dụng</th>
            <th>Trạng thái</th>
            <th>Xem chi tiết</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="promo" items="${promotions}" varStatus="status">
            <c:if test="${status.index >= startIndex && status.index < endIndex}">
                <tr>
                    <td>${promo.promoCode}</td>
                    <td>${promo.discountAmount}</td>
                    <td>${promo.discountPercentage}%</td>
                    <td>${promo.validFrom} → ${promo.validTo}</td>
                    <td>
                        <c:choose>
                            <c:when test="${promo.active}">Hiệu lực</c:when>
                            <c:otherwise>Hết hạn</c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <a href="promotion-detail?promoCode=${promo.promoCode}" class="btn btn-primary btn-sm">
                            Chi tiết
                        </a>
                    </td>
                </tr>
            </c:if>
        </c:forEach>
    </tbody>
</table>

<%-- PHÂN TRANG: chuyển trang --%>
<div class="mt-4 d-flex justify-content-center">
    <nav>
        <ul class="pagination">
            <c:forEach begin="1" end="${totalPages}" var="i">
                <li class="page-item ${i == currentPage ? 'active' : ''}">
                    <a class="page-link" href="promotions?page=${i}">${i}</a>
                </li>
            </c:forEach>
        </ul>
    </nav>
</div>


    </div>

</body>
</html>
