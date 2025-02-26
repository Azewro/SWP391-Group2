package controller;

import dao.PopularRoutesDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Route;

@WebServlet(name="PopularRoutesServlet", urlPatterns="/home")
public class PopularRoutesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PopularRoutesDAO popularRoutesDAO = new PopularRoutesDAO();
        try {
            ArrayList<Route> popularRoutes = popularRoutesDAO.getPopularRoutes();
            if (popularRoutes == null || popularRoutes.isEmpty()) {
                System.out.println("Không có tuyến đường phổ biến nào được tìm thấy!");
            } else {
                for (Route route : popularRoutes) {
                    System.out.println(route.getRouteName() + " từ "
                            + route.getStartLocation().getName()
                            + " đến "
                            + route.getEndLocation().getName()
                            + ", Giá vé: " + route.getBasePrice() + " VNĐ");
                }
            }
            request.setAttribute("popularRoutes", popularRoutes);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to load popular routes.");
        }
    } 

    @Override
    public String getServletInfo() {
        return "Servlet tự động hiển thị dữ liệu các tuyến đường phổ biến khi truy cập trang chủ";
    }
}
