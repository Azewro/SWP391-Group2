package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import model.UserOAuth;

public class UserOAuthDAO {
    private EntityManager entityManager;

    public UserOAuthDAO() {
        this.entityManager = Persistence.createEntityManagerFactory("G2BusTicketSystem").createEntityManager();
    }

    public boolean saveUserOAuth(UserOAuth userOAuth) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(userOAuth);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    public boolean isOAuthUserExist(String providerUserId) {
        try {
            UserOAuth userOAuth = entityManager.createQuery(
                            "SELECT u FROM UserOAuth u WHERE u.providerUserId = :providerUserId", UserOAuth.class)
                    .setParameter("providerUserId", providerUserId)
                    .getSingleResult();
            return userOAuth != null;
        } catch (NoResultException e) {
            return false;
        }
    }
}
