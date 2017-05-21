package servlets;

import db.Database;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Aldartron on 12.05.2017.
 */
@WebServlet("/ShowImage/*")
public class ShowImage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("image/jpeg");

        int id = Integer.valueOf(request.getParameter("id"));
        byte[] image = null;

        try (
                Connection conn = Database.getConnection();
                Statement stmt = conn.createStatement();
                OutputStream out = response.getOutputStream();
        ) {
            try (
                    ResultSet rs = stmt.executeQuery("SELECT Image FROM images " +
                            "WHERE ID_Image = " + id);
            ) {
                while (rs.next()) {
                    image = rs.getBytes("Image");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            response.setContentLength(image.length);
            out.write(image);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
