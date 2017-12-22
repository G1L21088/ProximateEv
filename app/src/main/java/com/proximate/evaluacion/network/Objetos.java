package com.proximate.evaluacion.network;

/**
 * Created by G1L21088 on 21/12/2017.
 */

public class Objetos {

    public static class Login {
        public final boolean success;
        public final boolean error;
        public final String message;
        public final String token;
        public final int id;

        public Login(boolean success, boolean error, String message, String token, int id) {
            this.success = success;
            this.error = error;
            this.message = message;
            this.token = token;
            this.id = id;
        }
    }
}
