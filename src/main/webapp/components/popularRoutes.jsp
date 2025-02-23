<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Hệ Thống Bán Vé Xe Buýt</title>
  <link rel="stylesheet" href="styles.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css">
<div class="popular-routes">
    <h2>Tuyến phổ biến</h2>
    <p>Được khách hàng tin tưởng và lựa chọn</p>
    <div class="route-cards">
        <c:forEach var="route" items="${popularRoutes}">
            <div class="route-card">
                <span class="route-title">Tuyến xe từ ${route.startLocation.name}</span>
                <div class="route-details">
                    <c:forEach var="trip" items="${route.trips}">
                        <div class="trip-info">
                            <span class="trip-destination">${trip.endLocation}</span>
                            <span class="trip-price">${trip.price}Ä</span>
                            <span class="trip-distance">${trip.distance}km - ${trip.duration} - ${trip.departureDate}</span>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
