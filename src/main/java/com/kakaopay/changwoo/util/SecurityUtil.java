package com.kakaopay.changwoo.util;
import java.security.MessageDigest;
/**
 * Created by changwooj111@gmail.com on 2019-08-19
 */
public class SecurityUtil {
    /*private static volatile SecurityUtil INSTANCE;

    final static String secretKey   = "kakaopaychangwoojangchangwoojang"; //32bit
    static String IV                = "namechangwoojang"; //16bit

    public static SecurityUtil getInstance(){
        if(INSTANCE==null){
            synchronized(SecurityUtil.class){
                if(INSTANCE==null)
                    INSTANCE=new SecurityUtil();
            }
        }
        return INSTANCE;
    }

    private SecurityUtil(){
        IV = secretKey.substring(0,16);
    }

    public static String AES_Encode(String str) throws NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte[] keyData = secretKey.getBytes();
        SecretKey secureKey = new SecretKeySpec(keyData, "AES");
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.ENCRYPT_MODE, secureKey, new IvParameterSpec(IV.getBytes()));
        byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
        String enStr = new String(Base64.encodeBase64(encrypted));

        return enStr;
    }

    public String AES_Decode(String str) throws UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte[] keyData = secretKey.getBytes();
        SecretKey secureKey = new SecretKeySpec(keyData, "AES");
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.DECRYPT_MODE, secureKey, new IvParameterSpec(IV.getBytes("UTF-8")));
        byte[] byteStr = Base64.decodeBase64(str.getBytes());
        return new String(c.doFinal(byteStr),"UTF-8");
    }
    */
    public static String encrypt(String planText) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(planText.getBytes());
            byte byteData[] = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                String hex = Integer.toHexString(0xff & byteData[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
