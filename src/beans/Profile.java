package beans;

import db.Database;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Aldartron on 23.04.2017.
 */

@ManagedBean
@RequestScoped
public class Profile implements Serializable{

    private int id;
    private String login;
    private String name;
    private String surename;
    private String info;
    private Date regdate;
    private byte[] avatar;

    private ArrayList<Book> bookList;

    public Profile() {}

    public void onload() {
        fillProfile();
    }

    private void fillProfile() {
        Connection conn = Database.getConnection();
        Statement stmt = null;
        ResultSet rs = null;

        if (conn != null)
            try {

                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM Authors WHERE Login = \"" + login + "\"");

                while (rs.next()) {
                    this.name = rs.getString("FirstName");
                    this.surename = rs.getString("LastName");
                    this.login = rs.getString("Login");
                    this.info = rs.getString("Info");
                    this.regdate = rs.getDate("RegDate");
                    this.avatar = null; //

                    // TODO: manage avatars
                }

                System.out.println("test" + login);

            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                try {
                    if (stmt != null) {
                        stmt.close();
                    }
                    if (rs != null) {
                        rs.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

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
