package controllers;

import beans.Book;
import beans.Chapter;
import db.Database;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Aldartron on 12.05.2017.
 */
@ManagedBean
@SessionScoped
public class BookReader {

    private Book currentBook;
    private Chapter currentChapter;
    private ArrayList<Chapter> currentChapterList;
    private int bookId; // ID of current book
    private int chapterId; // ID of current chapter

    public void initBook() {
        if (currentBook == null || currentBook.getId() != bookId) {
            currentBook = new Book();
            currentBook.setId(bookId);
            currentBook.fillBook();
            fillChapterList();
        }
        if (!currentChapterList.isEmpty()) {
            currentChapter = getChapterByID(chapterId);
        } else currentChapter = null;
    }

    private void fillChapterList() {
        ArrayList<Chapter> result = new ArrayList<>();
        try(
                Connection conn = Database.getConnection();
                Statement stmt = conn.createStatement();
        ) {
            try(
                    ResultSet rs = stmt.executeQuery(
                            "SELECT *  " +
                                    "FROM chapters " +
                                    "WHERE ID_Book = \"" + bookId + "\" " +
                                    "ORDER BY Number")
            ) {
                while (rs.next()) {
                    Chapter chapter = new Chapter();
                    chapter.setId(rs.getInt("ID_Chapter"));
                    chapter.setBookId(rs.getInt("ID_Book"));
                    chapter.setTitle(rs.getString("Title"));
                    chapter.setNumber(rs.getInt("Number"));
                    chapter.setContent(rs.getString("Content"));
                    result.add(chapter);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        currentChapterList = result;
    }

    private Chapter getChapterByID(int id) { // Возвращает главу из листа по ID, либо первую, если ID не подходит
        for (Chapter ch : currentChapterList) {
            if (ch.getId() == id) {
                return ch;
            }
        }
        return currentChapterList.get(0);
    }

    public Book getCurrentBook() {
        return currentBook;
    }

    public void setCurrentBook(Book currentBook) {
        this.currentBook = currentBook;
    }

    public ArrayList<Chapter> getCurrentChapterList() {
        return currentChapterList;
    }

    public void setCurrentChapterList(ArrayList<Chapter> currentChapterList) {
        this.currentChapterList = currentChapterList;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int id) {
        this.bookId = id;
    }

    public Chapter getCurrentChapter() {
        return currentChapter;
    }

    public void setCurrentChapter(Chapter currentChapter) {
        this.currentChapter = currentChapter;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }
}
