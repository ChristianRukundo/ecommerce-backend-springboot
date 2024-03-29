package com.christian.api.utils;

public class AppConstants {
    public static final String PAGE_NUMBER = "0";
    public static final String PAGE_SIZE = "3";
    public static final String SORT_CATEGORIES_BY = "categoryId";
    public static final String SORT_PRODUCTS_BY = "productId";
    public static final String SORT_USERS_BY = "id";
    public static final String SORT_ORDERS_BY = "totalAmount";

    public static final Long ADMIN_ID = 101L;
    public static final Long USER_ID = 102L;

    public static final String SORT_DIR = "asc";
    public static final String[] PUBLIC_URLS = { "/v3/api-docs/**", "/swagger-ui/**", "/api/v1/auth/register","/api/v1/auth/signup","/api/v1/auth/signin", "/api/v1/auth/login" };
    public static final String[] USER_URLS = { "/api/v1/public/**" };
    public static final String[] ADMIN_URLS = { "/api/v1/admin/**" };

}
