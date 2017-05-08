package controllers;

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
 * Created by Aldartron on 06.05.2017.
 */
@ManagedBean
@RequestScoped
public class ProfileListController {

    private int AUTHORS_ON_PAGE = 20;
    private String searchString;
    private ArrayList<Profile> profileList;

    public ProfileListController() {
        fillProfilesAll();
    }

    public void fillProfilesAll() {
        String sql = "SELECT *, COUNT(ID_Book) AS \"Count\" FROM authors LEFT JOIN books ON books.ID_Author = authors.ID_Author GROUP BY authors.ID_Author";
        fillProfilesBySQL(sql);
    }

    public ArrayList<Profile> getProfileList() {
        return profileList;
    }

    public String fillProfileListBySearch() throws IOException{
        if (searchString.trim().equals("") || searchString == null) {
            searchString = null;
            fillProfilesAll();
            return "books";
        }
        searchString = searchString.trim();
        String sql =
                "SELECT *, COUNT(ID_Book) AS \"Count\" FROM authors LEFT JOIN books ON books.ID_Author = authors.ID_Author \n" +
                "WHERE LOWER(Login) LIKE \"%" + searchString.toLowerCase() + "%\" OR " +
                "LOWER(FirstName) LIKE \"%" + searchString.toLowerCase() + "%\" OR " +
                "LOWER(LastName) LIKE \"%" + searchString.toLowerCase() +"%\" OR " +
                "LOWER(CONCAT(FirstName, \" \", LastName)) LIKE \"%" + searchString.toLowerCase() + "%\" OR " +
                "LOWER(CONCAT(LastName, \" \", FirstName)) LIKE \"%" + searchString.toLowerCase() + "%\"\n" +
                "GROUP BY authors.ID_Author";
        fillProfilesBySQL(sql);

        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage("Результаты поиска \"" + searchString +"\"" );
        context.addMessage("search_form", message);

        searchString = null;

        return "books";
    }

    private void fillProfilesBySQL(String sql) {

        profileList = new ArrayList<>();

        try (
                Connection conn = Database.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            try (
                    ResultSet rs = ps.executeQuery()
            ) {

                while (rs.next()) {
                    Profile p = new Profile();
                    p.setLogin(rs.getString("Login"));
                    p.setSurename(rs.getString("FirstName"));
                    p.setName(rs.getString("LastName"));
                    p.setInfo(rs.getString("Info"));
                    // TODO: Avatars
                    p.setRegdate(rs.getDate("RegDate"));
                    p.setId(rs.getInt("ID_Author"));
                    p.setBookCount(rs.getInt("Count"));
                    profileList.add(p);
                }
            } catch (Exception ex) {ex.printStackTrace();}
        } catch (Exception ex) {ex.printStackTrace();}

    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
}
