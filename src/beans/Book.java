package beans;

import db.Database;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
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
@RequestScoped
public class Book {

    private int id;
    private String genre;
    private String author;
    private String title;
    private byte[] cover;
    private Date pubDate;
    private Date editDate;
    private int chapterCount;

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
                                    "LEFT JOIN images ON books.ID_Cover = images.ID_Image " +
                                    "WHERE books.ID_Book = \"" + id + "\"")
            )
            {
                while (rs.next()) {
                    this.title = rs.getString("Title");
                    this.pubDate = rs.getDate("PubDate");
                    this.editDate = rs.getDate("EditDate");
                    this.chapterCount = rs.getInt("Count");
                    this.author = rs.getString("FirstName") + " " + rs.getString("LastName");
                    this.cover = rs.getBytes("Image");
                    // TODO: manage avatars
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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

    public byte[] getCover() {
        return cover;
    }

    public void setCover(byte[] cover) {
        this.cover = cover;
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

    public int getChapterCount() {
        return chapterCount;
    }

    public void setChapterCount(int chapterCount) {
        this.chapterCount = chapterCount;
    }

}
