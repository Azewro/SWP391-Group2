<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.LocalDate" %>
<%
    String today = LocalDate.now().toString(); // yyyy-MM-dd
%>

<html>
<head><title>Đặt vé</title></head>
<body>
    <jsp:include page="/components/header.jsp" />
    <h2>Chọn hành trình</h2>

    <form action="trip-list" method="get">
        <label>Điểm đi:</label>
        <select name="start_location_id">
            <c:forEach var="loc" items="${locations}">
                <option value="${loc.locationId}">${loc.name}</option>
            </c:forEach>
        </select>

        <label>Điểm đến:</label>
        <select name="end_location_id">
            <c:forEach var="loc" items="${locations}">
                <option value="${loc.locationId}">${loc.name}</option>
            </c:forEach>
        </select>

        <label>Ngày đi:</label>
        <input type="date" name="departure_date" required min="<%= today %>" />

        <button type="submit">Tìm chuyến</button>
    </form>
</body>
</html>
