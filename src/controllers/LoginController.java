package controllers;

import beans.Author;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;


@ManagedBean
@RequestScoped
public class LoginController {

    public LoginController() {}


    public String register(Author author) {
        if (author.add()) {
            author.login();
            return "index";
        }
        return null;
    }

    public String login(Author author) {
        author.login();
        return "profile?faces-redirect=true&includeViewParams=true&id=" + author.getLogin();
    }

    public String exit() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        try {
            request.logout();
        } catch (ServletException se) {
            se.printStackTrace();
        }

        request.getSession().invalidate();
        return "index";
    }
}
