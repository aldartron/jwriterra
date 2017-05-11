package controllers;

import beans.Book;
import beans.Profile;
import db.Database;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by Aldartron on 25.04.2017.
 */

@ManagedBean
@RequestScoped
public class BookListController {

    private int BOOKS_ON_PAGE = 100;
    private int currentPage = 1;
    private Profile currentProfile;
    private String searchString;
    private String currentGenre;

    private ArrayList<Book> bookList;

    public BookListController() {
        fillBooksAll();
    }

    public void fillBooksBySQL(String sql) {
        sql += " ORDER BY EditDate DESC";
        sql += " LIMIT " + Integer.toString((currentPage-1) * BOOKS_ON_PAGE) + ", " + Integer.toString(BOOKS_ON_PAGE);

        bookList = new ArrayList<>();
        try (
                Connection conn = Database.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Book book = new Book();
                    book.setId(rs.getInt("ID_Book"));
                    book.setGenre(rs.getString("Name"));
                    book.setAuthor(rs.getString("FirstName") + " " + rs.getString("LastName"));
                    book.setTitle(rs.getString("Title"));
                    book.setPubDate(rs.getDate("PubDate"));
                    book.setEditDate(rs.getDate("EditDate"));
                    bookList.add(book);
                }
            } catch (Exception ex) {ex.printStackTrace();}
        } catch (Exception ex) {ex.printStackTrace();}

        currentGenre = null;
    }

    public ArrayList<Book> getBooksByProfile(Profile profile) {
        currentProfile = profile;
        String sql =
                "SELECT b.ID_Book, g.Name, a.FirstName, a.LastName, b.Title, b.PubDate, b.EditDate " +
                "FROM books b " +
                "LEFT JOIN genres g ON b.ID_Genre = g.ID_Genre " +
                "LEFT JOIN authors a ON b.ID_Author = a.ID_Author " +
                "WHERE a.Login = \"" + profile.getLogin() + "\" ";
        fillBooksBySQL(sql);
        return bookList;
    }

    public void fillBooksAll() {
        String sql =
                "SELECT * FROM books b " +
                "LEFT JOIN genres g ON b.ID_Genre = g.ID_Genre " +
                "LEFT JOIN authors a ON b.ID_Author = a.ID_Author ";
        fillBooksBySQL(sql);
    }

    public String fillBooksByGenre(String genreName) {
        String sql =
                "SELECT * FROM books b " +
                "LEFT JOIN genres g ON b.ID_Genre = g.ID_Genre " +
                "LEFT JOIN authors a ON b.ID_Author = a.ID_Author " +
                "WHERE g.Name = \"" + genreName + "\"";

        fillBooksBySQL(sql);
        currentGenre = genreName;
        return "books";
    }

    public String fillBooksBySearch() throws IOException {
        if (searchString.trim().equals("") || searchString == null) {
            searchString = null;
            fillBooksAll();
            return "books";
        }
        searchString = searchString.trim();
        String sql =
                "SELECT * FROM books b " +
                "LEFT JOIN genres g ON b.ID_Genre = g.ID_Genre " +
                "LEFT JOIN authors a ON b.ID_Author = a.ID_Author " +
                 "WHERE LOWER(Title) LIKE \"%" + searchString.toLowerCase() + "%\" OR " +
                 "LOWER(CONCAT(Title, \" \", Name)) LIKE \"%" + searchString.toLowerCase() + "%\" OR " +
                 "LOWER(CONCAT(Name, \" \", Title)) LIKE \"%" + searchString.toLowerCase() + "%\"\n";

        fillBooksBySQL(sql);

        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage("Результаты поиска \"" + searchString +"\"" );
        context.addMessage("search_form", message);

        return "books";
    }

    public ArrayList<Integer> getPages() {
        ArrayList<Integer> pagesList = new ArrayList<>();
        for (int i = 1; i < currentProfile.getBookCount(); i += BOOKS_ON_PAGE) {
            pagesList.add(i / BOOKS_ON_PAGE + 1);
        }
        return pagesList;
    }

    public ArrayList<Book> getBookList() {
        return bookList;
    }

    public void setBookList(ArrayList<Book> bookList) {
        this.bookList = bookList;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String getCurrentGenre() {
        return currentGenre;
    }

    public void setCurrentGenre(String currentGenre) {
        this.currentGenre = currentGenre;
    }
}

