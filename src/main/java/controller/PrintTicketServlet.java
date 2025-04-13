package controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Ticket;

import java.io.*;
import javax.swing.text.Document;

@WebServlet("/print-ticket")
public class PrintTicketServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        System.out.println("📄 PrintTicketServlet được gọi");

        Ticket ticket = (Ticket) req.getSession().getAttribute("ticket");

        if (ticket == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy vé để in.");
            return;
        }

        resp.setContentType("application/pdf");
        resp.setHeader("Content-Disposition", "attachment; filename=\"ticket.pdf\"");

        try {
            OutputStream out = resp.getOutputStream();
            Document document = new Document(PageSize.A5, 36, 36, 54, 36);

            PdfWriter.getInstance(document, out);
            document.open();

            // Font Unicode
            String fontPath = getServletContext().getRealPath("/WEB-INF/fonts/arial.ttf");
            BaseFont bf = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font titleFont = new Font(bf, 16, Font.BOLD);
            Font textFont = new Font(bf, 12);

            // Tiêu đề
            Paragraph title = new Paragraph("THẺ THÔNG TIN VÉ XE", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20f);
            document.add(title);

            // Bảng thông tin
            PdfPTable table = new PdfPTable(2);
            table.setWidths(new int[]{1, 2});
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            addRow(table, "Họ tên:", ticket.getUser().getFullName(), textFont);
            addRow(table, "SĐT:", ticket.getUser().getPhone(), textFont);
            addRow(table, "Tuyến:", ticket.getTrip().getRoute().getRouteName(), textFont);
            addRow(table, "Khởi hành:", ticket.getTrip().getDepartureTime().toString(), textFont);
            addRow(table, "Số ghế:", String.valueOf(ticket.getSeat().getSeatNumber()), textFont);
            addRow(table, "Giá vé:", ticket.getPrice() + " VND", textFont);
            addRow(table, "Trạng thái:", ticket.getStatus(), textFont);

            document.add(table);

            Paragraph footer = new Paragraph("Vui lòng mang theo thẻ này khi lên xe.", textFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setSpacingBefore(20f);
            document.add(footer);

            document.close();
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Lỗi khi tạo PDF", e);
        }
    }

    private void addRow(PdfPTable table, String label, String value, Font font) {
        PdfPCell cell1 = new PdfPCell(new Phrase(label, font));
        PdfPCell cell2 = new PdfPCell(new Phrase(value, font));
        cell1.setBorder(Rectangle.NO_BORDER);
        cell2.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell1);
        table.addCell(cell2);
    }
}
