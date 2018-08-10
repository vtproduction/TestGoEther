package com.midsummer.web3jlight.util;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * Created by NienLe on 10,August,2018
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */
public class AddressUtil {
    private static final Pattern ignoreCaseAddrPattern = Pattern.compile("(?i)^(0x)?[0-9a-f]{40}$");
    private static final Pattern lowerCaseAddrPattern = Pattern.compile("^(0x)?[0-9a-f]{40}$");
    private static final Pattern upperCaseAddrPattern = Pattern.compile("^(0x)?[0-9A-F]{40}$");

    public static boolean isAddress(String address) {
        return !(TextUtils.isEmpty(address) || !ignoreCaseAddrPattern.matcher(address).find())
                && (lowerCaseAddrPattern.matcher(address).find() || upperCaseAddrPattern.matcher(address).find());
    }
}
