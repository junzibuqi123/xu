package jMail.mail;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

public class publicAndPrivate {
    private KeyPair keyPair = null;

    public publicAndPrivate() {
        try {
            this.keyPair = this.generateKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成密钥对
     * @return KeyPair
     * @throws Exception
     */
    private KeyPair generateKeyPair() throws Exception {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA",
                new org.bouncycastle.jce.provider.BouncyCastleProvider());
            // 这个值关系到块加密的大小，可以更改，但是不要太大，否则效率会低
            final int KEY_SIZE = 1024;
            keyPairGen.initialize(KEY_SIZE, new SecureRandom());
            KeyPair keyPair = keyPairGen.genKeyPair();
            return keyPair;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * 生成公钥
     * @param modulus
     * @param publicExponent
     * @return RSAPublicKey
     * @throws Exception
     */
    private RSAPublicKey generateRSAPublicKey(byte[] modulus,
            byte[] publicExponent) throws Exception {

        KeyFactory keyFac = null;
        try {
            keyFac = KeyFactory.getInstance("RSA",
                new org.bouncycastle.jce.provider.BouncyCastleProvider());
        } catch (NoSuchAlgorithmException ex) {
            throw new Exception(ex.getMessage());
        }
        RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(
            new BigInteger(modulus), new BigInteger(publicExponent));
        try {
            return (RSAPublicKey) keyFac.generatePublic(pubKeySpec);
        } catch (InvalidKeySpecException ex) {
            throw new Exception(ex.getMessage());
        }

    }

    /**
    * 生成私钥
    * @param modulus
    * @param privateExponent
    * @return RSAPrivateKey
    * @throws Exception
    */
    private RSAPrivateKey generateRSAPrivateKey(byte[] modulus,
            byte[] privateExponent) throws Exception {
        KeyFactory keyFac = null;
        try {
            keyFac = KeyFactory.getInstance("RSA",
                new org.bouncycastle.jce.provider.BouncyCastleProvider());
        } catch (NoSuchAlgorithmException ex) {
            throw new Exception(ex.getMessage());
        }
        RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(
            new BigInteger(modulus), new BigInteger(privateExponent));
        try {
            return (RSAPrivateKey) keyFac.generatePrivate(priKeySpec);
        } catch (InvalidKeySpecException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    /**
     * 返回公钥
     * @return
     * @throws Exception 
     */
    public RSAPublicKey getRSAPublicKey() throws Exception {

        // 获取公钥
        RSAPublicKey pubKey = (RSAPublicKey) keyPair.getPublic();
        // 获取公钥系数(字节数组形式)
        byte[] pubModBytes = pubKey.getModulus().toByteArray();
        // 返回公钥公用指数(字节数组形式)
        byte[] pubPubExpBytes = pubKey.getPublicExponent().toByteArray();
        // 生成公钥
        RSAPublicKey recoveryPubKey = this.generateRSAPublicKey(pubModBytes,
            pubPubExpBytes);
        return recoveryPubKey;
    }

    /**
     * 获取私钥
     * @return
     * @throws Exception 
     */
    public RSAPrivateKey getRSAPrivateKey() throws Exception {

        // 获取私钥
        RSAPrivateKey priKey = (RSAPrivateKey) keyPair.getPrivate();
        // 返回私钥系数(字节数组形式)
        byte[] priModBytes = priKey.getModulus().toByteArray();
        // 返回私钥专用指数(字节数组形式)
        byte[] priPriExpBytes = priKey.getPrivateExponent().toByteArray();
        // 生成私钥
        RSAPrivateKey recoveryPriKey = this.generateRSAPrivateKey(priModBytes,
            priPriExpBytes);
        return recoveryPriKey;
    }

}
