package br.unb.cic.bionimbuz.model;

import java.util.List;

public class User {

    private long id;
    private String login;
    private String password;
    private String nome;
    private String cpf;
    private String email;
    private String celphone;
    private String securityToken;
    private Long storageUsage;
    private List<FileInfo> files;
    private List<Workflow> workflows;

    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getLogin() {
        return this.login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getNome() {
        return this.nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCpf() {
        return this.cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getCelphone() {
        return this.celphone;
    }
    public void setCelphone(String celphone) {
        this.celphone = celphone;
    }
    public String getSecurityToken() {
        return this.securityToken;
    }
    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }
    public Long getStorageUsage() {
        return this.storageUsage;
    }
    public void setStorageUsage(Long storageUsage) {
        this.storageUsage = storageUsage;
    }
    public void addStorageUsage(Long usage) {
        this.storageUsage += usage;
    }
    public void subtractStorageUsage(Long usage) {
        this.storageUsage -= usage;
    }
    public List<FileInfo> getFiles() {
        return this.files;
    }
    public void setFiles(List<FileInfo> files) {
        this.files = files;
    }
    public List<Workflow> getWorkflows() {
        return this.workflows;
    }
    public void setWorkflows(List<Workflow> workflows) {
        this.workflows = workflows;
    }
}
