package br.unb.cic.bionimbuz.model;

import java.util.List;

public class User {

    private Long id;

    private String login;

    private String password;

    private String nome;

    private String cpf;

    private String email;

    private String celphone;

    private String securityToken;

    private List<UploadedFileInfo> files;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelphone() {
        return celphone;
    }

    public void setCelphone(String celphone) {
        this.celphone = celphone;
    }

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }

    public List<UploadedFileInfo> getFiles() {
        return files;
    }

    public void setFiles(List<UploadedFileInfo> files) {
        this.files = files;
    }

}
