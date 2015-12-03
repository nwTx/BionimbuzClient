package br.unb.cic.bionimbuz.rest.response;

public class LogoutResponse implements ResponseInfo {

    private boolean logoutSuccess;

    public boolean isLogoutSuccess() {
        return logoutSuccess;
    }

    public void setLogoutSuccess(boolean logoutSuccess) {
        this.logoutSuccess = logoutSuccess;
    }

}
