package scheduler;

import service.AutoPromotionJob;
import util.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainScheduler {
    public static void main(String[] args) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        AutoPromotionJob job = new AutoPromotionJob(conn);

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(job, 0, 24, TimeUnit.HOURS); // Chạy mỗi 24h
        System.out.println("⏳ Cron Job đã được lên lịch chạy mỗi ngày.");
    }
}
