package controllers;


import beans.Genre;
import db.Database;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

@ManagedBean(eager = true)
@ApplicationScoped
public class GenreController {

    private ArrayList<Genre> genreList;

    public GenreController() { fillGenresAll(); }

    private void fillGenresAll() {

        genreList = new ArrayList<>();

        try(
                Connection conn = Database.getConnection();
                Statement stmt = conn.createStatement();
                )
        {
            try(ResultSet rs = stmt.executeQuery("SELECT * FROM genres ORDER BY ID_Genre")) {
                while (rs.next()) {
                    Genre genre = new Genre();
                    genre.setName(rs.getString("Name"));
                    genre.setId(rs.getLong("ID_Genre"));
                    genre.setDesc(rs.getString("Descript"));
                    genreList.add(genre);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<Genre> getGenreList() {
        return genreList;
    }
}
