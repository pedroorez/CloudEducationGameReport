package com.ESIa.DAO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class hashgen {

    public String makeRandomSHA1()
            throws NoSuchAlgorithmException
        {
            Random randomGenerator = new Random();
            String input ;
            input = Integer.toString(randomGenerator.nextInt(691873654));
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.reset();
            byte[] buffer = input.getBytes();
            md.update(buffer);
            byte[] digest = md.digest();

            String hexStr = "";
            for (int i = 0; i < digest.length; i++) {
                hexStr +=  Integer.toString( ( digest[i] & 0xff ) + 0x100, 16).substring( 1 );
            }
            return hexStr;
        }
}