package com.onrender.todolistjeed.todolistapi.Services;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class EmailValidatorWithDomainCheck {
    public static boolean isValid(String email) {
        String[] parts = email.split("@");
        if (parts.length != 2) {
            return false;
        }

        String domain = parts[1];
        try {
            InetAddress address = InetAddress.getByName(domain);
            return true;
        } catch (UnknownHostException error) {
            return false;
        }
    }
}
