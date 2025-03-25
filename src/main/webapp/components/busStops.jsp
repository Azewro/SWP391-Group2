<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/components/header.jsp" />

<a href="bus-schedule" class="btn btn-secondary mt-3">← Quay về trang lịch trình</a>

<h2 style="color: orange;">Danh sách trạm dừng trên tuyến</h2>

<table border="1" cellpadding="10" cellspacing="0" style="width: 100%; text-align: center;">
    <tr style="background-color: #f2f2f2;">
        <th>STT</th>
        <th>Tên trạm</th>
        <th>Vị trí</th>
        <th>Thứ tự dừng</th>
        <th>Mô tả</th>
    </tr>
    <c:forEach var="stop" items="${stops}" varStatus="loop">
        <tr>
            <td>${loop.count}</td>
            <td>${stop.stopName}</td>
            <td>${stop.locationName}</td>
            <td>${stop.stopOrder}</td>
            <td>${stop.description}</td>
        </tr>
    </c:forEach>
</table>
