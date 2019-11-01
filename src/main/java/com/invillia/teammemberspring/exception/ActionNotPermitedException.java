package com.invillia.teammemberspring.exception;

public class ActionNotPermitedException extends RuntimeException {

    public ActionNotPermitedException(final String message) {
        super("Ação não permitida.");
    }

}
