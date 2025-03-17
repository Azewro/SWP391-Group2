/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.AdminBusDAO;
import dao.AdminBusTripDAO;
import dao.AdminRouteDAO;
import dao.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Bus;
import model.BusTrip;
import model.Route;
import model.User;

/**
 *
 * @author author
 */
@WebServlet(name = "TripDetail", urlPatterns = {"/TripDetail"})
public class TripDetail extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private final AdminBusTripDAO busTripDAO = new AdminBusTripDAO();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet TripDetail</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TripDetail at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String tripIdParam = request.getParameter("tripId");
            String driver = request.getParameter("driver");
            String busId = request.getParameter("busId");
            String RouteId = request.getParameter("RouteId");
            BusTrip busTrip = null;
            
            if (tripIdParam != null) {
                int tripId = Integer.parseInt(tripIdParam);
                busTrip = busTripDAO.getBusTripById(tripId);
            }

            // Lấy danh sách tài xế, tuyến đường, xe từ DAO
            request.setAttribute("busTrip", busTrip);
            request.setAttribute("seats", busTripDAO.getAvailableSeatsByTripId(tripIdParam));
            request.setAttribute("route", new AdminRouteDAO().getRoute(Integer.valueOf(RouteId)));
            request.setAttribute("driver", new UserDAO().getUserById(Integer.valueOf(driver)));
            AdminBusDAO busDAO = new AdminBusDAO();
            request.setAttribute("bus", busDAO.getBusById(Integer.valueOf(busId)));
            request.getRequestDispatcher("/components/TripDetail.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(TripDetail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
