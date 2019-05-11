package com.wishlist.util;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomUtil {

    private RandomUtil() {
        throw new RuntimeException("You can not create this class");
    }

    private static final int NAME_COUNT = 10;

    private static final int ACTIVATION_KEY_COUNT = 20;

    private static final int PASS_COUNT = 7;

    public static String generateName() {
        return RandomStringUtils.randomAlphanumeric(RandomUtil.NAME_COUNT);
    }

    public static String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(RandomUtil.PASS_COUNT);
    }

    public static String generateActivationKey() {
        return RandomStringUtils.randomNumeric(RandomUtil.ACTIVATION_KEY_COUNT);
    }
}
