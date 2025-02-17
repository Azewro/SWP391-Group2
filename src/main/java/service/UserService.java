package service;

import model.User;
import org.mindrot.jbcrypt.BCrypt;
import dao.UserDAO;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public boolean isUserExist(String email, String username) {
        return userDAO.isUserExist(email, username);
    }

    private boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[^\\s]{8,32}$";
        return password.matches(passwordPattern);
    }

    public String registerUser(String username, String password, String email, String phone, String fullName) {
        if (userDAO.isUserExist(email, username)) {
            return "Email hoặc tên đăng nhập đã tồn tại!";
        }

        if (!isValidPassword(password)) {
            return "Mật khẩu phải có ít nhất 8 ký tự, bao gồm chữ hoa, chữ thường, số và không có khoảng trắng.";
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
        User user = new User(username, hashedPassword, email, phone, fullName);
        boolean success = userDAO.saveUser(user);
        return success ? "success" : "Đăng ký thất bại, vui lòng thử lại!";
    }
}
