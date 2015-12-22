package jMail.mail;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class testscr {
    public static void main(String arg[]) {
        secretMassage sm = new secretMassage();
        publicAndPrivate pp = new publicAndPrivate();
        try {
            RSAPublicKey pubKey = pp.getRSAPublicKey();
            RSAPrivateKey priKey = pp.getRSAPrivateKey();
            System.out.println(pubKey);
            byte[] st = sm.encrypt(pubKey, "xujialin33");
            System.out.println(st);
            System.out.println("解密后==" + new String(sm.decrypt(priKey, st)));

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
