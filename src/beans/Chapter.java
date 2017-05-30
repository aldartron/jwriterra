package beans;

import db.Database;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Aldartron on 11.05.2017.
 */
@ManagedBean
@SessionScoped
public class Chapter {
    private int id;
    private int bookId;
    private int number;
    private String title;
    private String content;

    public Chapter() {}

    public static void getChaptersByBookId(int id) {
        try(
                Connection conn = Database.getConnection();
                Statement stmt = conn.createStatement();
        )
        {
            try(
                    ResultSet rs = stmt.executeQuery(
                            "SELECT *, COUNT(ID_Chapter) AS \"Count\" " +
                                 "FROM books LEFT JOIN chapters ON books.ID_Book = chapters.ID_Book " +
                                 "LEFT JOIN authors ON books.ID_Author = authors.ID_Author " +
                                 "WHERE books.ID_Book = \"" + id + "\"")
            )
            {
                while (rs.next()) {
                    rs.getString("Title");
                    rs.getString("FirstName");
                    rs.getString("LastName");
                    // TODO: manage avatars
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void updateInfo() {
        String sql = "UPDATE chapters SET " +
                "Title = \'" + this.title + "\'" +
                ", number = '" + this.number + "\'" +
                ", content = '" + this.content + "\'" +
                "WHERE ID_Chapter = " + id;

        try(
                Connection conn = Database.getConnection();
                Statement stmt = conn.createStatement();
        ) {
            stmt.executeUpdate(sql);
            stmt.executeUpdate("UPDATE books SET EditDate = NOW() WHERE ID_Book = " + this.bookId );
        } catch (Exception ex) { ex.printStackTrace(); }
            }

    public void delete() {
        String sql = "DELETE FROM chapters WHERE ID_Chapter = " + this.id;
        try(
                Connection conn = Database.getConnection();
                Statement stmt = conn.createStatement();
        ) {
            stmt.executeUpdate(sql);
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        String result = "";
        if (this.content != null && !this.content.equals(""))
            result = "<p>" + content + "</p>";
        return result.replaceAll("\n", "<p></p>");
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
