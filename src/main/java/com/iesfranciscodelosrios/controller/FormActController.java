package com.iesfranciscodelosrios.controller;

import com.iesfranciscodelosrios.service.FormActService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/form/?")
public class FormActController {

    @Autowired
    private FormActService formActService;

}
