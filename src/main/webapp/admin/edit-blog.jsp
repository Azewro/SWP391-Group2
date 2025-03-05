<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Edit Blog</title>
        <script src="https://cdn.ckeditor.com/4.16.2/standard/ckeditor.js"></script>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    </head>
    <body class="bg-light">

        <div class="container mt-5">
            <div class="row justify-content-center">
                <div class="col-md-8">
                    <div class="card shadow">
                        <div class="card-header bg-warning text-white text-center">
                            <h2>Edit Blog</h2>
                        </div>
                        <div class="card-body">
                            <form action="blog" method="post">
                                <input type="hidden" name="action" value="update">
                                <input type="hidden" name="id" value="${blog.id}">

                                <div class="mb-3">
                                    <label class="form-label">Title:</label>
                                    <input type="text" class="form-control" name="title" value="${blog.title}" required>
                                </div>

                                <div class="mb-3">
                                    <label class="form-label">Content:</label>
                                    <textarea class="form-control" name="content" id="content" required>${blog.content}</textarea>
                                </div>
                                <script>
                                    CKEDITOR.replace('content');
                                </script>

                                <div class="mb-3">
                                    <label class="form-label">Summary:</label>
                                    <input type="text" class="form-control" name="summary" value="${blog.summary}" required>
                                </div>

                                <div class="mb-3">
                                    <label class="form-label">Upload Image:</label>
                                    <input type="file" class="form-control" id="imgUpload" accept="image/*" onchange="convertToBase64()">
                                    <input type="hidden" name="img" id="imgBase64" value="${blog.img}">
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Category:</label>
                                    <select class="form-select" name="cateId" required>
                                        <c:forEach var="u" items="${cls}">
                                            <option value="${u.id}" ${blog.category.id == u.id?"selected":""}>${u.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="mb-3 text-center">
                                    <label class="form-label">Preview:</label>
                                    <br>
                                    <img id="imgPreview" src="${blog.img}" class="img-fluid border" style="max-width: 300px; height: auto;">
                                </div>



                                <div class="text-center">
                                    <button type="submit" class="btn btn-primary">Update</button>
                                    <a href="blog" class="btn btn-secondary">Cancel</a>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script>
            function convertToBase64() {
                let fileInput = document.getElementById("imgUpload");
                let imgBase64Input = document.getElementById("imgBase64");
                let imgPreview = document.getElementById("imgPreview");

                if (fileInput.files.length > 0) {
                    let file = fileInput.files[0];
                    let reader = new FileReader();

                    reader.onload = function (e) {
                        imgBase64Input.value = e.target.result; // Store Base64 in hidden input
                        imgPreview.src = e.target.result; // Show preview
                    };

                    reader.readAsDataURL(file); // Convert to Base64
                }
            }
        </script>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
