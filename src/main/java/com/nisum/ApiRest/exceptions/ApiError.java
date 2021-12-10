package com.nisum.ApiRest.exceptions;

public class ApiError {

    private String mensaje;

    public ApiError(String message){
        this.mensaje = message;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
