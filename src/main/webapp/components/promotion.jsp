<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<h2 style="text-align:center; color:#006666;">KHUYẾN MÃI NỔI BẬT</h2>

<%-- Danh sách ảnh khuyến mãi thật từ FUTA --%>
<c:set var="imageLinks" value="
    https://cdn.futabus.vn/futa-busline-web-cms-prod/343_x_184_px_x4_4fd05509ef/343_x_184_px_x4_4fd05509ef.jpg,
    https://cdn.futabus.vn/futa-busline-web-cms-prod/2_343_x_184_px_f365e0f9c8/2_343_x_184_px_f365e0f9c8.png,
    https://cdn.futabus.vn/futa-busline-web-cms-prod/VNPAYFUTA_67_Resize_343_x_184_bd2e13cd77/VNPAYFUTA_67_Resize_343_x_184_bd2e13cd77.png
" />
<c:set var="imageArray" value="${fn:split(imageLinks, ',')}" />

<div style="display: flex; justify-content: center; gap: 20px; flex-wrap: wrap; padding: 20px;">
    <c:forEach var="promo" items="${promotions}" varStatus="status">
        <c:if test="${status.index lt 3}">
            <div style="width: 360px; border-radius: 12px; overflow: hidden; box-shadow: 0 4px 8px rgba(0,0,0,0.1);">
                <a href="promotion-detail?promoCode=${promo.promoCode}" style="display: block;">
                    <img src="${fn:trim(imageArray[status.index])}" alt="Promo ${status.index}" style="width: 100%; height: auto;">
                </a>
            <p style="color:red">PromoCode: ${promo.promoCode}</p>

            </div>
        </c:if>
    </c:forEach>
</div>
