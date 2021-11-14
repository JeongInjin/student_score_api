package com.freewheelin.student.service.student;

import com.freewheelin.student.api.common.response.BaseResponse;
import com.freewheelin.student.api.common.response.CMResponse;
import com.freewheelin.student.api.common.response.Constant;
import com.freewheelin.student.api.common.response.ResPonseExceptionHandler;
import com.freewheelin.student.api.dto.ErrResponseDto;
import com.freewheelin.student.api.dto.StudentListResponseDto;
import com.freewheelin.student.api.dto.StudentSaveRequestDto;
import com.freewheelin.student.api.dto.SubjectListResponseDto;
import com.freewheelin.student.domain.stsjBridge.StSjBridge;
import com.freewheelin.student.domain.stsjBridge.StSjBridgeRepository;
import com.freewheelin.student.domain.student.Student;
import com.freewheelin.student.domain.student.StudentRepository;
import com.freewheelin.student.domain.subject.Subject;
import com.freewheelin.student.domain.subject.SubjectRepository;
import com.freewheelin.student.service.subject.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final StSjBridgeRepository stSjBridgeRepository;
    private final ResPonseExceptionHandler resPonseExceptionHandler;

    @Transactional
    public ResponseEntity<Object> save(StudentSaveRequestDto requestDto){
        String code = null;
        String message = null;
        StudentListResponseDto student = new StudentListResponseDto();
        try{
            student = studentRepository.findByPhoneNumber(requestDto.getPhoneNumber());
            // 휴대폰 번호로 중복검사.기존 회원이라면 ->
            if(student != null){
                HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
                String statusCode = Constant.STATUS_CODE.ALREADY_EXIST_STUDENT.getValue();
                String statusMessage = Constant.STATUS_MESSAGE.ALREADY_EXIST_STUDENT.getValue();
                String regexKey =  "phoneNumber";
                String replaceStr = student.getPhoneNumber();

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
            //추가조건 : 학생 추가시 추가한 학생은 등록되어 있는 모든 과목을 수강하여야만 합니다.
            student = studentRepository.findByPhoneNumber(requestDto.getPhoneNumber());
            findByAllAndStToSaveStSj(student);


        }catch (Exception e){
            System.out.println(e.toString());
            code = Constant.STATUS_CODE.BAD_REQUEST_BODY.getValue();
            message = Constant.STATUS_MESSAGE.SAVE_FAIL.getValue();
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CMResponse(code, message));
    }

    //과목 추가 후 전체 학생에 과목 추가.
    @Transactional
    public void findByAllAndStToSaveStSj(StudentListResponseDto student) {
        List<SubjectListResponseDto> subjectList = subjectRepository.findAll().stream()
                .map(SubjectListResponseDto::new)
                .collect(Collectors.toList());
        if(subjectList.size() > 0){
            List<StSjBridge> list = new ArrayList<>(){
                {
                    for(int i =0; i < subjectList.size(); i++){
                        add(StSjBridge.builder()
                                .studentDto(student)
                                .subjectDto(subjectList.get(i))
                                .score(-1)
                                .build());
                    }
                }
            };
            stSjBridgeRepository.saveAll(list);
        }

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
    public ResponseEntity<Object> delete(Long studentId) {
        Optional<Student> student = studentRepository.findById(studentId);
        student.ifPresent(selectStudent ->{
            studentRepository.delete(selectStudent);
        });

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body("");
    }

}
