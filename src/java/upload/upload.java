package upload;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
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
import javax.servlet.ServletContext;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Calendar;
import model.Media;
import model.User;

@WebServlet(urlPatterns = {"/upload"})
@MultipartConfig(location = "/tmp")
public class upload extends HttpServlet {
    
    @EJB
    private MetroShareSB mssb;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet upload</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet upload at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
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
        int lastIndexOfDot = fileName.lastIndexOf(".");
        fileName = mssb.getNextMediaId() + fileName.substring(lastIndexOfDot);
        
        User user = mssb.readUserBySessionID(request.getParameter("sessionid"));
        
        String uploadDirectoryPath = getServletContext().getRealPath("/uploads") + File.separator + user.getId() + File.separator;
                
        request.getPart("file").write(fileName);
        
        Path file = FileSystems.getDefault().getPath("/tmp" + File.separator + fileName);

        Path destinationFile = FileSystems.getDefault().getPath(uploadDirectoryPath + fileName);
        Files.move(file, destinationFile, StandardCopyOption.REPLACE_EXISTING);
        
        System.out.println("NSFW: " + request.getParameter("nsfw"));
        
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

    private String getFilename(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return "{\"result:\" \"success\"}";
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
