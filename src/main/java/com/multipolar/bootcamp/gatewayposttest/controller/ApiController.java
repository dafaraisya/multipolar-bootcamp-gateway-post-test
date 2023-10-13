package com.multipolar.bootcamp.gatewayposttest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.multipolar.bootcamp.gatewayposttest.dto.AccountDTO;
import com.multipolar.bootcamp.gatewayposttest.dto.ErrorMessageDTO;
import com.multipolar.bootcamp.gatewayposttest.kafka.AccessLog;
import com.multipolar.bootcamp.gatewayposttest.service.AccessLogService;
import com.multipolar.bootcamp.gatewayposttest.util.RestTemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {
    private static final String ACCOUNT_URL = "http://localhost:8081/accounts";
    private final RestTemplateUtil restTemplateUtil;
    private final ObjectMapper objectMapper;
    private final AccessLogService logService;

//    private final HttpServletRequest request;

    @Autowired
    public ApiController(RestTemplateUtil restTemplateUtil, ObjectMapper objectMapper, AccessLogService logService) {
        this.restTemplateUtil = restTemplateUtil;
        this.objectMapper = objectMapper;
        this.logService = logService;
//        this.request = request;
    }

    @GetMapping("/getAccounts")
    public ResponseEntity<?> getAccounts(HttpServletRequest request) throws JsonProcessingException {
        try {
            ResponseEntity<?> response = restTemplateUtil.getList(ACCOUNT_URL, new ParameterizedTypeReference<Object>() {});
            AccessLog accessLog = new AccessLog(
                    "GET",
                    "mongodb://localhost:27017/account",
                    response.getStatusCode().value(),
                    response.getBody().toString(),
                    request.getRemoteAddr(),
                    request.getHeader("User-Agent"),
                    LocalDateTime.now()
            );
            logService.logAccess(accessLog);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (HttpClientErrorException ex) {
            List<ErrorMessageDTO> errorResponse = objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            AccessLog accessLog = new AccessLog(
                    "GET",
                    "mongodb://localhost:27017/account",
                    400,
                    "get failed",
                    request.getRemoteAddr(),
                    request.getHeader("User-Agent"),
                    LocalDateTime.now()
            );
            logService.logAccess(accessLog);
            return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        }
    }

    @GetMapping("/getAccount/{id}")
    public ResponseEntity<?> getAccountById(HttpServletRequest request, @PathVariable String id) throws JsonProcessingException {
        try {
            ResponseEntity<?> response = restTemplateUtil.get(ACCOUNT_URL + "/" + id, AccountDTO.class);
            AccessLog accessLog = new AccessLog(
                    "GET",
                    "mongodb://localhost:27017/account",
                    response.getStatusCode().value(),
                    response.getBody().toString(),
                    request.getRemoteAddr(),
                    request.getHeader("User-Agent"),
                    LocalDateTime.now()
            );
            logService.logAccess(accessLog);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (HttpClientErrorException ex) {
            List<ErrorMessageDTO> errorResponse = objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            AccessLog accessLog = new AccessLog(
                    "GET",
                    "mongodb://localhost:27017/account",
                    400,
                    "get failed",
                    request.getRemoteAddr(),
                    request.getHeader("User-Agent"),
                    LocalDateTime.now()
            );
            logService.logAccess(accessLog);
            return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        }
    }

    @PostMapping("/createAccount")
    public ResponseEntity<?> postAccount(@RequestBody AccountDTO accountDTO, HttpServletRequest request) throws JsonProcessingException {
        try {
            ResponseEntity<?> response = restTemplateUtil.post(ACCOUNT_URL, accountDTO, AccountDTO.class);
            AccessLog accessLog = new AccessLog(
                    "POST",
                    "mongodb://localhost:27017/account",
                    response.getStatusCode().value(),
                    response.getBody().toString(),
                    request.getRemoteAddr(),
                    request.getHeader("User-Agent"),
                    LocalDateTime.now()
            );
            logService.logAccess(accessLog);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }catch(HttpClientErrorException ex) {
            List<ErrorMessageDTO> errorResponse = objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            AccessLog accessLog = new AccessLog(
                    "POST",
                    "mongodb://localhost:27017/account",
                    400,
                    "post failed",
                    request.getRemoteAddr(),
                    request.getHeader("User-Agent"),
                    LocalDateTime.now()
            );
            logService.logAccess(accessLog);
            return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        }

    }

    @PutMapping("/updateAccount/{id}")
    public ResponseEntity<?> updateAccount(@RequestBody AccountDTO accountDTO, @PathVariable String id, HttpServletRequest request) throws JsonProcessingException {
        try {
            ResponseEntity<?> response = restTemplateUtil.put(ACCOUNT_URL +"/"+ id, accountDTO, AccountDTO.class);
            AccessLog accessLog = new AccessLog(
                    "PUT",
                    "mongodb://localhost:27017/account",
                    response.getStatusCode().value(),
                    response.getBody().toString(),
                    request.getRemoteAddr(),
                    request.getHeader("User-Agent"),
                    LocalDateTime.now()
            );
            logService.logAccess(accessLog);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }catch(HttpClientErrorException ex) {
            List<ErrorMessageDTO> errorResponse = objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            AccessLog accessLog = new AccessLog(
                    "PUT",
                    "mongodb://localhost:27017/account",
                    400,
                    "put failed",
                    request.getRemoteAddr(),
                    request.getHeader("User-Agent"),
                    LocalDateTime.now()
            );
            logService.logAccess(accessLog);
            return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        }

    }

    @DeleteMapping("/deleteAccount/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable String id, HttpServletRequest request) throws JsonProcessingException {
        try {
            ResponseEntity<?> response = restTemplateUtil.delete(ACCOUNT_URL +"/"+ id);
            AccessLog accessLog = new AccessLog(
                    "DELETE",
                    "mongodb://localhost:27017/account",
                    response.getStatusCode().value(),
                    "",
                    request.getRemoteAddr(),
                    request.getHeader("User-Agent"),
                    LocalDateTime.now()
            );
            logService.logAccess(accessLog);
            return ResponseEntity.noContent().build();
        }catch(HttpClientErrorException ex) {
            List<ErrorMessageDTO> errorResponse = objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            AccessLog accessLog = new AccessLog(
                    "DELETE",
                    "mongodb://localhost:27017/account",
                    400,
                    "delete failed",
                    request.getRemoteAddr(),
                    request.getHeader("User-Agent"),
                    LocalDateTime.now()
            );
            logService.logAccess(accessLog);
            return ResponseEntity.noContent().build();
        }

    }
}
