/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import dao.BlogDAO;
import dao.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Blog;
import model.BlogCategory;

/**
 *
 * @author author
 */
@WebServlet("/admin/blog")
public class BlogServlet extends HttpServlet {

    private BlogDAO blogDAO;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        blogDAO = new BlogDAO();
        try {
            userDAO = new UserDAO();
        } catch (SQLException ex) {
            Logger.getLogger(BlogServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if ("delete".equals(action)) {
                deleteBlog(request, response);
            } else if ("edit".equals(action)) {
                showEditForm(request, response);
            } else if ("create".equals(action)) {
                request.setAttribute("cls", blogDAO.getCategories("", 0, 1110));
                request.getRequestDispatcher("./createBlog.jsp").forward(request, response);
            } else {
                listBlogs(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if ("create".equals(action)) {
                createBlog(request, response);
            } else if ("update".equals(action)) {
                updateBlog(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void listBlogs(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String title = request.getParameter("title");
        Integer categoryId = null;
        if (request.getParameter("categoryId") != null && request.getParameter("categoryId") != "") {
            categoryId = Integer.valueOf(request.getParameter("categoryId"));
        }

        // Pagination parameters
        int page = 1;
        int limit = 3;  // Number of blogs per page
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        int offset = (page - 1) * limit;

        // Fetch paginated blogs
        List<Blog> blogs = blogDAO.getBlogs(title != null ? title : "", categoryId, offset, limit);

        // Get total count for pagination
        int totalBlogs = blogDAO.countBlogs(title != null ? title : "", categoryId);
        int totalPages = (int) Math.ceil((double) totalBlogs / limit);
        // Set attributes for JSP
        request.setAttribute("blogs", blogs);
        request.setAttribute("cls", blogDAO.getCategories("", 0, 1110));
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);

        request.getRequestDispatcher("./blog.jsp").forward(request, response);
    }

    private void createBlog(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String summary = request.getParameter("summary");
        String img = request.getParameter("img");
        String cateId = request.getParameter("cateId");
        System.out.println("===================== " +cateId);
        //câp nhật sau
        int author = 1;
        Blog blog = new Blog(0, title, content, summary, img, new BlogCategory(Integer.valueOf(cateId)), author);
        blogDAO.createBlog(blog);
        response.sendRedirect("./blog");
    }

    private void updateBlog(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String summary = request.getParameter("summary");
        String img = request.getParameter("img");
        String cateId = request.getParameter("cateId");

        //câp nhật sau
        int author = 1;
        Blog blog = new Blog(id, title, content, summary, img, new BlogCategory(Integer.valueOf(cateId)), author);
        blogDAO.updateBlog(blog);
        response.sendRedirect("./blog");
    }

    private void deleteBlog(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        blogDAO.deleteBlog(id);
        response.sendRedirect("blog");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Blog existingBlog = blogDAO.getBlog(id);
        request.setAttribute("blog", existingBlog);
        request.setAttribute("ul", userDAO.getAllUsers("", Boolean.TRUE));
        request.setAttribute("cls", blogDAO.getCategories("", 0, 1110));
        request.getRequestDispatcher("./edit-blog.jsp").forward(request, response);
    }
}
