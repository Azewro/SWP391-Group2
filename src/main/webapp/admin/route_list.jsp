<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Route" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Bootstrap 5 -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<!-- Font Awesome -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

<%@ include file="header.jsp" %>

<div id="layoutSidenav">
  <jsp:include page="sidebar.jsp"/>
  <div id="layoutSidenav_content">
    <main class="content-wrapper">
      <div class="container-fluid px-4">
        <h2 class="mt-4">Danh sách tuyến đường</h2>

        <form method="get" action="routes">
          <div class="row mb-3">
            <div class="col-md-4">
              <input type="text" name="search" class="form-control" placeholder="Tìm theo tên tuyến...">
            </div>
            <div class="col-md-3">
              <select name="location" class="form-control">
                <option value="">Chọn địa điểm</option>
                <!-- Lặp danh sách địa điểm -->
                <c:forEach var="location" items="${locations}">
                  <option value="${location.locationId}">${location.name}</option>
                </c:forEach>
              </select>
            </div>
            <div class="col-md-2">
              <button type="submit" class="btn btn-primary"><i class="fas fa-search"></i> Tìm kiếm</button>
            </div>
          </div>
        </form>

        <table class="table table-striped">
          <thead>
          <tr>
            <th>#</th>
            <th>Tên tuyến</th>
            <th>Điểm bắt đầu</th>
            <th>Điểm kết thúc</th>
            <th>Khoảng cách (km)</th>
            <th>Giá vé</th>
            <th>Hành động</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach var="route" items="${routes}">
            <tr>
              <td>${route.routeId}</td>
              <td>${route.routeName}</td>
              <td>${route.startLocation.name}</td>
              <td>${route.endLocation.name}</td>
              <td>${route.distance}</td>
              <td>${route.basePrice} VNĐ</td>
              <td>
                <a href="routes?action=edit&id=${route.routeId}" class="btn btn-warning btn-sm"><i class="fas fa-edit"></i> Sửa</a>
                <a href="routes?action=delete&id=${route.routeId}" class="btn btn-danger btn-sm" onclick="return confirm('Bạn có chắc chắn muốn xóa?')">
                  <i class="fas fa-trash"></i> Xóa
                </a>
              </td>
            </tr>
          </c:forEach>
          </tbody>
        </table>

        <a href="route_form.jsp" class="btn btn-success"><i class="fas fa-plus"></i> Thêm tuyến mới</a>
      </div>
    </main>
  </div>
</div>

<%@ include file="footer.jsp" %>
