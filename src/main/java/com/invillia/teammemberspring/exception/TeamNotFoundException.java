package com.invillia.teammemberspring.exception;

/**
 * @author s2it_pandrade
 * @version : $<br/>
 * : $
 * @since 24/10/19 16:56
 */
public class TeamNotFoundException extends RuntimeException {

    public TeamNotFoundException(final String message) {
        super("Time não encontrado, ID: " + message);
    }

}
