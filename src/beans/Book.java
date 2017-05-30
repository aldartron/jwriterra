package beans;

import db.Database;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Aldartron on 23.04.2017.
 */

@ManagedBean
@SessionScoped
public class Book {

    private int id;
    private String genre;
    private String author;
    private String authorLogin;
    private String title;
    private int coverID;
    private Date pubDate;
    private Date editDate;

    public Book() {}

    public void fillBook() {
        try(
                Connection conn = Database.getConnection();
                Statement stmt = conn.createStatement();
        )
        {
            try(
                    ResultSet rs = stmt.executeQuery(
                            "SELECT *, COUNT(ID_Chapter) AS \"Count\" " +
                                    "FROM books " +
                                    "LEFT JOIN chapters ON books.ID_Book = chapters.ID_Book " +
                                    "LEFT JOIN authors ON books.ID_Author = authors.ID_Author " +
                                    "LEFT JOIN genres ON books.ID_Genre = genres.ID_Genre " +
                                    "WHERE books.ID_Book = \"" + id + "\"")
            )
            {
                while (rs.next()) {
                    this.id = rs.getInt("ID_Book");
                    this.title = rs.getString("Title");
                    this.pubDate = rs.getDate("PubDate");
                    this.editDate = rs.getDate("EditDate");
                    this.authorLogin = rs.getString("Login");
                    this.author = rs.getString("FirstName") + " " + rs.getString("LastName");
                    this.coverID = rs.getInt("ID_Cover");
                    this.genre = rs.getString("Name");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void updateInfo() {
        String sql = "UPDATE books SET " +
                "Title = \'" + this.title + "\'" +
                ", ID_Genre = (SELECT ID_Genre FROM genres WHERE genres.Name = \'" + this.genre + "\' LIMIT 1) " +
                ", EditDate = NOW() " +
                "WHERE ID_Book = " + this.id;

        try(
                Connection conn = Database.getConnection();
                Statement stmt = conn.createStatement();
        ) {
            stmt.executeUpdate(sql);
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    public void delete() {
        String sql = "DELETE FROM books WHERE ID_Book = " + this.id;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCoverID() {
        return coverID;
    }

    public void setCoverID(int coverID) {
        this.coverID = coverID;
    }

    public String getPubDate() {
        if (pubDate != null) {
            SimpleDateFormat ddmmyyyy = new SimpleDateFormat("dd.MM.yyyy");
            return ddmmyyyy.format(pubDate);
        } else return null;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public String getEditDate() {
        if (editDate != null) {
            SimpleDateFormat ddmmyyyy = new SimpleDateFormat("dd.MM.yyyy");
            return ddmmyyyy.format(editDate);
        } else return null;
    }

    public void setEditDate(Date editDate) {
        this.editDate = editDate;
    }

    public String getAuthorLogin() {
        return authorLogin;
    }

    public void setAuthorLogin(String authorLogin) {
        this.authorLogin = authorLogin;
    }
}
