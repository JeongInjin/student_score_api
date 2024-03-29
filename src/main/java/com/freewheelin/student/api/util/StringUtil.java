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
     * 주어진 문자열이 null 또는 공백일 경우 참 반환
     *
     * StringUtils.isEmpty("") = true
     *
     * @param str 문자열
     * @return null 또는 공백일 경우 true
     */
    public static boolean isEmpty(String str) {
        return (str == null || str.length() == 0);
    }

    /*
    * 필수값을 확인하여 boolean return 합니다.
    * */
    public static boolean requiredValidationCheckReturnBoolean(Object t){
        boolean result = true;
        if(t instanceof Long || t instanceof String){
            return !StringUtil.isEmpty(String.valueOf(t));
        }
        if(t instanceof Integer){
            if(!StringUtil.isEmpty(String.valueOf(t))){
                int score = Integer.parseInt(String.valueOf(t));
                if(score < 0 || 100 < score)
                    return false;
            }else{
                return false;
            }
        }
        return result;
    }

}
