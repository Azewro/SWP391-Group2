<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, model.Seat, dao.SeatDAO" %>
<%
    int tripId = Integer.parseInt(request.getParameter("tripId"));
    List<Seat> seatList = SeatDAO.getSeatsForTrip(tripId);
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
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }

        form {
            background-color: #fff;
            padding: 40px 50px;
            border-radius: 12px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
            text-align: center;
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

        /* Khi checkbox được chọn, làm vàng toàn bộ label */
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
    <form action="booking" method="post" class="flex">
    <input type="hidden" name="tripId" value="<%= tripId %>" />

    <h2>🚌 Chọn ghế cho chuyến #<%= tripId %></h2>

    <div class="top-bar">
        <label for="seatCount">Số vé:</label>
        <select id="seatCount" name="seatCount">
            <% for (int i = 1; i <= 6; i++) { %>
                <option value="<%= i %>"><%= i %></option>
            <% } %>
        </select>
    </div>

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

    <div class="legend">
        <span class="legend-label"><span style="background:#4CAF50;"></span> Còn trống</span>
        <span class="legend-label"><span style="background:#ffcc00;"></span> Đang chọn</span>
        <span class="legend-label"><span style="background:#f44336;"></span> Đã đặt</span>
    </div>

    <div id="error-msg">⚠️ Bạn chỉ được chọn tối đa số vé đã chọn!</div>

    <button type="submit">Xác nhận đặt vé</button>
</form>

<script>
    const seatCountSelect = document.getElementById("seatCount");
    const checkboxes = document.querySelectorAll('input[name="seatIds"]');
    const errorMsg = document.getElementById("error-msg");

    function enforceLimit() {
        const max = parseInt(seatCountSelect.value);
        const checkedBoxes = document.querySelectorAll('input[name="seatIds"]:checked');

        if (checkedBoxes.length > max) {
            // Bỏ chọn ô mới nhất
            this.checked = false;
            errorMsg.style.display = "block";
            setTimeout(() => errorMsg.style.display = "none", 2000);
        }
    }

    checkboxes.forEach(cb => {
        cb.addEventListener("change", enforceLimit);
    });
</script>
</body>
</html>
