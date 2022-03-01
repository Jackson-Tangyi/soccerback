package com.felix.soccerback;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootTest
class SoccerbackApplicationTests {

    @Test
    void contextLoads() {
    }

    @GetMapping("/")
    public String index(){
        return "ok";
    }

}
