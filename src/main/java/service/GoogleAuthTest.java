package service;


import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Scanner;

public class GoogleAuthTest {
    private static final String CLIENT_ID = "627788153739-pqbr1b10t2m0ggsrvfjihc5tacgi2jes.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "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";
    private static final String REDIRECT_URI = "http://localhost:8080/SWP391_Group2_war_exploded/google-callback";

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("🔗 Vui lòng đăng nhập Google bằng link sau:");
        String googleAuthUrl = "https://accounts.google.com/o/oauth2/auth"
                + "?client_id=" + CLIENT_ID
                + "&redirect_uri=" + REDIRECT_URI
                + "&response_type=code"
                + "&scope=openid%20email%20profile"
                + "&access_type=offline"
                + "&prompt=consent";
        System.out.println(googleAuthUrl);

        System.out.println("\n✍ Nhập code từ Google sau khi đăng nhập:");
        String code = scanner.nextLine();

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
                System.out.println("❌ Lỗi: Token không hợp lệ!");
                return;
            }

            GoogleIdToken.Payload payload = idToken.getPayload();

            // Hiển thị thông tin người dùng từ Google
            System.out.println("\n✅ Đăng nhập thành công!");
            System.out.println("📧 Email: " + payload.getEmail());
            System.out.println("👤 Tên đầy đủ: " + payload.get("name"));
            System.out.println("🖼 Ảnh đại diện: " + payload.get("picture"));

        } catch (GeneralSecurityException | IOException e) {
            System.out.println("❌ Lỗi trong quá trình xác thực: " + e.getMessage());
            e.printStackTrace();
        }

        scanner.close();
    }
}

