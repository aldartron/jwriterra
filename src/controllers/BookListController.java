package controllers;

import beans.Book;
import beans.Profile;
import db.Database;

import javax.faces.bean.ManagedBean;
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

@ManagedBean(eager=true)
@SessionScoped
public class BookListController {

    private int BOOKS_ON_PAGE = 20;
    private int currentPage = 1;
    private Profile currentProfile;

    private ArrayList<Book> bookList;

    public BookListController() {}

    public ArrayList<Book> getBookList() {
        return bookList;
    }

    public ArrayList<Book> getBookList(Profile profile) {

        currentProfile = profile;

        bookList = new ArrayList<>();
        String sql = "SELECT b.ID_Book, g.Name Жанр, a.FirstName, a.LastName, b.Title, b.PubDate, b.EditDate " +
                "FROM books b " +
                "LEFT JOIN genres g ON b.ID_Genre = g.ID_Genre " +
                "LEFT JOIN authors a ON b.ID_Author = a.ID_Author " +
                "WHERE a.Login = \"" + profile.getLogin() + "\" " +
                "ORDER BY EditDate " +
                "LIMIT " + Integer.toString((currentPage-1) * BOOKS_ON_PAGE) + ", " + Integer.toString(BOOKS_ON_PAGE);

        try (
                Connection conn = Database.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Book book = new Book();
                    book.setId(rs.getInt(1));
                    book.setGenre(rs.getString(2));
                    book.setAuthor(rs.getString(3) + " " + rs.getString(4));
                    book.setTitle(rs.getString(5));
                    book.setPubDate(rs.getDate(6));
                    book.setEditDate(rs.getDate(7));

                    bookList.add(book);
                }

            } catch (Exception ex) {ex.printStackTrace();}

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return bookList;
    }

    public int getBOOKS_ON_PAGE() {
        return BOOKS_ON_PAGE;
    }

    public ArrayList<Integer> getPages() {

        ArrayList<Integer> pagesList = new ArrayList<>();

        for (int i = 1; i < currentProfile.getBookCount(); i += BOOKS_ON_PAGE) {
            pagesList.add(i / BOOKS_ON_PAGE + 1);
        }

        return pagesList;
    }


}
