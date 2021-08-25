package com.example.kongservicesecurity.rest;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KongServiceContorller {

    @GetMapping("/kongservice/kongplace/test")
    public String kongplace(){
        return "콩플레이스 서비스";
    }

    @GetMapping("/kongservice/kongeco/test")
    public String kongeco(){
        return "콩에코 서비스";
    }

    @GetMapping("/kongservice/kongcheck/test")
    public String kongcheck(){
        return "콩체크 서비스";
    }

    @GetMapping("/kongservice/kongpass/test")
    public String kongpass(){
        return "콩패스 서비스";
    }
}
