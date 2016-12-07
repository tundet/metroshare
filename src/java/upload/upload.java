package upload;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import controller.MetroShareSB;
import java.nio.file.Files;
import javax.ejb.EJB;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Calendar;
import model.Media;
import model.User;

/**
 * Image uploader servlet.
 *
 * This servlet is responsible for handling the uploading of images. It
 * processes a post request and saves the image received within the request in
 * the uploads directory and inserts information about the uploaded image in the
 * database.
 */
@WebServlet(urlPatterns = {"/upload"})
@MultipartConfig(location = "/tmp")
public class upload extends HttpServlet {

    @EJB
    private MetroShareSB mssb;

    /**
     * Show unauthorized access message.
     *
     * Show a message if a user tries to access this upload servlet directly
     * using a GET request
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Unauthorized</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<p>You are not supposed to access this file directly.</p>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     * Upload an image and insert information in the database.
     *
     * Upload the image the user submitted and insert information about it in
     * the database to make it accessible using the web UI.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fileName = getFilename(request.getPart("file"));

        // Get the file extension of the image.
        int lastIndexOfDot = fileName.lastIndexOf(".");
        fileName = mssb.getNextMediaId() + fileName.substring(lastIndexOfDot);

        User user = mssb.readUserBySessionID(request.getParameter("sessionid"));

        // Set upload directory to the "/uploads" directory.
        String uploadDirectoryPath = getServletContext().getRealPath("/uploads") + File.separator + user.getId() + File.separator;

        // Write the file to a temporary location.
        request.getPart("file").write(fileName);

        Path file = FileSystems.getDefault().getPath("/tmp" + File.separator + fileName);

        // Move the file to the "/uploads" directory.
        Path destinationFile = FileSystems.getDefault().getPath(uploadDirectoryPath + fileName);
        Files.move(file, destinationFile, StandardCopyOption.REPLACE_EXISTING);

        Media image = new Media();
        image.setUserId(user);
        image.setDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        image.setMediaLocation("uploads/" + user.getId() + "/" + fileName);
        image.setTitle(request.getParameter("title"));

        if (request.getParameter("nsfw") == null) {
            image.setNsfw(false);
        } else {
            image.setNsfw(true);
        }

        mssb.insert(image);
    }

    /**
     * Get the file name of the uploaded image.
     *
     * Get the full file name, including file extension, of the uploaded image.
     *
     * Also, return a status message as JSON when the file has been uploaded.
     *
     * @param part The file whose name to get
     * @return The file name as a string
     */
    private String getFilename(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return "{\"result\": \"success\"}";
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
