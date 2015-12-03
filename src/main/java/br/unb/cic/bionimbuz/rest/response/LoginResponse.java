package br.unb.cic.bionimbuz.rest.response;

import br.unb.cic.bionimbuz.model.User;

public class LoginResponse implements ResponseInfo {

    private User user;

    public LoginResponse() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
