package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.stream.Collectors;

@WebServlet("/google-callback")
public class GoogleCallbackServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String CLIENT_ID = "your-client-id";
    private static final String CLIENT_SECRET = "your-client-secret";
    private static final String REDIRECT_URI = "http://localhost:8080/google-callback";
    private static final String TOKEN_URL = "https://oauth2.googleapis.com/token";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");

        if (code == null) {
            response.sendRedirect("login.jsp?error=google_login_failed");
            return;
        }

        // Lấy access_token từ Google
        String tokenRequestBody = "code=" + code
                + "&client_id=" + CLIENT_ID
                + "&client_secret=" + CLIENT_SECRET
                + "&redirect_uri=" + REDIRECT_URI
                + "&grant_type=authorization_code";

        String accessTokenJson = sendPostRequest(TOKEN_URL, tokenRequestBody);
        JSONObject jsonObject = new JSONObject(accessTokenJson);
        String accessToken = jsonObject.getString("access_token");

        // Lấy thông tin user từ Google
        String userInfoJson = sendGetRequest("https://www.googleapis.com/oauth2/v2/userinfo", accessToken);
        JSONObject userInfo = new JSONObject(userInfoJson);

        String email = userInfo.getString("email");
        String fullName = userInfo.getString("name");

        // Kiểm tra xem user đã tồn tại trong database chưa
        boolean userExists = checkUserExists(email);

        HttpSession session = request.getSession();
        session.setAttribute("user_email", email);
        session.setAttribute("user_fullname", fullName);

        if (userExists) {
            response.sendRedirect("index    .jsp");
        } else {
            saveUserToDatabase(email, fullName);
            response.sendRedirect("register2.jsp");
        }
    }

    private boolean checkUserExists(String email) {
        String sql = "SELECT COUNT(*) FROM Users WHERE email = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/G2BusTicketSystem", "root", "");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void saveUserToDatabase(String email, String fullName) {
        String sql = "INSERT INTO Users (email, full_name, auth_type) VALUES (?, ?, 'google')";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/G2BusTicketSystem", "root", "");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, fullName);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String sendPostRequest(String url, String body) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setDoOutput(true);
        try (OutputStream os = conn.getOutputStream()) {
            os.write(body.getBytes());
            os.flush();
        }
        return new BufferedReader(new InputStreamReader(conn.getInputStream())).lines()
                .collect(Collectors.joining("\n"));
    }

    private String sendGetRequest(String url, String accessToken) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + accessToken);
        return new BufferedReader(new InputStreamReader(conn.getInputStream())).lines()
                .collect(Collectors.joining("\n"));
    }
}
