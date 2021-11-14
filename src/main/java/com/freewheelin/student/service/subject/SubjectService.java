package com.freewheelin.student.service.subject;

import com.freewheelin.student.api.common.response.BaseResponse;
import com.freewheelin.student.api.common.response.CMResponse;
import com.freewheelin.student.api.common.response.Constant;
import com.freewheelin.student.api.common.response.ResPonseExceptionHandler;
import com.freewheelin.student.api.dto.ErrResponseDto;
import com.freewheelin.student.api.dto.StudentListResponseDto;
import com.freewheelin.student.api.dto.SubjectListResponseDto;
import com.freewheelin.student.api.dto.SubjectSaveRequestDto;
import com.freewheelin.student.domain.stsjBridge.StSjBridge;
import com.freewheelin.student.domain.stsjBridge.StSjBridgeRepository;
import com.freewheelin.student.domain.student.Student;
import com.freewheelin.student.domain.student.StudentRepository;
import com.freewheelin.student.domain.subject.Subject;
import com.freewheelin.student.domain.subject.SubjectRepository;
import com.freewheelin.student.service.student.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository;
    private final StSjBridgeRepository stSjBridgeRepository;
    private final ResPonseExceptionHandler resPonseExceptionHandler;

    @Transactional
    public ResponseEntity<Object> save(SubjectSaveRequestDto requestDto){
        String code = null;
        String message = null;
        SubjectListResponseDto subject = new SubjectListResponseDto();
        try{
            subject = subjectRepository.findByName(requestDto.getName());

            if(subject != null){
                HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
                String statusCode = Constant.STATUS_CODE.ALREADY_EXIST_SUBJECT.getValue();
                String statusMessage = Constant.STATUS_MESSAGE.ALREADY_EXIST_SUBJECT.getValue();
                String regexKey =  "name";
                String replaceStr = subject.getName();

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
            subjectRepository.save(requestDto.toEntity());
            //추가 조간 : 과목 추가시 등록되어 있는 학생은 모두 추가한 과목을 수강해야만 합니다.
            subject = subjectRepository.findByName(requestDto.getName());
            findByAllAndSbToSaveStSj(subject);
        }catch (Exception ex){
            System.out.print(ex.toString());
            code = Constant.STATUS_CODE.BAD_REQUEST_BODY.getValue();
            message = Constant.STATUS_MESSAGE.SAVE_FAIL.getValue();
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CMResponse(code, message));
    }

    @Transactional
    public void findByAllAndSbToSaveStSj(SubjectListResponseDto subject) {
        List<StudentListResponseDto> studentList = studentRepository.findAll().stream()
                .map(StudentListResponseDto::new)
                .collect(Collectors.toList());
        if(studentList.size() > 0){
            List<StSjBridge> list = new ArrayList<>(){
                {
                    for(int i =0; i < studentList.size(); i++){
                        add(StSjBridge.builder()
                                .subjectDto(subject)
                                .score(-1)
                                .studentDto(studentList.get((i)))
                                .build());
                    }
                }
            };
            stSjBridgeRepository.saveAll(list);
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Object> findByAllDesc() {
        List<SubjectListResponseDto> list = new ArrayList<>();
        list = subjectRepository.findAllDesc();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("subjects", list);
        BaseResponse response = BaseResponse.builder()
                .data(resultMap)
                .error(null)
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @Transactional
    public ResponseEntity<Object> delete(Long subjectId) {
        Optional<Subject> subject = subjectRepository.findById(subjectId);
        subject.ifPresent(selectSubject ->{
            subjectRepository.delete(selectSubject);
        });
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body("");
    }


}
