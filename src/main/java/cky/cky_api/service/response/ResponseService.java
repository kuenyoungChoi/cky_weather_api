package cky.cky_api.service.response;

import cky.cky_api.service.ResponseCode;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ResponseService {

    // 결과 & 데이터 & 메시지
    public <T> ResponseEntity<?> result(HttpStatus status, ResponseCode code, T data) throws ParseException {
        DataResult<T> result = new DataResult<>();
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse((String) data);
        JSONObject jsonObj = (JSONObject) obj;
        result.setCode(code.toString());
        result.setResult((T) jsonObj);
        result.setStatus(status.value());
        return ResponseEntity.ok().body(result);
    }

    public ResponseEntity<?> result(HttpStatus status, ResponseCode code, String message) {
        CommonResult result = new CommonResult();
        result.setCode(code.toString());
        result.setMsg(message);
        result.setStatus(status.value());
        return new ResponseEntity<>(result, status);
    }

    public <T> ResponseEntity<?> ok(T data) throws ParseException {
        System.out.println("data = " + data.getClass().getName());
        return result(HttpStatus.OK, ResponseCode.OK, data);
    }

    public <T> ResponseEntity<?> fail(String message) {
        return result(HttpStatus.BAD_REQUEST, ResponseCode.FAIL, message);
    }

    /**
     * 오류 메시지
     */
    public ResponseEntity<?> error() {
        ErrorResult result = new ErrorResult();
        result.setError("");
        result.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public <T> ResponseEntity<?> error(String message) {
        ErrorResult result = new ErrorResult();
        result.setError(message);
        result.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
