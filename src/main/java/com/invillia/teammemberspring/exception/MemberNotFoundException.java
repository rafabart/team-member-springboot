package com.invillia.teammemberspring.exception;

/**
 * @author s2it_pandrade
 * @version : $<br/>
 * : $
 * @since 24/10/19 16:56
 */
public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException(final String message) {
        super("Membro não encontrado, ID: " + message);
    }

}
