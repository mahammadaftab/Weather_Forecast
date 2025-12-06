package com.weatherforecast.util;

import java.util.regex.Pattern;

/**
 * Utility class for input validation and sanitization
 */
public class ValidationUtil {
    
    // Regular expression patterns for validation
    private static final Pattern CITY_ID_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]{1,50}$");
    private static final Pattern MONGO_ID_PATTERN = Pattern.compile("^[a-f0-9]{24}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z0-9\\s\\-_\\.]{1,100}$");
    private static final Pattern COUNTRY_CODE_PATTERN = Pattern.compile("^[A-Z]{2}$");
    private static final Pattern STATE_CODE_PATTERN = Pattern.compile("^[A-Z0-9]{1,10}$");
    private static final Pattern IP_ADDRESS_PATTERN = Pattern.compile("^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
    
    /**
     * Validate city ID
     * @param cityId the city ID to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidCityId(String cityId) {
        return cityId != null && (CITY_ID_PATTERN.matcher(cityId).matches() || MONGO_ID_PATTERN.matcher(cityId).matches());
    }
    
    /**
     * Validate location name
     * @param name the name to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidName(String name) {
        return name != null && NAME_PATTERN.matcher(name).matches();
    }
    
    /**
     * Validate country code
     * @param countryCode the country code to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidCountryCode(String countryCode) {
        return countryCode != null && COUNTRY_CODE_PATTERN.matcher(countryCode).matches();
    }
    
    /**
     * Validate state code
     * @param stateCode the state code to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidStateCode(String stateCode) {
        return stateCode != null && STATE_CODE_PATTERN.matcher(stateCode).matches();
    }
    
    /**
     * Validate IP address
     * @param ipAddress the IP address to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidIpAddress(String ipAddress) {
        return ipAddress != null && IP_ADDRESS_PATTERN.matcher(ipAddress).matches();
    }
    
    /**
     * Sanitize input string by removing potentially harmful characters
     * @param input the input string to sanitize
     * @return sanitized string
     */
    public static String sanitizeInput(String input) {
        if (input == null) {
            return null;
        }
        // Remove HTML tags and script elements
        return input.replaceAll("<[^>]*>", "")
                   .replaceAll("(?i)<script.*?>.*?</script>", "")
                   .trim();
    }
    
    /**
     * Validate and sanitize city ID
     * @param cityId the city ID to validate and sanitize
     * @return sanitized city ID if valid, null otherwise
     */
    public static String validateAndSanitizeCityId(String cityId) {
        if (isValidCityId(cityId)) {
            return sanitizeInput(cityId);
        }
        return null;
    }
    
    /**
     * Validate and sanitize location name
     * @param name the name to validate and sanitize
     * @return sanitized name if valid, null otherwise
     */
    public static String validateAndSanitizeName(String name) {
        if (isValidName(name)) {
            return sanitizeInput(name);
        }
        return null;
    }
    
    /**
     * Validate hours parameter
     * @param hours the hours to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidHours(int hours) {
        return hours >= 1 && hours <= 168; // Max 7 days (168 hours)
    }
    
    /**
     * Validate days parameter
     * @param days the days to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidDays(int days) {
        return days >= 1 && days <= 14; // Max 14 days
    }
}