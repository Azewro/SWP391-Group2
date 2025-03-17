<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="model.Bus" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<% Bus bus = (Bus) request.getAttribute("bus"); %>

<%@ include file="header.jsp" %>

<div id="layoutSidenav">
    <jsp:include page="sidebar.jsp"/>
    <div id="layoutSidenav_content">
        <main class="content-wrapper">
            <div class="container mt-4">
                <h2 class="mb-3"><%= (bus != null) ? "Chỉnh sửa Xe Buýt" : "Thêm Xe Buýt" %></h2>

                <form action="bus" method="POST">
                    <input type="hidden" name="action" value="<%= (bus != null) ? "update" : "insert" %>">
                    <input type="hidden" name="busId" value="<%= (bus != null) ? bus.getBusId() : "" %>">

                    <div class="mb-3">
                        <label class="form-label">Biển số xe</label>
                        <input type="text" name="plateNumber" class="form-control" required value="<%= (bus != null) ? bus.getPlateNumber() : "" %>">
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Số ghế</label>
                        <input type="number" name="capacity" class="form-control" required value="<%= (bus != null) ? bus.getCapacity() : "" %>">
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Loại xe</label>
                        <input type="text" name="busType" class="form-control" value="<%= (bus != null) ? bus.getBusType() : "" %>">
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Trạng thái</label>
                        <select name="isActive" class="form-select">
                            <option value="true" <%= (bus != null && bus.isActive()) ? "selected" : "" %>>Hoạt động</option>
                            <option value="false" <%= (bus != null && !bus.isActive()) ? "selected" : "" %>>Ngừng hoạt động</option>
                        </select>
                    </div>

                    <button type="submit" class="btn btn-primary">
                        <i class="fas fa-save"></i> Lưu
                    </button>
                    <a href="bus?action=list" class="btn btn-secondary">
                        <i class="fas fa-arrow-left"></i> Quay lại
                    </a>
                </form>
            </div>
        </main>
    </div>
</div>

<%@ include file="footer.jsp" %>
