package controller;

import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.UserDAO;
import model.User;
import util.DatabaseConnection;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;

@WebServlet("/google-callback")
public class GoogleCallbackServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String CLIENT_ID = "627788153739-pqbr1b10t2m0ggsrvfjihc5tacgi2jes.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "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";
    private static final String REDIRECT_URI = "http://localhost:8080/SWP391_Group2_war_exploded/google-callback";

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");

        // Nếu không có code, chuyển hướng đến trang login Google
        if (code == null || code.isEmpty()) {
            String googleAuthUrl = "https://accounts.google.com/o/oauth2/auth"
                    + "?client_id=" + CLIENT_ID
                    + "&redirect_uri=" + REDIRECT_URI
                    + "&response_type=code"
                    + "&scope=openid%20email%20profile"
                    + "&access_type=offline"
                    + "&prompt=consent";
            System.out.println(googleAuthUrl);
            System.out.println("❌ Không nhận được code từ Google!");
            response.sendRedirect("login.jsp?error=google_auth_failed");
            return;
        }

        try {
            // Lấy access token từ Google
            TokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(
                    GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY,
                    "https://oauth2.googleapis.com/token", CLIENT_ID, CLIENT_SECRET, code, REDIRECT_URI)
                    .execute();

            String idTokenString = tokenResponse.get("id_token").toString();

            // Xác minh ID Token
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY)
                    .setAudience(Collections.singletonList(CLIENT_ID))
                    .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken == null) {
                response.sendRedirect("login.jsp?error=invalid_token");
                return;
            }

            GoogleIdToken.Payload payload = idToken.getPayload();

            // Lấy thông tin người dùng từ Google
            String email = payload.getEmail();
            String fullName = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");

// Kiểm tra người dùng trong database
            UserDAO userDAO = new UserDAO();
            User user = userDAO.findByUsernameOrEmail(email);

            HttpSession session = request.getSession();

            if (user != null) {
                if (!user.isActive()) {
                    request.setAttribute("error", "Tài khoản của bạn đã bị khóa. Lý do: " + user.getStatusReason());
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }

                // Nếu user đã tồn tại, đồng bộ session với LoginServlet
                session.setAttribute("user", user);
                session.setAttribute("role_id", user.getRoleId());

                if (user.getRoleId() == 1 || user.getRoleId() == 2) {
                    response.sendRedirect("admin/dashboard");
                } else {
                    response.sendRedirect("index.jsp");
                }
            } else {
                // ❌ Không tồn tại user → hiện thông báo lỗi và quay về login
                request.setAttribute("error", "Bạn chưa có tài khoản, vui lòng đăng ký.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }


        } catch (GeneralSecurityException | SQLException e) {
            throw new ServletException("Lỗi khi xác minh Google ID Token", e);
        }
    }

    private boolean checkUserExists(String email) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT COUNT(*) FROM Users WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void saveUserToDatabase(String email, String fullName, String pictureUrl) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO Users (email, full_name, auth_type) VALUES (?, ?, 'google')";
            PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, email);
            stmt.setString(2, fullName);
            stmt.executeUpdate();

            // Lấy user_id mới tạo
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            int userId = -1;
            if (generatedKeys.next()) {
                userId = generatedKeys.getInt(1);
            }

            // Lưu vào UserOAuth
            if (userId > 0) {
                String oauthSql = "INSERT INTO UserOAuth (user_id, provider, provider_user_id, email, full_name, profile_picture) " +
                        "VALUES (?, 'google', ?, ?, ?, ?) ON DUPLICATE KEY UPDATE full_name=?, profile_picture=?";
                PreparedStatement stmtOAuth = conn.prepareStatement(oauthSql);
                stmtOAuth.setInt(1, userId);
                stmtOAuth.setString(2, email);
                stmtOAuth.setString(3, email);
                stmtOAuth.setString(4, fullName);
                stmtOAuth.setString(5, pictureUrl);
                stmtOAuth.setString(6, fullName);
                stmtOAuth.setString(7, pictureUrl);
                stmtOAuth.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
