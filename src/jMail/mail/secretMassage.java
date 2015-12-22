package jMail.mail;

import java.io.ByteArrayOutputStream;
import java.security.Key;

import javax.crypto.Cipher;

public class secretMassage {

    // public static void main(String arg[]) {
    // secretMassage sm = new secretMassage();
    // publicAndPrivate rsa = new publicAndPrivate();
    // String str = "董利伟";
    // RSAPublicKey pubKey;
    // try {
    // pubKey = rsa.getRSAPublicKey();
    // RSAPrivateKey priKey = rsa.getRSAPrivateKey();
    // System.out.println(
    // "加密后==" + new String(sm.encrypt(pubKey, "sss".getBytes())));
    // System.out.println("解密后==" + new String(
    // sm.decrypt(priKey, sm.encrypt(pubKey, "sss".getBytes()))));
    //
    // } catch (Exception e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    //
    // }

    /**
     * 解密
     * @param key 解密的密钥
     * @param raw 已经加密的数据
     * @return 解密后的明文
     * @throws Exception
     */
    public byte[] decrypt(Key key, byte[] raw) throws Exception {
        // byte[] raw = massage.getBytes();
        try {
            Cipher cipher = Cipher.getInstance("RSA",
                new org.bouncycastle.jce.provider.BouncyCastleProvider());
            cipher.init(cipher.DECRYPT_MODE, key);
            int blockSize = cipher.getBlockSize();
            ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
            int j = 0;
            while (raw.length - j * blockSize > 0) {
                bout.write(cipher.doFinal(raw, j * blockSize, blockSize));
                j++;
            }
            return bout.toByteArray();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**加密
     * 
     * **/
    public byte[] encrypt(Key key, String massage) throws Exception {
        byte[] data = massage.getBytes();
        try {
            Cipher cipher = Cipher.getInstance("RSA",
                new org.bouncycastle.jce.provider.BouncyCastleProvider());
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // 获得加密块大小，如:加密前数据为128个byte，而key_size=1024 加密块大小为127
            // byte,加密后为128个byte;
            // 因此共有2个加密块，第一个127 byte第二个为1个byte
            int blockSize = cipher.getBlockSize();
            int outputSize = cipher.getOutputSize(data.length);// 获得加密块加密后块大小
            int leavedSize = data.length % blockSize;
            int blocksSize = leavedSize != 0 ? data.length / blockSize + 1
                    : data.length / blockSize;
            byte[] raw = new byte[outputSize * blocksSize];
            int i = 0;
            while (data.length - i * blockSize > 0) {
                if (data.length - i * blockSize > blockSize)
                    cipher.doFinal(data, i * blockSize, blockSize, raw,
                        i * outputSize);
                else
                    cipher.doFinal(data, i * blockSize,
                        data.length - i * blockSize, raw, i * outputSize);
                // 这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把byte[]放到ByteArrayOutputStream中
                // ，而最后doFinal的时候才将所有的byte[]进行加密，可是到了此时加密块大小很可能已经超出了OutputSize所以只好用dofinal方法。
                i++;
            }
            return raw;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
