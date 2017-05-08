package beans;

import db.Database;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@ManagedBean
@SessionScoped
public class Author {

    public Author() {}

    private long id;
    private String login;
    private String keyword;
    private String firstname;
    private String lastname;
    private Date regdate;
    private byte[] cover;
    private String info;
    private int bookCount;

    public void add() {

        Connection conn = Database.getConnection();
        PreparedStatement ps = null;

        String cryptWord = "";
        try {

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(keyword.getBytes("UTF-8"));
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            cryptWord = bigInt.toString(16);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (conn != null) {
            try {

                String sql = "INSERT INTO authors(Login, Keyword, FirstName, LastName, Info, RegDate) VALUES (?, ?, ?, ?, ?, NOW())";
                ps = conn.prepareStatement(sql);
                ps.setString(1, login);
                ps.setString(2, cryptWord);
                ps.setString(3, firstname);
                ps.setString(4, lastname);
                if (!info.equals(null))
                    ps.setString(5, info);
                int i = ps.executeUpdate();

            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                try {
                    conn.close();
                    ps.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

    }

    public String login() {
        try {

            ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).login(login, keyword);
            return "index";

        } catch (ServletException ex) {
            Logger.getLogger(Author.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage("FUCK" + login + " " + keyword);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage("login_form", message);
        }
        return "login";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getRegdate() {
        return regdate;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

    public byte[] getCover() {
        return cover;
    }

    public void setCover(byte[] cover) {
        this.cover = cover;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getBookCount() {
        return bookCount;
    }

    public void setBookCount(int bookCount) {
        this.bookCount = bookCount;
    }

    @Override
    public String toString() {
        return "Author: " + this.login;
    }
}
