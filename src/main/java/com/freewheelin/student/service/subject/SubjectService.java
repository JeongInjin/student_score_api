package com.freewheelin.student.service.subject;

import com.freewheelin.student.api.common.response.BaseResponse;
import com.freewheelin.student.api.common.response.CMResponse;
import com.freewheelin.student.api.common.response.Constant;
import com.freewheelin.student.api.common.response.ResPonseExceptionHandler;
import com.freewheelin.student.api.dto.ErrResponseDto;
import com.freewheelin.student.api.dto.SubjectListResponseDto;
import com.freewheelin.student.api.dto.SubjectSaveRequestDto;
import com.freewheelin.student.domain.subject.Subject;
import com.freewheelin.student.domain.subject.SubjectRepository;
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
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final ResPonseExceptionHandler resPonseExceptionHandler;

    @Transactional
    public ResponseEntity<Object> save(SubjectSaveRequestDto requestDto){
        String code = null;
        String message = null;
        try{
            Subject subject = new Subject();
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
        }catch (Exception ex){
            System.out.print(ex.toString());
            code = Constant.STATUS_CODE.BAD_REQUEST_BODY.getValue();
            message = Constant.STATUS_MESSAGE.SAVE_FAIL.getValue();
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CMResponse(code, message));
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
    public ResponseEntity<Object> delete(Long id) {
        subjectRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body("");
    }
}
