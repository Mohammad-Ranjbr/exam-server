package com.exam.examserver.config;

public class ApplicationConstans {

    //public static final int ADMIN_USER=501;
    public static final String[] PUBLIC_URLS = {
            "/api/user/**",
            "/api/quiz/**",
            "/api/categories/**",
            "/api/questions/**",
            "/api/v1/authentication/**"
    };
    public static final int NORMAL_USER = 502 ;
    public static final String ACCESS_DENIED = "Access Denied" ;

    public static final String AUTHORIZATION_HEADER = "Authorization" ;
    public static final String SECRET = "JwtTokenSecretForExamPortalServer" ;
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60 * 1000 ;
    public static final String JWT_TOKEN_STARTS_WITH  = "ExamPortal";
    public static final String JWT_TOKEN_ILLEGAL_ARGUMENT_EXCEPTION = "Unable To Get Jwt Token";
    public static final String JWT_TOKEN_EXPIRED_JWT_EXCEPTION = "Jwt Token Has Expired";
    public static final String JWT_TOKEN_MALFORMED_JWT_EXCEPTION = "Invalid Jwt Token";
    public static final String NULL_JWT_TOKEN = "Jwt Token is Null Or Does Not Starts With ExamPortal";
    public static final String JWT_TOKEN_FAILED_VALIDATE = "Invalid jwt token";
    public static final String JWT_TOKEN_NULL_USERNAME_OR_CONTEXT = "Username is null or Security Context is not null";
    public static final String JWT_TOKEN_SIGNATURE_EXCEPTION = "JWT signature does not match locally computed signature";

}
