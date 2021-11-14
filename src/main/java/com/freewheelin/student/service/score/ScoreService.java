package com.freewheelin.student.service.score;


import com.freewheelin.student.api.common.response.BaseResponse;
import com.freewheelin.student.api.common.response.CMResponse;
import com.freewheelin.student.api.common.response.Constant;
import com.freewheelin.student.api.common.response.ResPonseExceptionHandler;
import com.freewheelin.student.api.dto.ErrResponseDto;
import com.freewheelin.student.api.dto.ScoreStudentResponse;
import com.freewheelin.student.api.dto.ScoreStudentResponseInterface;
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
import java.util.List;
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

        if(resultMap.get("result").toString().equals("catch")){
            return (ResponseEntity<Object>) throwExceptionHandler(resultMap);
        }else{
            Optional<StSjBridge> stSjBridge = stSjBridgeRepository.findByStIdSjId(requestDto.getStudent_id(), requestDto.getSubject_id());
            //기존 점수가 있다면 ->
            if(stSjBridge.isPresent()){
                if(stSjBridge.get().getScore() + requestDto.getScore() + 1 > 100) requestDto.setScore(100);
                requestDto.setStSjBridge_id(stSjBridge.get().getId());
                stSjBridgeRepository.saveSql(requestDto.getScore(), requestDto.getStSjBridge_id());
            }else{ // insert
                StSjBridge saveDto = new StSjBridge(requestDto.getStudent_id(), requestDto.getSubject_id(), requestDto.getScore());
                stSjBridgeRepository.save(saveDto);
//                stSjBridgeRepository.save(requestDto.toEntity(requestDto.getStudent_id(), requestDto.getSubject_id(), requestDto.getScore()));
            }

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new CMResponse(null, null));
        }
    }

    @Transactional
    public ResponseEntity<Object> modify(StSjSaveRequestDto requestDto) {
        HashMap<String, Object> resultMap;
        resultMap = ScoreDataValidationCheck(requestDto);

        if(resultMap.get("result").toString().equals("catch")){
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

        if(resultMap.get("result").toString().equals("catch")){
            return (ResponseEntity<Object>) throwExceptionHandler(resultMap);
        }else{
            stSjBridgeRepository.deleteByStIdSjId(requestDto.getStudent_id(), requestDto.getSubject_id());

            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new CMResponse(null, null));
        }
    }

    public ResponseEntity<Object> studentScoreAvg(Long studentId) {
        HashMap<String, Object> resultMap;
        resultMap = findStudentReturnMap(studentId);

        if(resultMap.get("result").toString().equals("catch")){
            return (ResponseEntity<Object>) throwExceptionHandler(resultMap);
        }else{
            List<ScoreStudentResponseInterface> list =  stSjBridgeRepository.findByStudentAllSubject(studentId);
            int studentAvg = getStudentAvg(list);
            HashMap<String, Object> responseMap = new HashMap<>(){{
                put("averageScore", studentAvg);
                put("subjects", list);
            }};
            BaseResponse response = BaseResponse.builder()
                    .data(responseMap)
                    .error(null)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }


    public ResponseEntity<Object> subjectScoreAvg(Long subjectId) {
        HashMap<String, Object> resultMap;
        resultMap = findSubjectReturnMap(subjectId);
        if(resultMap.get("result").toString().equals("catch")){
            return (ResponseEntity<Object>) throwExceptionHandler(resultMap);
        }else{
            List<ScoreStudentResponseInterface> list =  stSjBridgeRepository.findByAllSubjectScoreAvg(subjectId);
            int studentAvg = getStudentAvg(list);
            HashMap<String, Object> responseMap = new HashMap<>(){{
                put("averageScore", studentAvg);
                put("subjects", list);
            }};
            BaseResponse response = BaseResponse.builder()
                    .data(responseMap)
                    .error(null)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    private int getStudentAvg(List<ScoreStudentResponseInterface> list) {
        int result = 0;
        int score = 0, countSubject = 0;
        if(list.size() > 0 ){
            for(ScoreStudentResponseInterface s : list){
                if(s.getScore() > 0){
                    score += s.getScore();
                    countSubject++;
                }
            }

            if(countSubject > 0){
                try{
                    result = (int) Math.ceil(score / countSubject);
                }catch (Exception e){
                    System.out.println(e.toString());
                    result = -1;
                }
            }else{
                result = -1;
            }
        }

        return result;
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
        HashMap<String, Object> resultMap;

        resultMap = findStudentReturnMap(requestDto.getStudent_id());
        if(StringUtil.isEmpty(resultMap.get("result").toString())){
            resultMap = findSubjectReturnMap(requestDto.getSubject_id());
        }

        return resultMap;
    }

    private HashMap<String, Object> findSubjectReturnMap(Long subjectId) {
        HashMap<String, Object> resultMap = new HashMap<>() {{
            put("result", "");
        }};
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        Optional<Subject> subject = subjectRepository.findById(subjectId);
        if(subject.isEmpty()){
            resultMap.put("result", "catch");
            resultMap.put("httpStatus", httpStatus);
            resultMap.put("statusCode", Constant.STATUS_CODE.SUBJECT_NOT_FOUND.getValue());
            resultMap.put("statusMessage", Constant.STATUS_MESSAGE.SUBJECT_NOT_FOUND.getValue());
            resultMap.put("regexKey", "subjectId");
            resultMap.put("replaceStr", String.valueOf(subjectId));
        }
        return resultMap;
    }

    private HashMap<String, Object> findStudentReturnMap(Long studentId) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        HashMap<String, Object> resultMap = new HashMap<>() {{
            put("result", "");
        }};

        Optional<Student> student = studentRepository.findById(studentId);
        if(student.isEmpty()){
            resultMap.put("result", "catch");
            resultMap.put("httpStatus", httpStatus);
            resultMap.put("statusCode", Constant.STATUS_CODE.STUDENT_NOT_FOUND.getValue());
            resultMap.put("statusMessage", Constant.STATUS_MESSAGE.STUDENT_NOT_FOUND.getValue());
            resultMap.put("regexKey", "studentId");
            resultMap.put("replaceStr", String.valueOf(studentId));
        }
        return resultMap;
    }

}
