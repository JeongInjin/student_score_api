package com.freewheelin.student.service.score;


import com.freewheelin.student.api.common.response.CMResponse;
import com.freewheelin.student.api.common.response.Constant;
import com.freewheelin.student.api.common.response.ResPonseExceptionHandler;
import com.freewheelin.student.api.dto.ErrResponseDto;
import com.freewheelin.student.api.dto.StSjSaveRequestDto;
import com.freewheelin.student.api.util.StringUtil;
import com.freewheelin.student.domain.stsjBridge.StSjBridge;
import com.freewheelin.student.domain.stsjBridge.StSjBridgeRepository;
import com.freewheelin.student.domain.student.Student;
import com.freewheelin.student.domain.student.StudentRepository;
import com.freewheelin.student.domain.subject.Subject;
import com.freewheelin.student.domain.subject.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ScoreService {
    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository;
    private final StSjBridgeRepository stSjBridgeRepository;
    private final ResPonseExceptionHandler resPonseExceptionHandler;

    @Transactional
    public ResponseEntity<Object> save(StSjSaveRequestDto requestDto) {
        HashMap<String, Object> resultMap;
        resultMap = ScoreDataValidationCheck(requestDto);

        if(resultMap.get("returnCode").equals("catch")){
            return (ResponseEntity<Object>) throwExceptionHandler(resultMap);
        }else{
            Optional<StSjBridge> stSjBridge = stSjBridgeRepository.findByStIdSjId(requestDto.getStudent_id(), requestDto.getSubject_id());
            //기존 점수가 있다면 ->
            if(stSjBridge.isPresent()){
                if(stSjBridge.get().getScore() + requestDto.getScore() + 1 > 100) requestDto.setScore(100);
                requestDto.setStSjBridge_id(stSjBridge.get().getId());
                stSjBridgeRepository.saveSql(requestDto.getScore(), requestDto.getStSjBridge_id());
            }else{ // insert
                stSjBridgeRepository.save(requestDto.toEntity(requestDto.getSubject_id(), requestDto.getSubject_id(), requestDto.getScore()));
            }

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new CMResponse(null, null));
        }
    }

    @Transactional
    public ResponseEntity<Object> modify(StSjSaveRequestDto requestDto) {
        HashMap<String, Object> resultMap;
        resultMap = ScoreDataValidationCheck(requestDto);

        if(resultMap.get("returnCode").equals("catch")){
            return (ResponseEntity<Object>) throwExceptionHandler(resultMap);
        }else{
            Optional<StSjBridge> stSjBridge = stSjBridgeRepository.findByStIdSjId(requestDto.getStudent_id(), requestDto.getSubject_id());
            if(stSjBridge.isPresent()){
                requestDto.setStSjBridge_id(stSjBridge.get().getId());
                stSjBridgeRepository.saveSql(requestDto.getScore(), requestDto.getStSjBridge_id());
            }

            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new CMResponse(null, null));
        }
    }


    @Transactional
    public ResponseEntity<Object> delete(StSjSaveRequestDto requestDto) {
        HashMap<String, Object> resultMap;
        resultMap = ScoreDataValidationCheck(requestDto);

        if(resultMap.get("returnCode").equals("catch")){
            return (ResponseEntity<Object>) throwExceptionHandler(resultMap);
        }else{
            stSjBridgeRepository.deleteByStIdSjId(requestDto.getStudent_id(), requestDto.getSubject_id());

            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new CMResponse(null, null));
        }
    }

    private HttpEntity<Object> throwExceptionHandler(HashMap<String, Object> resultMap) {
        ErrResponseDto errResponseDto = ErrResponseDto.builder()
                .httpStatus((HttpStatus) resultMap.get("httpStatus"))
                .statusCode((String) resultMap.get("statusCode"))
                .statusMessage((String) resultMap.get("statusMessage"))
                .regexKey((String) resultMap.get("regexKey"))
                .replaceStr((String) resultMap.get("replaceStr"))
                .build();
        return resPonseExceptionHandler.errResponseException(errResponseDto);
    }

    private HashMap<String, Object> ScoreDataValidationCheck(StSjSaveRequestDto requestDto) {
        HashMap<String, Object> resultMap = new HashMap<>() {{
           put("returnCode", "");
        }};
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        String statusCode = "", statusMessage = "", regexKey = "", replaceStr = "";

        Optional<Student> student = studentRepository.findById(requestDto.getStudent_id());
        if(student.isEmpty()){
            resultMap.put("returnCode", "catch");
            statusCode = Constant.STATUS_CODE.STUDENT_NOT_FOUND.getValue();
            statusMessage = Constant.STATUS_MESSAGE.STUDENT_NOT_FOUND.getValue();
            regexKey =  "studentId";
            replaceStr = String.valueOf(requestDto.getSubject_id());
        }

        if(StringUtil.isEmpty(statusCode)){
            Optional<Subject> subject = subjectRepository.findById(requestDto.getSubject_id());
            if(subject.isEmpty()){
                resultMap.put("returnCode", "catch");
                statusCode = Constant.STATUS_CODE.SUBJECT_NOT_FOUND.getValue();
                statusMessage = Constant.STATUS_MESSAGE.SUBJECT_NOT_FOUND.getValue();
                regexKey =  "subjectId";
                replaceStr = String.valueOf(requestDto.getSubject_id());
            }
        }

        if(resultMap.get("returnCode").equals("catch")){
            resultMap.put("httpStatus" ,httpStatus);
            resultMap.put("statusCode" ,statusCode);
            resultMap.put("statusMessage" ,statusMessage);
            resultMap.put("regexKey" ,regexKey);
            resultMap.put("replaceStr" ,replaceStr);
        }
        return resultMap;
    }
}
