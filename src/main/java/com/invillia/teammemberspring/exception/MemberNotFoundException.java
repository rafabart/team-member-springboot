package com.invillia.teammemberspring.exception;

public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException(final String message) {
        super("Membro não encontrado, ID: " + message);
    }

}
