package controllers;

import beans.Chapter;
import db.Database;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Aldartron on 31.05.2017.
 */

@ManagedBean
@SessionScoped
public class ChapterEditController {

    boolean isEdit = false;

    public String startEdit() {
        isEdit = true;
        return "book?faces-redirect=true&includeViewParams=true";
    }

    public String cancelEdit() {
        isEdit = false;

        return "book?faces-redirect=true&includeViewParams=true";
    }

    public String saveEdit(Chapter chapter) {
        isEdit = false;
        chapter.updateInfo();
        return "book?faces-redirect=true&includeViewParams=true";
    }

    public String deleteChapter(Chapter chapter) {
        isEdit = false;
        chapter.delete();
        return "book?faces-redirect=true&includeViewParams=true";
    }

    public void initEdit(Chapter chapter, Chapter src) {
        if (src != null) {
            chapter.setId(src.getId());
            chapter.setBookId(src.getBookId());
            chapter.setTitle(src.getTitle());
            chapter.setNumber(src.getNumber());
            chapter.setContent(src.getContent());
        }

    }

    public String newChapter(int bookId) {
        try(
                Connection conn = Database.getConnection();
                Statement stmt = conn.createStatement();
        ) {
            stmt.executeUpdate(
                    "INSERT INTO chapters (ID_Book, Number, Content) " +
                            "VALUES (" + bookId + ", " +
                            "(SELECT MAX(Number)+1 FROM chapters AS ch WHERE ID_Book = " + bookId + " LIMIT 1) " +
                            ", \'\')"
            );
        } catch (Exception ex) { ex.printStackTrace(); }

        int lastId = 0;

        try (
                Connection conn = Database.getConnection();
                Statement stmt = conn.createStatement();
        ) {
            try( ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID() FROM chapters LIMIT 1")) {
                while (rs.next()) {
                    lastId = rs.getInt(1);
                }
            } catch (Exception ex) {ex.printStackTrace();}
        } catch (Exception ex) {ex.printStackTrace();}

        isEdit = true;
        return "book?faces-redirect=true&id=" + bookId + "&chapter=" + lastId;
    }

    public boolean getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(boolean edit) {
        isEdit = edit;
    }
}
