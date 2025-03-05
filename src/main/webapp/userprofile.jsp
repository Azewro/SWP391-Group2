<%--
  Created by IntelliJ IDEA.
  User: LONG
  Date: 3/5/2025
  Time: 11:12 AM
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>User Profile</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body {
      background-color: #f8f9fa;
    }
    .profile-container {
      max-width: 900px;
      margin: 50px auto;
    }
    .card {
      border-radius: 10px;
    }
    .profile-img {
      width: 120px;
      height: 120px;
      border-radius: 50%;
      margin: auto;
      display: block;
    }
  </style>
</head>
<body>
<div class="container profile-container">
  <div class="card p-4">
    <div class="text-center">
      <img src="https://bootdey.com/img/Content/avatar/avatar7.png" class="profile-img" alt="User Avatar">
      <h4 class="mt-3">${user.fullName}</h4>
      <p>Full Stack Developer</p>
    </div>
  </div>

  <div class="card mt-4 p-4">
    <h5>Profile Information</h5>
    <div class="row mt-3">
      <div class="col-md-6">
        <label class="form-label">Full Name</label>
        <input type="text" class="form-control" value="${user.fullName}" readonly>
      </div>
      <div class="col-md-6">
        <label class="form-label">Email</label>
        <input type="text" class="form-control" value="${user.email}" readonly>
      </div>
    </div>
    <div class="row mt-3">
      <div class="col-md-6">
        <label class="form-label">Phone</label>
        <input type="text" class="form-control" value="${user.phone}" readonly>
      </div>
      <div class="col-md-6">
        <label class="form-label">Address</label>
        <input type="text" class="form-control" value="Bay Area, San Francisco, CA" readonly>
      </div>
    </div>
    <div class="text-center mt-3">
      <button class="btn btn-primary">Edit</button>
    </div>
  </div>
</div>
</body>
</html>

