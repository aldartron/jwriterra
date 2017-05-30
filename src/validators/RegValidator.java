package validators;

import db.Database;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Aldartron on 23.05.2017.
 */

@FacesValidator("RegValidator")
public class RegValidator implements Validator {

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        String sql = "SELECT Login FROM authors";

        try (
                Connection conn = Database.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String login = rs.getString(1);
                    if (login.equals(o.toString())) {
                        FacesMessage message = new FacesMessage("Пользователь с таким именем уже существует");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext context = FacesContext.getCurrentInstance();
                        context.addMessage(uiComponent.getClientId(), message);
                        throw new ValidatorException(message);
                    }
                }
            } catch (Exception ex) {ex.printStackTrace();}
        } catch (Exception ex) {ex.printStackTrace();}

    }
}
