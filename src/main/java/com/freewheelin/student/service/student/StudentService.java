package com.freewheelin.student.service.student;

import com.freewheelin.student.api.common.response.BaseResponse;
import com.freewheelin.student.api.common.response.CMResponse;
import com.freewheelin.student.api.common.response.Constant;
import com.freewheelin.student.api.common.response.ResPonseExceptionHandler;
import com.freewheelin.student.api.dto.StudentSaveRequestDto;
import com.freewheelin.student.api.util.StringUtil;
import com.freewheelin.student.domain.student.Student;
import com.freewheelin.student.domain.student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final ResPonseExceptionHandler resPonseExceptionHandler;

    public List<Student> findByPhoneNumber(String ph){
        List<Student> entity = studentRepository.findByPhoneNumber(StringUtil.formatPhoneNumber(ph));
        return entity;
    }

    @Transactional
    public ResponseEntity<? extends BaseResponse>  save(StudentSaveRequestDto requestDto){
        String code = null;
        String message = null;
        try{
            List<Student> student = new ArrayList<>();
            student = findByPhoneNumber(requestDto.getPhoneNumber());
            if(student.size() > 0){
                return resPonseExceptionHandler.studentEmptyException(student.get(0).getPhoneNumber());
            }
            studentRepository.save(requestDto.toEntity());
        }catch (Exception e){
            System.out.println(e.toString());
            code = Constant.STATUS_CODE.BAD_REQUEST_BODY.getValue();
            message = Constant.STATUS_MESSAGE.SAVE_FAIL.getValue();
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CMResponse(code, message));
    }
}
