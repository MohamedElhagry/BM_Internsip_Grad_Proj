package com.project.bm_internsip_grad_project.securityFilters;

public class Constants {
    public static final Long JWT_TIMEOUT = 60*60*5L;
    public static final String SECRET = "secret";
    public static final String HEADER_START = "Bearer ";
    public static final Integer HEADER_LEN = 7;
}
