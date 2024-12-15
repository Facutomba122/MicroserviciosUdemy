package org.facupinazo.springcloud.mvsc.users.utils;

public class MyExceptions extends RuntimeException {

    public static class EmailAlreadyExistsException extends RuntimeException {
        public EmailAlreadyExistsException() {
            super("El email ya existe en el sistema.");
        }

        public EmailAlreadyExistsException(String message) {
            super(message);
        }
    }
}
