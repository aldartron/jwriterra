package controllers;

import beans.Book;
import db.Database;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@ManagedBean
@SessionScoped
public class BookEditController {

    private boolean isEdit;

    public BookEditController() {}

    public String startEdit() {
        isEdit = true;
        return "book?faces-redirect=true&includeViewParams=true";
    }

    public String cancelEdit() {
        isEdit = false;
        return "book?faces-redirect=true&includeViewParams=true";
    }

    public String saveEdit(Book book) {
        isEdit = false;
        book.updateInfo();
        return "book?faces-redirect=true&includeViewParams=true";
    }

    public String deleteBook(Book book) {
        isEdit = false;
        book.delete();
        return "books";
    }

    public void initEdit(Book book, Book src) {
        book.setId(src.getId());
        book.setTitle(src.getTitle());
        book.setGenre(src.getGenre());
    }

    public String newBook() {
        String sql1 = "INSERT INTO books (ID_Author, PubDate) VALUES ((SELECT ID_Author FROM authors WHERE Login = \"" + FacesContext.getCurrentInstance().getExternalContext().getRemoteUser() + "\"), NOW())";
        int lastId = 0;
        try(
                Connection conn = Database.getConnection();
                Statement stmt = conn.createStatement();

        ) {
            stmt.executeUpdate(sql1);
        } catch (Exception ex) { ex.printStackTrace(); }

        try (
                Connection conn = Database.getConnection();
                Statement stmt = conn.createStatement();
        ) {
           try( ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID() FROM authors LIMIT 1")) {
               while (rs.next()) {
                   lastId = rs.getInt(1);
               }
           } catch (Exception ex) {ex.printStackTrace();}
        } catch (Exception ex) {ex.printStackTrace();}

        isEdit = true;
        return "book?faces-redirect=true&includeViewParams=true&id=" + lastId;
    }

    public boolean getIsEdit() { return isEdit; }

    public void setIsEdit(boolean edit) {
        isEdit = edit;
    }
}
