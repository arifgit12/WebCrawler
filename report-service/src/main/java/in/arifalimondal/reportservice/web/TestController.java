package in.arifalimondal.reportservice.web;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@Slf4j
@RefreshScope
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Value("${test.name}")
    private String name;

    @GetMapping("/ping")
    public String message() {
        logger.info("Wao!! Application Deployed successfully");
        return "Wao!! " + name + " Application Deployed successfully...";
    }

    @GetMapping("/t1")
    public String testResource() {
        logger.info("Test T1");
        return "This is a Test resource";
    }
}
