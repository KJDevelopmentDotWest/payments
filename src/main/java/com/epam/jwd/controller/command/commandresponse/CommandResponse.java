package com.epam.jwd.controller.command.commandresponse;

import java.util.Objects;

public class CommandResponse {
    private String URL;
    private Boolean isRedirect;

    public CommandResponse(String URL, Boolean isRedirect) {
        this.URL = URL;
        this.isRedirect = isRedirect;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public Boolean getRedirect() {
        return isRedirect;
    }

    public void setRedirect(Boolean redirect) {
        isRedirect = redirect;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommandResponse that = (CommandResponse) o;
        return Objects.equals(URL, that.URL) && Objects.equals(isRedirect, that.isRedirect);
    }

    @Override
    public int hashCode() {
        return Objects.hash(URL, isRedirect);
    }

    @Override
    public String toString() {
        return "CommandResponse{" +
                "URL='" + URL + '\'' +
                ", isRedirect=" + isRedirect +
                '}';
    }
}
