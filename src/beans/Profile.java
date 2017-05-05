package beans;

import db.Database;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Aldartron on 23.04.2017.
 */

@ManagedBean
@RequestScoped
public class Profile implements Serializable{

    private int id;
    @ManagedProperty(value = "#{param.id}")
    private String login;
    private String name;
    private String surename;
    private String info;
    private Date regdate;
    private byte[] avatar;
    private int bookCount;

    private ArrayList<Book> bookList;

    public Profile() {}

    public void onload() {
        fillProfile();
    }

    private void fillProfile() {

        try(
                Connection conn = Database.getConnection();
                Statement stmt = conn.createStatement();
                Statement stmt1 = conn.createStatement();
            )
        {

            try(
                    ResultSet rs = stmt.executeQuery("SELECT * FROM Authors WHERE Login = \"" + login + "\"");
                    ResultSet rs1 =stmt1.executeQuery("SELECT COUNT(ID_Book) AS \"Count\" FROM books WHERE ID_Author = " + id)
                )
            {
                while (rs.next()) {
                    this.id = rs.getInt("ID_Author");
                    this.name = rs.getString("FirstName");
                    this.surename = rs.getString("LastName");
                    this.login = rs.getString("Login");
                    this.info = rs.getString("Info");
                    this.regdate = rs.getDate("RegDate");
                    this.avatar = null; //
                    // TODO: manage avatars
                }

                while (rs1.next()) {
                    this.bookCount = rs1.getInt("Count");
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public int getBookCount() {
        return bookCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurename() {
        return surename;
    }

    public void setSurename(String surename) {
        this.surename = surename;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getRegdate() {
        SimpleDateFormat ddmmyyyy = new SimpleDateFormat("dd.MM.yyyy");
        return ddmmyyyy.format(regdate);
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public ArrayList<Book> getBookList() {
        return bookList;
    }

    public void setBookList(ArrayList<Book> bookList) {
        this.bookList = bookList;
    }
}
