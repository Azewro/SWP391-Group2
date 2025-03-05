<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Blog Categories</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <h2 class="mb-4">Blog Categories</h2>
        <a href="blog-category?action=create" class="btn btn-success">Create new</a>
        <!-- Filter Form -->
        <form class="row mb-3" method="get" action="blog-category">
            <input type="hidden" name="action" value="list">
            <div class="col-md-4">
                <input type="text" class="form-control" name="name" placeholder="Search by name" value="${param.name}">
            </div>
            <div class="col-md-2">
                <button type="submit" class="btn btn-primary">Search</button>
            </div>
        </form>
        
        <!-- Category Table -->
        <table class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="category" items="${categories}">
                    <tr>
                        <td>${category.id}</td>
                        <td>${category.name}</td>
                        <td>${category.description}</td>
                        <td>
                            <a href="blog-category?action=edit&id=${category.id}" class="btn btn-warning btn-sm">Edit</a>
                            <a href="blog-category?action=delete&id=${category.id}" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure?')">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        
            <nav>
            <ul class="pagination">
                <c:if test="${currentPage > 1}">
                    <li class="page-item">
                        <a class="page-link" href="blog-category?page=${currentPage - 1}&name=${param.name}&categoryId=${param.categoryId}">Previous</a>
                    </li>
                </c:if>

                <c:forEach var="i" begin="1" end="${totalPages}">
                    <li class="page-item ${i == currentPage ? 'active' : ''}">
                        <a class="page-link" href="blog-category?page=${i}&name=${param.name}&categoryId=${param.categoryId}">${i}</a>
                    </li>
                </c:forEach>

                <c:if test="${currentPage < totalPages}">
                    <li class="page-item">
                        <a class="page-link" href="blog-category?page=${currentPage + 1}&name=${param.name}&categoryId=${param.categoryId}">Next</a>
                    </li>
                </c:if>
            </ul>
        </nav>
        
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
