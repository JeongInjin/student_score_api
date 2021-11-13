package com.freewheelin.student.service.student;

import com.freewheelin.student.api.common.response.BaseResponse;
import com.freewheelin.student.api.common.response.CMResponse;
import com.freewheelin.student.api.common.response.Constant;
import com.freewheelin.student.api.common.response.ResPonseExceptionHandler;
import com.freewheelin.student.api.dto.ErrResponseDto;
import com.freewheelin.student.api.dto.StudentListResponseDto;
import com.freewheelin.student.api.dto.StudentSaveRequestDto;
import com.freewheelin.student.domain.student.Student;
import com.freewheelin.student.domain.student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final ResPonseExceptionHandler resPonseExceptionHandler;

    @Transactional
    public ResponseEntity<Object> save(StudentSaveRequestDto requestDto){
        String code = null;
        String message = null;
        try{
            List<Student> student = new ArrayList<>();
            student = studentRepository.findByPhoneNumber(requestDto.getPhoneNumber());
            // 휴대폰 번호로 중복검사.기존 회원이라면 ->
            if(student.size() > 0){
                HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
                String statusCode = Constant.STATUS_CODE.ALREADY_EXIST_STUDENT.getValue();
                String statusMessage = Constant.STATUS_MESSAGE.ALREADY_EXIST_STUDENT.getValue();
                String regexKey =  "phoneNumber";
                String replaceStr = student.get(0).getPhoneNumber();

                ErrResponseDto errResponseDto = ErrResponseDto.builder()
                        .httpStatus(httpStatus)
                        .statusCode(statusCode)
                        .statusMessage(statusMessage)
                        .regexKey(regexKey)
                        .replaceStr(replaceStr)
                        .build();

                return resPonseExceptionHandler.errResponseException(errResponseDto);
            }
            //저장
            studentRepository.save(requestDto.toEntity());
        }catch (Exception e){
            System.out.println(e.toString());
            code = Constant.STATUS_CODE.BAD_REQUEST_BODY.getValue();
            message = Constant.STATUS_MESSAGE.SAVE_FAIL.getValue();
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CMResponse(code, message));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Object> findByAllDesc() {
        List<StudentListResponseDto> list = new ArrayList<>();
        list = studentRepository.findAllDesc();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("students", list);
        BaseResponse response = BaseResponse.builder()
                .data(resultMap)
                .error(null)
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }
    @Transactional
    public ResponseEntity<Object> delete(Long id) {
        studentRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body("");
    }
}
