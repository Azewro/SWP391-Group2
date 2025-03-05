<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Blog Category</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card shadow">
                    <div class="card-header bg-warning text-white text-center">
                        <h2>Edit Blog Category</h2>
                    </div>
                    <div class="card-body">
                        <form action="blog-category" method="post">
                            <input type="hidden" name="action" value="update">
                            <input type="hidden" name="id" value="${category.id}">
                            
                            <div class="mb-3">
                                <label class="form-label">Category Name:</label>
                                <input type="text" class="form-control" name="name" value="${category.name}" required>
                            </div>
                            
                            <div class="mb-3">
                                <label class="form-label">Description:</label>
                                <textarea class="form-control" name="description" required>${category.description}</textarea>
                            </div>
                            
                            <div class="text-center">
                                <button type="submit" class="btn btn-primary">Update</button>
                                <a href="blog-category-list.jsp" class="btn btn-secondary">Cancel</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
