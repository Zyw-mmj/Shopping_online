package com.example.webstore.Util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordEncryption {
    private static final int SALT_LENGTH = 16;

    public static void main(String[] args) {
        String password = "myPassword"; // 假设这是用户的原始密码
        String salt = generateSalt();
        String hashedPassword = hashPassword(password, salt);

        // 将盐和加密后的密码一起存储在数据库中
        System.out.println("盐值：" + salt);
        System.out.println("加密后的密码：" + hashedPassword+salt);
        String s=hashedPassword+salt;
        System.out.println(s.substring(Math.max(s.length() - 32, 0)));
        String x="83fa6b922963130fbcc026c81f42f126465d2c6f939abffb8d5304f7d001b399";
        System.out.println(x.length());

//        // 模拟验证密码过程
//        String userInputPassword = "myPassword"; // 假设这是用户输入的密码
//        String hashedUserInputPassword = hashPassword(userInputPassword, salt);
//        if (hashedPassword.equals(hashedUserInputPassword)) {
//            System.out.println("密码正确");
//        } else {
//            System.out.println("密码错误");
//        }
    }

    public static String getMD5Password(String password){
        String salt = generateSalt();
        String hashedPassword = hashPassword(password, salt);
        return hashedPassword+salt;
    }
    public static String getMD5Password(String password,String salt){
        String hashedPassword = hashPassword(password, salt);
        return hashedPassword+salt;
    }

    private static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[SALT_LENGTH];
        random.nextBytes(saltBytes);
        return bytesToHex(saltBytes);
    }

    private static String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(salt.getBytes());
            byte[] hashedPasswordBytes = md.digest(password.getBytes());
            return bytesToHex(hashedPasswordBytes);
        } catch (NoSuchAlgorithmException e) {
            // 处理算法不支持的异常
            System.err.println("MD5算法不受支持: " + e.getMessage());
            return null;
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}


