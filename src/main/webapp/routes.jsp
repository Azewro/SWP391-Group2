<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>

<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Danh sách tuyến xe</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">
    <div class="row">
        <div class="col-md-12">
            <div class="input-group mb-3">
                <input type="text" class="form-control" placeholder="Nhập điểm đi">
                <span class="input-group-text">⇆</span>
                <input type="text" class="form-control" placeholder="Nhập điểm đến">
            </div>
            <table class="table table-bordered">
                <thead class="table-light">
                <tr>
                    <th>Tuyến xe</th>
                    <th>Loại xe</th>
                    <th>Quãng đường</th>
                    <th>Thời gian hành trình</th>
                    <th>Giá vé</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="rou" items="${routes}">
                <tr>
                    <td class="text-danger">${rou.getRouteName()}</td>
                    <td>${rou.getStartLocation()}</td>
                    <td>${rou.getEndLocation()}</td>
                    <td>${rou.getDistance()}</td>
                    <td>${rou.getEstimatedDuration()}</td>
                    <td>${rou.getBasePrice}</td>
                    <td>${rou.getRouteName()}</td>
                    <td><button class="btn btn-outline-danger">Tìm tuyến xe</button></td>
                </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>


