<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, java.math.BigDecimal, model.*, dao.SeatDAO, dao.BusTripDAO, dao.BusStopDAO" %>
<%
    int tripId = Integer.parseInt(request.getParameter("tripId"));
    BusTrip trip = BusTripDAO.getTripById(tripId);
    List<Seat> seatList = SeatDAO.getSeatsForTrip(tripId);
    List<BusStop> busStops = BusStopDAO.getStopsByRouteIdOrdered(trip.getRoute().getRouteId());
    int totalSegments = busStops.size() - 1;
%>
<!DOCTYPE html>
<html>
<head>
    <title>Chọn ghế</title>
    <style>
        body {
            font-family: Arial, sans-serif;
    background-color: #f2f2f2;
    margin: 0;
    padding: 0;
    min-height: 100vh;
    justify-content: center;
    align-items: center;
        }

        form {
            background-color: #fff;
            padding: 40px 50px;
            border-radius: 12px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
            text-align: center;
            width: 650px;
        }

        h2 {
            margin-bottom: 20px;
        }

        .top-bar {
            margin-bottom: 20px;
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 10px;
            flex-wrap: wrap;
        }

        .top-bar select {
            font-size: 16px;
            padding: 6px 12px;
        }

        .seat-map {
            display: grid;
            grid-template-columns: repeat(5, 60px);
            gap: 12px;
            justify-content: center;
            margin-bottom: 20px;
        }

        .seat {
            display: inline-block;
            width: 60px;
            height: 60px;
            border-radius: 8px;
            font-weight: bold;
            font-size: 15px;
            line-height: 60px;
            text-align: center;
            border: 1px solid #ccc;
            background-color: #4CAF50;
            color: white;
            cursor: pointer;
            position: relative;
            transition: background 0.3s ease;
        }

        .seat input[type="checkbox"] {
            display: none;
        }

        .seat.unavailable {
            background-color: #f44336;
            cursor: not-allowed;
        }

        .seat input[type="checkbox"]:checked + span {
            display: inline-block;
            width: 100%;
            height: 100%;
            background-color: #ffcc00;
            color: black;
            border-radius: 8px;
            line-height: 60px;
            transition: 0.3s;
        }

        .legend {
            font-size: 14px;
            margin-bottom: 15px;
        }

        .legend span {
            display: inline-block;
            width: 18px;
            height: 18px;
            margin-right: 6px;
            border-radius: 4px;
            vertical-align: middle;
        }

        .legend-label {
            margin: 0 15px;
        }

        #error-msg {
            color: red;
            font-size: 14px;
            margin-top: 5px;
            display: none;
        }

        .price-info {
            font-weight: bold;
            margin-bottom: 15px;
        }

        button {
            padding: 12px 25px;
            font-size: 16px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
        }
    </style>
</head>
<body>
<jsp:include page="/components/header.jsp"/>

<form action="booking" method="post">
    <input type="hidden" name="tripId" value="<%= tripId %>" />

    <h2>🚌 Chọn ghế cho chuyến #<%= tripId %></h2>

    <!-- Số vé -->
    <div class="top-bar">
        <label for="seatCount">Số vé:</label>
        <select id="seatCount" name="seatCount">
            <% for (int i = 1; i <= 6; i++) { %>
                <option value="<%= i %>"><%= i %></option>
            <% } %>
        </select>
    </div>

    <!-- Điểm đón/trả -->
    <div class="top-bar">
        <label>Điểm đón:</label>
        <select name="pickupStopId" id="pickupStop" required>
            <% for (BusStop stop : busStops) { %>
                <option value="<%= stop.getStopId() %>" data-order="<%= stop.getStopOrder() %>">
                    <%= stop.getStopName() %>
                </option>
            <% } %>
        </select>

        <label>Điểm trả:</label>
        <select name="dropoffStopId" id="dropoffStop" required>
            <% for (BusStop stop : busStops) { %>
                <option value="<%= stop.getStopId() %>" data-order="<%= stop.getStopOrder() %>">
                    <%= stop.getStopName() %>
                </option>
            <% } %>
        </select>
    </div>

    <!-- Sơ đồ ghế -->
    <div class="seat-map">
        <% for (Seat seat : seatList) {
            boolean available = seat.isAvailable();
        %>
        <label class="seat <%= available ? "" : "unavailable" %>">
            <% if (available) { %>
                <input type="checkbox" name="seatIds" value="<%= seat.getSeatId() %>" />
                <span><%= seat.getSeatNumber() %></span>
            <% } else { %>
                <span><%= seat.getSeatNumber() %></span>
            <% } %>
        </label>
        <% } %>
    </div>

    <!-- Chú thích -->
    <div class="legend">
        <span class="legend-label"><span style="background:#4CAF50;"></span> Còn trống</span>
        <span class="legend-label"><span style="background:#ffcc00;"></span> Đang chọn</span>
        <span class="legend-label"><span style="background:#f44336;"></span> Đã đặt</span>
    </div>

    <!-- Giá vé dự kiến -->
    <div class="price-info">
        Giá vé dự kiến: <span id="calculated-price"><%= trip.getCurrentPrice() %></span> đ
    </div>

    <div id="error-msg">⚠️ Số ghế vượt quá số vé hoặc điểm đón/trả không hợp lệ!</div>

    <button type="submit">Xác nhận đặt vé</button>
</form>

<script>
    const seatCountSelect = document.getElementById("seatCount");
    const checkboxes = document.querySelectorAll('input[name="seatIds"]');
    const errorMsg = document.getElementById("error-msg");
    const pickup = document.getElementById("pickupStop");
    const dropoff = document.getElementById("dropoffStop");
    const priceDisplay = document.getElementById("calculated-price");

    const basePrice = <%= trip.getCurrentPrice() %>;
    const totalSegments = <%= totalSegments %>;

    function updatePrice() {
        const pickupOrder = parseInt(pickup.options[pickup.selectedIndex].dataset.order);
        const dropoffOrder = parseInt(dropoff.options[dropoff.selectedIndex].dataset.order);
        const seatCount = parseInt(seatCountSelect.value);

        if (dropoffOrder <= pickupOrder) {
            errorMsg.style.display = "block";
            priceDisplay.textContent = "0";
            return false;
        }

        const segments = dropoffOrder - pickupOrder;
        const pricePerTicket = Math.round((basePrice * segments) / totalSegments);
        const totalPrice = pricePerTicket * seatCount;

        priceDisplay.textContent = totalPrice;
        errorMsg.style.display = "none";
        return true;
    }

    pickup.addEventListener("change", updatePrice);
    dropoff.addEventListener("change", updatePrice);
    seatCountSelect.addEventListener("change", updatePrice);
    window.addEventListener("load", updatePrice);

    checkboxes.forEach(cb => {
        cb.addEventListener("change", function () {
            const max = parseInt(seatCountSelect.value);
            const checked = document.querySelectorAll('input[name="seatIds"]:checked');

            if (cb.checked && checked.length > max) {
                cb.checked = false;
                errorMsg.style.display = "block";
                setTimeout(() => errorMsg.style.display = "none", 2000);
            }
        });
    });
</script>
</body>
</html>
