package br.unb.cic.bionimbuz.web.beans;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import br.unb.cic.bionimbuz.model.User;
import br.unb.cic.bionimbuz.rest.service.RestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@SessionScoped
public class SessionBean implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionBean.class);

    private static final long serialVersionUID = 1L;
    private final RestService restService;
    private User loggedUser = new User();
    private boolean serverStatus;

    private User user = new User();

    // Inicializes session
    public SessionBean() {
        restService = new RestService();
    }

    /**
     * Connect to the server to execute a Login request
     *
     * @return redirectView
     */
    public String login() {
        User responseUser = null;

        try {
            responseUser = restService.login(user);

        } catch (Exception e) {
            LOGGER.error("Server is offline (or HTTP 500 - Internal error)");
            e.printStackTrace();

            return "login?faces-redirect=true&internal_error=true";
        }

        // If user.cpf is not null, user sent to the server was found on database and had its data retrieved back to this client
        if (responseUser.getCpf() != null) {
            loggedUser = responseUser;

            return "success";

            // Login not found 
        } else {
            user = new User();

            return "login?faces-redirect=true&not_found=true";
        }

    }

    /**
     * Invalidates user session and communicates to the server
     *
     * @return
     */
    public String logout() {
        // Invalidates user session
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

        // Send a logout message to the server
        try {
            restService.logout(loggedUser);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Reset user variables
        this.loggedUser = new User();
        this.user = new User();
        
        return "logout";
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public boolean isServerStatus() {
        return serverStatus;
    }

    public void setServerStatus(boolean serverStatus) {
        this.serverStatus = serverStatus;
    }

}
