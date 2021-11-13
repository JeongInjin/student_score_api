package com.freewheelin.student.api.common.response;

public class Constant {
    /********************************************************************
     * SUCCESS MESSAGE
     * ******************************************************************/


    /********************************************************************
    * ERROR MESSAGE
    * ******************************************************************/
    public static final String SAVE_FAIL_JM = "잠시 후 다시 시도해 주세요.불편을 드려 대단히 죄송합니다.";
    public static final String BAD_REQUEST_BODY = "";
    public static final String ALREADY_EXIST_STUDENT_MESSAGE = "이미 존재하는 학생입니다. [${phoneNumber}]";

    /********************************************************************
     * CODE
     * ******************************************************************/
    public static final String BAD_REQUEST_BODY_CODE = "BAD_REQUEST_BODY";
    public static final String ALREADY_EXIST_STUDENT_CODE = "ALREADY_EXIST_STUDENT";
    public static final String INTERNAL_SERVER_ERROR_CODE = "SERVER_ERROR";





    public static final String BAD_REQUEST_CODE = "400";
    public enum STATUS_CODE{
        BAD_REQUEST_BODY(BAD_REQUEST_BODY_CODE), // HttpStatus 400
        ALREADY_EXIST_STUDENT(ALREADY_EXIST_STUDENT_CODE), // students 테이블에 같은 phoneNumber가 존재.
        INTERNAL_SERVER_ERROR(INTERNAL_SERVER_ERROR_CODE), // 서버 ERROR
        ;

        private String  value;

        STATUS_CODE(String value) {
            this.value  =   value;
        }

        public String getValue() {
            return this.value;
        }
    }
    public enum STATUS_MESSAGE{
        SAVE_FAIL(SAVE_FAIL_JM),
        ALREADY_EXIST_STUDENT(ALREADY_EXIST_STUDENT_MESSAGE), // 400 - 이미 존재하는 학생
        ;
        private String  value;

        STATUS_MESSAGE(String value) {
            this.value  =   value;
        }

        public String getValue() {
            return this.value;
        }
    }

}
