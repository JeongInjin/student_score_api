package com.freewheelin.student.api.util;
/*
* DESC : 문자열 관련 util 을 제공합니다.
* */
public final class StringUtil {
    private StringUtil() {};

    /*
    * 휴대폰 번호를 받아 하이픈 형식으로 return 합니다.
    * */
    public static String formatPhoneNumber(String phoneNumber){
        if(phoneNumber.length() == 10 || phoneNumber.length() == 11){
            String regEx = "(\\d{3})(\\d{3,4})(\\d{4})";
            return phoneNumber.replaceAll(regEx, "$1-$2-$3");
        }
        return phoneNumber;
    }

    /**
     * 입력된 문자열이 주어진 문자열과 일치하는 모든 문자열을 바꿔야할 문자열로 변경<br><br>
     *
     * StringUtils.replaceAll("Anyframe Java Test Anyframe Java Test", "Anyframe", "Enterprise") = "Enterprise Java Test Enterprise Java Test"
     *
     * @param message 문자열
     * @param regex 검색할 문자열
     * @param replacement 변경할 문자열
     * @return 검색된 모든 문자열을 변경한 문자열
     * @see String#replaceAll(String, String)
     */
    public static String replaceAll(String message, String regex, String replacement){
        if (message == null) {
            return null;
        }
        return message.replaceAll(regex, replacement);
    }
}
