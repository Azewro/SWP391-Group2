package scheduler;

import service.AutoPromotionJob;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CronJobManager {
    private static ScheduledExecutorService scheduler;
    private static boolean isRunning = false;

    public static void startCronJob() throws SQLException {
        if (isRunning) {
            System.out.println("⚠️ Cron Job đã chạy, không thể khởi động lại.");
            return;
        }

        Connection conn = DatabaseConnection.getConnection();
        AutoPromotionJob job = new AutoPromotionJob(conn);

        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(job, 0, 24, TimeUnit.HOURS); // Chạy mỗi 24h
        isRunning = true;

        System.out.println("✅ Cron Job đã bắt đầu.");
    }

    public static void stopCronJob() {
        if (scheduler != null) {
            scheduler.shutdown();
            isRunning = false;
            System.out.println("🛑 Cron Job đã dừng.");
        }
    }

    public static boolean isCronJobRunning() {
        return isRunning;
    }
}
