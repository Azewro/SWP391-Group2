<%@ page import="model.User" %><%-- Created by IntelliJ IDEA. User: Acer Date: 2/17/2025 Time: 4:13 PM To change this
    template use File | Settings | File Templates. --%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
      <%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <html lang="vi">

        <head>
          <meta charset="UTF-8">
          <meta name="viewport" content="width=device-width, initial-scale=1.0">
          <title>Hệ Thống Bán Vé Xe Buýt</title>
          <link rel="stylesheet" href="styles.css">
          <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css">
         
        </head>

        <body>
          <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>

          <jsp:include page="/components/header.jsp" />


          
          <!-- content -->
          <%@ include file="./components/popularRoutes.jsp" %>

          <%@ include file="./components/promotion.jsp" %>
            <footer class="footer">
              <div class="footer-content">
                <div class="support-info">
                  <h3>TRUNG TÂM TỔNG ĐÀI & CSKH</h3>
                  <p class="hotline">1900 6067</p>
                  <p>CÔNG TY CỔ PHẦN XE KHÁCH G2 Bus - G2 BUS LINES</p>
                  <p>Địa chỉ: Hòa Lạc, Hà Nội, Việt Nam.</p>
                  <p>Email: <a href="mailto:GPT@fpt.edu.vn">GPT@fpt.edu.vn</a></p>
                  <p>Điện thoại: 0979605489</p>
                </div>
                <div class="footer-links">
                  <ul>
                    <li><a href="#">Về chúng tôi</a></li>
                    <li><a href="#">Lịch trình</a></li>
                    <li><a href="#">Tuyển dụng</a></li>
                    <li><a href="#">Tin tức & Sự kiện</a></li>
                    <li><a href="#">Mạng lưới văn phòng</a></li>
                  </ul>
                </div>
              </div>
            </footer>




        </body>

        </html>