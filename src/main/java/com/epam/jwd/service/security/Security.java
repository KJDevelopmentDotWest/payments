package com.epam.jwd.service.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Objects;
import java.util.Random;

/**
 * This singleton class contains security methods
 */
public class Security {
    private static final Logger logger = LogManager.getLogger(Security.class);

    private static final byte[] SALT_FIRST = {70, -61, -8, 42, -17, -105, 92, 47, -58, -28, 3, 28, -63, 97, -100, 17};
    private static final byte[] SALT_SECOND = {-96, 59, 118, 27, -85, 126, -1, 37, 91, -12, 74, -94, 73, -68, 36, -84};

    private static final String SECRET_KEY_FACTORY_INSTANCE_NAME = "PBKDF2WithHmacSHA1";
    private static final Integer ITERATION_COUNT = 50000;
    private static final Integer KEY_LENGTH = 50;

    private static Security instance;

    private Security(){}

    public static Security getInstance(){
        if (Objects.isNull(instance)){
            instance = new Security();
        }
        return instance;
    }

    /**
     *
     * @param password password to be hashed
     * @return hash result
     */
    public String generateHashForPassword(String password) {
        Random random = new Random();
        boolean randomBoolean = random.nextBoolean();

        byte[] salt;

        if (randomBoolean){
            salt = SALT_FIRST;
        } else {
            salt = SALT_SECOND;
        }

        try {
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(SECRET_KEY_FACTORY_INSTANCE_NAME);
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return new String(hash, StandardCharsets.US_ASCII);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.error(e);
            return null;
        }
    }

    /**
     *
     * @param key password to be checked
     * @param encryptedValue encrypted password to be checked
     * @return true if encrypted key matches encryptedValue, false otherwise
     */
    public Boolean isPasswordMatches(String key, String encryptedValue){
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance(SECRET_KEY_FACTORY_INSTANCE_NAME);
            KeySpec spec = new PBEKeySpec(key.toCharArray(), SALT_FIRST, ITERATION_COUNT, KEY_LENGTH);
            byte[] hashOne = factory.generateSecret(spec).getEncoded();
            spec = new PBEKeySpec(key.toCharArray(), SALT_SECOND, ITERATION_COUNT, KEY_LENGTH);
            byte[] hashTwo = factory.generateSecret(spec).getEncoded();
            return new String(hashOne, StandardCharsets.US_ASCII).equals(encryptedValue) || new String(hashTwo, StandardCharsets.US_ASCII).equals(encryptedValue);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.error(e);
            return false;
        }
    }
}