package cn.killers.wqs.caskeycloak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
public class CasKeycloakApplication {

    public static void main(String[] args) {
        SpringApplication.run(CasKeycloakApplication.class, args);
    }

    @GetMapping("/test")
    public Map test() {
        HashMap<String, String> map = new HashMap(1);
        map.put("test", "test");
        return map;
    }

}
