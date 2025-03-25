<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="model.BusMaintenanceLog" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<% BusMaintenanceLog log = (BusMaintenanceLog) request.getAttribute("log"); %>

<%@ include file="header.jsp" %>

<div id="layoutSidenav">
    <jsp:include page="sidebar.jsp"/>
    <div id="layoutSidenav_content">
        <main class="content-wrapper">
            <div class="container mt-4">
                <h2 class="mb-3"><%= (log != null) ? "Chỉnh sửa Bảo Trì" : "Thêm Bảo Trì" %></h2>

                <form action="admin/bus-maintenance" method="POST">
                    <input type="hidden" name="action" value="<%= (log != null) ? "update" : "insert" %>">
                    <input type="hidden" name="logId" value="<%= (log != null) ? log.getLogId() : "" %>">
                    <input type="hidden" name="busId" value="${busId}">


                    <div class="mb-3">
                        <label class="form-label">Ngày bảo trì</label>
                        <% String formattedDate = (log != null && log.getMaintenanceDate() != null)
                                ? log.getMaintenanceDate().toString().replace(" ", "T").substring(0, 16)
                                : ""; %>
                        <input type="datetime-local" name="maintenanceDate" class="form-control" required value="<%= formattedDate %>">
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Mô tả</label>
                        <textarea name="description" class="form-control"><%= (log != null) ? log.getDescription() : "" %></textarea>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Chi phí</label>
                        <input type="number" name="cost" class="form-control" required value="<%= (log != null) ? log.getCost() : "" %>">
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Trạng thái</label>
                        <select name="status" class="form-select">
                            <option value="Scheduled" <%= (log != null && log.getStatus().equals("Scheduled")) ? "selected" : "" %>>Đang chờ</option>
                            <option value="Completed" <%= (log != null && log.getStatus().equals("Completed")) ? "selected" : "" %>>Hoàn thành</option>
                        </select>
                    </div>

                    <button type="submit" class="btn btn-primary">
                        <i class="fas fa-save"></i> Lưu
                    </button>
                    <a href="bus-maintenance?action=list&busId=<%= (request.getParameter("busId") != null) ? request.getParameter("busId") : "0" %>"
                       class="btn btn-secondary">
                        <i class="fas fa-arrow-left"></i> Quay lại
                    </a>

                </form>
            </div>
        </main>
    </div>
</div>

<%@ include file="footer.jsp" %>
