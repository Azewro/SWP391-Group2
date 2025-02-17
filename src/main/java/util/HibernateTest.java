package util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class HibernateTest {
    public static void main(String[] args) {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("G2BusTicketSystem");
            EntityManager em = emf.createEntityManager();
            System.out.println("✅ Hibernate kết nối thành công!");
            em.close();
            emf.close();
        } catch (Exception e) {
            System.out.println("❌ Lỗi kết nối Hibernate:");
            e.printStackTrace();
        }
    }
}
