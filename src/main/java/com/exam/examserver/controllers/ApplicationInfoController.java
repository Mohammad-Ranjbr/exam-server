package com.exam.examserver.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/info")
@CrossOrigin({"http://localhost:4200","http://localhost:9091"})
public class ApplicationInfoController {

    @Value("${app.name}") //@Value("${app.name}")
    private String appName;
    @Value("${app.version}") //@Value("${app.version}")
    private String appVersion;


    @GetMapping("/details")
    public ResponseEntity<String> getApplicationInformation(){
        String message = String.format("Application Name : %s , Application Version : %s",appName,appVersion);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
