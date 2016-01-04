package com.schindig.utils;
/**
 * Created by Agronis on 12/31/15.
 */
public class Venmo {

    private static final String venmoID = "3361";
    private static final String venmoKey = "RY5yVqzdbxbWsq8gjDvcbSq7VRmrjQ9j";
    private static final String venmoTokenURL = "https://api.venmo.com/v1/oauth/access_token";
    private static final String venmoAuthURL = "https://api.venmo.com/v1/oauth/authorize";
    private static final String venmoPaymentURL = "https://api.venmo.com/v1/payments";
    private static final String venmoSandbox = "https://sandbox-api.venmo.com/v1/payments";
    private static final String frontEnd = "https://api.venmo.com/v1/oauth/authorize?client_id=3361&scope=make_payments%20access_profile&response_type=code";

    public static String getVenmoID() {

        return venmoID;
    }
    public static String getVenmoKey() {

        return venmoKey;
    }
    public static String getVenmoAuthURL() {

        return venmoAuthURL;
    }
    public static String getVenmoTokenURL() {

        return venmoTokenURL;
    }
    public static String getVenmoPaymentURL() {

        return venmoPaymentURL;
    }
    public static String getFrontEnd() {

        return frontEnd;
    }
    public static String getVenmoSandbox() {

        return venmoSandbox;
    }

}
