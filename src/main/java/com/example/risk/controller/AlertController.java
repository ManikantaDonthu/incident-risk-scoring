package com.example.risk.controller;

import com.example.risk.model.Alert;
import com.example.risk.repository.AlertRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertRepository r;

    public AlertController(AlertRepository r) {
        this.r = r;
    }

    @GetMapping
    public List<Alert> list() {
        return r.findAll();
    }
}
