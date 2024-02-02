package cky.cky_api.service;

public enum ResponseCode {
    OK("OK"),
    FAIL("FAIL"),
    ERROR("ERROR");

    private final String responseCode;

    private ResponseCode(String code) {
        responseCode = code;
    }

    @Override
    public String toString() {
        return responseCode;
    }
}
