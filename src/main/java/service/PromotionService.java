package service;

import dao.AdminPromotionDAO;
import dao.AdminTicketPromotionDAO;
import dao.AdminUserPromotionDAO;
import model.Promotion;
import model.TicketPromotion;
import model.UserPromotion;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

public class PromotionService {
    private AdminPromotionDAO promotionDAO;
    private AdminUserPromotionDAO userPromotionDAO;
    private AdminTicketPromotionDAO ticketPromotionDAO;

    public PromotionService(Connection conn) {
        this.promotionDAO = new AdminPromotionDAO(conn);
        this.userPromotionDAO = new AdminUserPromotionDAO(conn);
        this.ticketPromotionDAO = new AdminTicketPromotionDAO(conn);
    }

    // 1️⃣ Áp dụng khuyến mãi khi khách đặt vé
    public BigDecimal applyPromotion(int userId, int ticketId, BigDecimal originalPrice) {
        // Tìm mã giảm giá hợp lệ cho user
        UserPromotion userPromo = getValidPromotionForUser(userId);
        TicketPromotion ticketPromo = getValidPromotionForTicket(ticketId);

        // Chọn mã có giá trị giảm giá cao nhất
        BigDecimal bestDiscount = BigDecimal.ZERO;
        if (userPromo != null) bestDiscount = userPromo.getDiscountPercentage();
        if (ticketPromo != null && ticketPromo.getDiscountPercentage().compareTo(bestDiscount) > 0) {
            bestDiscount = ticketPromo.getDiscountPercentage();
        }

        // Nếu không có mã giảm giá hợp lệ
        if (bestDiscount.compareTo(BigDecimal.ZERO) == 0) {
            return originalPrice;
        }

        // Tính giá sau giảm giá
        return calculateDiscountedPrice(originalPrice, bestDiscount);
    }

    // 2️⃣ Lấy mã giảm giá hợp lệ cho User
    public UserPromotion getValidPromotionForUser(int userId) {
        List<UserPromotion> promotions = userPromotionDAO.getUserPromotionsByUserId(userId);
        for (UserPromotion promo : promotions) {
            if (promo.getExpirationDate().after(new Timestamp(System.currentTimeMillis()))) {
                return promo;
            }
        }
        return null;
    }

    // 3️⃣ Lấy mã giảm giá hợp lệ cho Ticket
    public TicketPromotion getValidPromotionForTicket(int ticketId) {
        List<TicketPromotion> promotions = ticketPromotionDAO.getAllTicketPromotions();
        for (TicketPromotion promo : promotions) {
            if (promo.getTicketId() == ticketId && promo.getExpirationDate().after(new Timestamp(System.currentTimeMillis()))) {
                return promo;
            }
        }
        return null;
    }

    // 4️⃣ Tính toán giá sau giảm giá
    public BigDecimal calculateDiscountedPrice(BigDecimal originalPrice, BigDecimal discountPercentage) {
        BigDecimal discountAmount = originalPrice.multiply(discountPercentage).divide(new BigDecimal(100));
        return originalPrice.subtract(discountAmount);
    }
}
