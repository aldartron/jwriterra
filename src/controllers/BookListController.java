package controllers;

import beans.Book;
import db.Database;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
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

    public BookListController() {}

    public ArrayList<Book> getLastEditedBooks(String login, int count) {

        ArrayList<Book> lastEditedBooks = new ArrayList<>();
        String sql = "SELECT b.ID_Book, g.Name Жанр, a.FirstName, a.LastName, b.Title, b.PubDate, b.EditDate " +
                "FROM books b " +
                "LEFT JOIN genres g ON b.ID_Genre = g.ID_Genre " +
                "LEFT JOIN authors a ON b.ID_Author = a.ID_Author " +
                "WHERE a.Login = \"" + login + "\" " +
                "ORDER BY EditDate " +
                "LIMIT " + Integer.toString(count);

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
                    lastEditedBooks.add(book);
                }

            } catch (Exception ex) {ex.printStackTrace();}

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return lastEditedBooks;
    }

}
