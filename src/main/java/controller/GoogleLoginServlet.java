package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/google-login")
public class GoogleLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String CLIENT_ID = "your-client-id"; // Thay bằng Client ID từ Google
    private static final String REDIRECT_URI = "http://localhost:8080/google-callback";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IOException {
        String googleAuthUrl = "https://accounts.google.com/o/oauth2/auth"
                + "?client_id=" + CLIENT_ID
                + "&redirect_uri=" + REDIRECT_URI
                + "&response_type=code"
                + "&scope=email%20profile"
                + "&access_type=offline";

        response.sendRedirect(googleAuthUrl);
    }
}

