package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Subject;
import modelsDAO.SubjectDAO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;

@WebServlet(name = "DownloadPDFServlet", urlPatterns = "/descarga")
public class DownloadPDFServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("subjectId"));
        Subject subject = SubjectDAO.getSubjectByID(id);
        if (subject != null) {
            try (PDDocument pdf = new PDDocument()) {
                PDPage page = new PDPage();
                pdf.addPage(page);
                try (PDPageContentStream contentStream = new PDPageContentStream(pdf, page)) {
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.TIMES_ROMAN, 14);
                    contentStream.newLineAtOffset(100, 700);
                    contentStream.showText("Info sobre la asignatura de " + subject.getName());
                    contentStream.endText();
                }
                // Indicamos al navegador que el contenido de la respuesta es de tipo PDF
                res.setContentType("application/pdf");
                // Content-Disposition: Cabecera que indica al navegador como manejar la descarga del archivo
                // attachment: Le indica al navegador que debe descargar el archivo en lugar de mostrarlo.
                res.setHeader("Content-Disposition", "attachment; filename=" + subject.getName() + ".pdf");
                pdf.save(res.getOutputStream());
            }
        } else {
            res.sendError(HttpServletResponse.SC_NOT_FOUND, "Error al descargar el archivo");
        }
    }
}
