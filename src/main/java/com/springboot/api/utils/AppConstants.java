package com.springboot.api.utils;

public class AppConstants {

    public static final String DEFAULT_PAGE_NUMBER = "0";
    public  static final String DEFAULT_PAGE_SIZE = "10";
    public static final String DEFAULT_SORT_BY = "id";
    public static final String DEFAULT_SORT_DIRECTION = "asc";
    public static final String[] PUBLIC_URLS = { "/v3/api-docs/**", "/swagger-ui/**", "/api/v1/auth/register", "/api/v1/auth/login" };
    public static final String[] USER_URLS = { "/api/v1/public/**" };
    public static final String[] ADMIN_URLS = { "/api/v1/admin/**" };

}
