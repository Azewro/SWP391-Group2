/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.BlogDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import model.BlogCategory;

/**
 *
 * @author author
 */
@WebServlet("/admin/blog-category")
public class BlogCategoryServlet extends HttpServlet {

    private BlogDAO blogDAO;

    @Override
    public void init() {
        blogDAO = new BlogDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action == null || action.equals("list")) {
                listCategories(request, response);
            } else if (action.equals("edit")) {
                showEditForm(request, response);
            } else if (action.equals("create")) {
                request.getRequestDispatcher("./create-blog-category.jsp").forward(request, response);
            } else if (action.equals("delete")) {
                deleteCategory(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action.equals("create")) {
                createCategory(request, response);
            } else if (action.equals("update")) {
                updateCategory(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void listCategories(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String name = request.getParameter("name");
        if (name == null) {
            name = "";
        }

        int page = 1;
        int recordsPerPage = 5;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        int offset = (page - 1) * recordsPerPage;
        List<BlogCategory> categories = blogDAO.getCategories(name, offset, recordsPerPage);
        int totalRecords = blogDAO.countCategories(name);
        int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);

        request.setAttribute("categories", categories);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("name", name);
        request.getRequestDispatcher("./blog-category-list.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        BlogCategory category = blogDAO.getCategory(id);
        request.setAttribute("category", category);
        request.getRequestDispatcher("./edit-blog-category.jsp").forward(request, response);
    }

    private void createCategory(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        BlogCategory category = new BlogCategory(0, name, description);
        blogDAO.createCategory(category);
        response.sendRedirect("blog-category?action=list");
    }

    private void updateCategory(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        BlogCategory category = new BlogCategory(id, name, description);
        blogDAO.updateCategory(category);
        response.sendRedirect("blog-category?action=list");
    }

    private void deleteCategory(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        blogDAO.deleteCategory(id);
        response.sendRedirect("blog-category?action=list");
    }
}
