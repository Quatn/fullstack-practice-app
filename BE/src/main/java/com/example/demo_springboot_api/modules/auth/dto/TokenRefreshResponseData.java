package com.example.demo_springboot_api.modules.auth.dto;

public record TokenRefreshResponseData(UserState userState, String accessToken) {}
