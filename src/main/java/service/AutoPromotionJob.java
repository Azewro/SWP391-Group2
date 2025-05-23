package service;

import dao.AdminTicketPromotionDAO;
import dao.AdminUserPromotionDAO;
import dao.AdminPromotionDAO;
import model.UserPromotion;
import model.TicketPromotion;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

public class AutoPromotionJob implements Runnable {
    private AdminUserPromotionDAO userPromotionDAO;
    private AdminTicketPromotionDAO ticketPromotionDAO;
    private AdminPromotionDAO promotionDAO;

    public AutoPromotionJob(Connection conn) {
        this.userPromotionDAO = new AdminUserPromotionDAO(conn);
        this.ticketPromotionDAO = new AdminTicketPromotionDAO(conn);
        this.promotionDAO = new AdminPromotionDAO(conn);
    }

    @Override
    public void run() {
        System.out.println("🔄 Đang chạy Cron Job cấp mã giảm giá...");
        applyVipDiscount();
        applyLowTrafficDiscount();
        applyNewUserDiscount();
        applyOffPeakDiscount(); // 🆕 Giảm giá giờ thấp điểm
        System.out.println("✅ Cron Job hoàn thành!");
    }


    // 1️⃣ Tự động cấp mã giảm giá cho khách VIP
    private void applyVipDiscount() {
        List<Integer> vipUsers = userPromotionDAO.getVipUsers(5); // Lấy danh sách khách hàng đã mua ≥5 vé
        for (int userId : vipUsers) {
            userPromotionDAO.addUserPromotion(new UserPromotion(
                    0, userId, new BigDecimal("10.0"), "VIP10", new Timestamp(System.currentTimeMillis() + 2592000000L) // Hạn 30 ngày
            ));
            System.out.println("🎟️ Đã cấp mã VIP10 cho user " + userId);
        }
    }

    // 2️⃣ Giảm giá cho tuyến xe vắng khách (>50% ghế trống)
    private void applyLowTrafficDiscount() {
        List<Integer> lowTrafficTrips = ticketPromotionDAO.getLowTrafficTrips(50); // Lấy danh sách chuyến xe ít khách
        for (int tripId : lowTrafficTrips) {
            ticketPromotionDAO.addTicketPromotion(new TicketPromotion(
                    0, tripId, new BigDecimal("15.0"), "LOWTRAFFIC15", new Timestamp(System.currentTimeMillis() + 86400000L) // Hạn 1 ngày
            ));
            System.out.println("🚌 Đã giảm giá 15% cho tuyến " + tripId);
        }
    }

    // 3️⃣ Giảm giá cho khách hàng mới (Lần đầu đặt vé)
    private void applyNewUserDiscount() {
        List<Integer> newUsers = userPromotionDAO.getNewUsers(); // Lấy danh sách user chưa từng đặt vé
        for (int userId : newUsers) {
            userPromotionDAO.addUserPromotion(new UserPromotion(
                    0, userId, new BigDecimal("10.0"), "NEWUSER10",
                    new Timestamp(System.currentTimeMillis() + 2592000000L) // Hạn 30 ngày
            ));
            System.out.println("🎉 Đã cấp mã NEWUSER10 cho user " + userId);
        }
    }

    // 4️⃣ Giảm giá cho các tuyến xe chạy giờ thấp điểm (10h - 15h)
    private void applyOffPeakDiscount() {
        List<Integer> offPeakTrips = ticketPromotionDAO.getOffPeakTrips(10, 15); // Lấy danh sách chuyến xe khởi hành từ 10h - 15h
        for (int tripId : offPeakTrips) {
            ticketPromotionDAO.addTicketPromotion(new TicketPromotion(
                    0, tripId, new BigDecimal("10.0"), "OFFPEAK10",
                    new Timestamp(System.currentTimeMillis() + 86400000L) // Hạn sử dụng trong ngày
            ));
            System.out.println("⏰ Đã giảm giá 10% cho tuyến xe giờ thấp điểm " + tripId);
        }
    }

}
