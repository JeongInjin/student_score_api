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
    public static final String ALREADY_EXIST_STUDENT_MESSAGE = "이미 존재하는 학생입니다. [phoneNumber]";
    public static final String ALREADY_EXIST_SUBJECT_MESSAGE = "이미 존재하는 과목입니다. [name]";
    public static final String STUDENT_NOT_FOUND_MESSAGE = "학생을 찾을 수 없습니다. [studentId]";
    public static final String SUBJECT_NOT_FOUND_MESSAGE = "과목을 찾을 수 없습니다. [subjectId]";

    /********************************************************************
     * CODE
     * ******************************************************************/
    public static final String BAD_REQUEST_BODY_CODE = "BAD_REQUEST_BODY";
    public static final String ALREADY_EXIST_STUDENT_CODE = "ALREADY_EXIST_STUDENT";
    public static final String ALREADY_EXIST_SUBJECT_CODE = "ALREADY_EXIST_SUBJECT";
    public static final String STUDENT_NOT_FOUND_CODE = "STUDENT_NOT_FOUND";
    public static final String SUBJECT_NOT_FOUND_CODE = "SUBJECT_NOT_FOUND";
    public static final String INTERNAL_SERVER_ERROR_CODE = "SERVER_ERROR";


    public enum STATUS_CODE{
        BAD_REQUEST_BODY(BAD_REQUEST_BODY_CODE), // HttpStatus 400
        ALREADY_EXIST_STUDENT(ALREADY_EXIST_STUDENT_CODE), // students 테이블에 같은 phoneNumber 존재.
        ALREADY_EXIST_SUBJECT(ALREADY_EXIST_SUBJECT_CODE), // subject 테이블에 같은 name 존재.
        STUDENT_NOT_FOUND(STUDENT_NOT_FOUND_CODE), // 점수 등록시 학생 없을시.
        SUBJECT_NOT_FOUND(STUDENT_NOT_FOUND_CODE), // 점수 등록시 과목 없을시.
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
        ALREADY_EXIST_SUBJECT(ALREADY_EXIST_SUBJECT_MESSAGE), // 400 - 이미 존재하는 과목
        STUDENT_NOT_FOUND(STUDENT_NOT_FOUND_MESSAGE), // 404 - 학생을 찾을 수 없습니다.
        SUBJECT_NOT_FOUND(SUBJECT_NOT_FOUND_MESSAGE), // 404 - 과목을 찾을 수 없습니다.
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
