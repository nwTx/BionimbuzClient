package br.unb.cic.bionimbuz.rest.response;

public class SignUpResponse implements ResponseInfo {

    private boolean added;

    public boolean isAdded() {
        return added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }

}
