package br.ufg.com.dedoduro.web;

public class WebError extends Error {

    private String request;

    public WebError(String message, String request) {
        super(message);
        this.request = request;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }
}
