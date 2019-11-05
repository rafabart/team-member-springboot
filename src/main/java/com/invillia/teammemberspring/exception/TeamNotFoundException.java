package com.invillia.teammemberspring.exception;

public class TeamNotFoundException extends RuntimeException {

    public TeamNotFoundException(final String message) {
        super("Time não encontrado, " + message);
    }

}
