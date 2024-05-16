package com.iesfranciscodelosrios.controller;

import org.apache.coyote.Response;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

    @GetMapping(value = "", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String checkHealth() {
        return new String("Health 100%");
    }

}
