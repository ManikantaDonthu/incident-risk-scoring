package com.example.risk.config;

import com.example.risk.model.Alert;
import com.example.risk.model.AlertCategory;
import com.example.risk.repository.AlertRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seed(AlertRepository r) {
        return args -> {
            if (r.count() > 0) {
                return;
            }
            r.save(new Alert("Suspicious email attachment", AlertCategory.phishing, "mail-gw-01", 18));
            r.save(new Alert("Credential harvesting page", AlertCategory.phishing, "proxy-02", 27));
            r.save(new Alert("Endpoint ransomware signature", AlertCategory.malware, "endpoint-17", 36));
            r.save(new Alert("Malicious PowerShell execution", AlertCategory.malware, "endpoint-22", 31));
            r.save(new Alert("Repeated port scan", AlertCategory.network_scan, "edge-fw-01", 12));
            r.save(new Alert("External host port sweep", AlertCategory.network_scan, "edge-fw-02", 20));
            r.save(new Alert("Privileged login anomaly", AlertCategory.credential_access, "auth-01", 34));
            r.save(new Alert("Unusual token use", AlertCategory.credential_access, "iam-02", 25));
        };
    }
}
