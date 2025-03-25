<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.BusMaintenanceLog" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ include file="header.jsp" %>

<div id="layoutSidenav">
    <jsp:include page="sidebar.jsp"/>
    <div id="layoutSidenav_content">
        <main class="content-wrapper">
            <div class="container mt-4">
                <h2 class="mb-3">Lịch sử bảo trì xe buýt</h2>

                <%-- Nút quay lại danh sách xe buýt --%>
                <a href="bus?action=list" class="btn btn-secondary mb-3">
                    <i class="fas fa-arrow-left"></i> Quay lại danh sách xe buýt
                </a>

                <%-- Nút thêm bảo trì --%>
                <a href="bus-maintenance?action=edit&busId=${busId}" class="btn btn-success mb-3">
                    <i class="fas fa-plus"></i> Thêm bảo trì
                </a>

                <%-- Bảng lịch sử bảo trì --%>
                <div class="table-responsive">
                    <table class="table table-bordered table-hover">
                        <thead class="table-dark text-center">
                        <tr>
                            <th>ID</th>
                            <th>Ngày bảo trì</th>
                            <th>Mô tả</th>
                            <th>Chi phí</th>
                            <th>Trạng thái</th>
                            <th>Hành động</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="log" items="${maintenanceLogs}">
                            <tr class="text-center">
                                <td>${log.logId}</td>
                                <td>${log.maintenanceDate}</td>
                                <td>${log.description}</td>
                                <td>${log.cost} VND</td>
                                <td>
                                        <span class="badge ${log.status == 'Completed' ? 'bg-success' : 'bg-warning'}">
                                                ${log.status}
                                        </span>
                                </td>
                                <td>
                                        <%-- Đường dẫn sửa/xóa bảo trì --%>
                                    <a href="bus-maintenance?action=edit&logId=${log.logId}" class="btn btn-warning btn-sm">
                                        <i class="fas fa-edit"></i> Sửa
                                    </a>
                                    <a href="bus-maintenance?action=delete&logId=${log.logId}" class="btn btn-danger btn-sm" onclick="return confirm('Xóa bản ghi này?');">
                                        <i class="fas fa-trash"></i> Xóa
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </main>
    </div>
</div>

<%@ include file="footer.jsp" %>
