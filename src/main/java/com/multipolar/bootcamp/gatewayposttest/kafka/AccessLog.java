package com.multipolar.bootcamp.gatewayposttest.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessLog {
    private String httpMethod;
    private String requestUri;
    private Integer responseStatusCode;
    private String content;
    private String clientIP;
    private String userAgent;
    private LocalDateTime timeStamp;
}
