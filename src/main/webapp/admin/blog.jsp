<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Blog Management</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    </head>
    <body class="container mt-4">
        <h1 class="mb-3">Blog List</h1>
        <a class="btn btn-success" href="./blog?action=create">Create Blog</a>
        <form action="blog" method="get" class="mb-3 d-flex gap-2">
            <input type="text" name="title" class="form-control w-25" placeholder="Search Title" value="${param.title}"/>
            <select name="categoryId" class="form-select w-25">
                <option value="">All</option>
                <c:forEach var="c" items="${cls}">
                    <option value="${c.id}" ${param.categoryId == c.id ? 'selected' : ''}>${c.name}</option>
                </c:forEach>
            </select>
            <button type="submit" class="btn btn-primary">Filter</button>
        </form>

        <table class="table table-striped table-bordered">
            <thead class="table-dark">
                <tr>
                    <th>Id</th>
                    <th>Title</th>
                    <th>Image</th>
                    <th>Category</th>
                    <th>Summary</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="blog" items="${blogs}">
                    <tr>
                        <td>${blog.id}</td>
                        <td>${blog.title}</td>
                        <td><img width="160px" src="${blog.img}" alt="alt"/></td>
                        <td>${blog.category.name}</td>
                        <td>${blog.summary}</td>
                        <td>
                            <a href="blog?action=edit&id=${blog.id}" class="btn btn-warning btn-sm">Edit</a>
                            <a href="blog?action=delete&id=${blog.id}" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure?')">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <nav>
            <ul class="pagination">
                <c:if test="${currentPage > 1}">
                    <li class="page-item">
                        <a class="page-link" href="blog?page=${currentPage - 1}&title=${param.title}&categoryId=${param.categoryId}">Previous</a>
                    </li>
                </c:if>

                <c:forEach var="i" begin="1" end="${totalPages}">
                    <li class="page-item ${i == currentPage ? 'active' : ''}">
                        <a class="page-link" href="blog?page=${i}&title=${param.title}&categoryId=${param.categoryId}">${i}</a>
                    </li>
                </c:forEach>

                <c:if test="${currentPage < totalPages}">
                    <li class="page-item">
                        <a class="page-link" href="blog?page=${currentPage + 1}&title=${param.title}&categoryId=${param.categoryId}">Next</a>
                    </li>
                </c:if>
            </ul>
        </nav>



    </body>
</html>
