package in.arifalimondal.crawlingservice.web;

import in.arifalimondal.crawlingservice.model.Website;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api")
public class TestCrawlerController {

    Logger logger = LoggerFactory.getLogger(TestCrawlerController.class);

    @GetMapping("/getwebsite/{id}")
    public Website getWebsiteById(@PathVariable int id) {
        List<Website> websites = getWebsites();

        Website user = websites.stream()
                        .filter(u -> u.getId()==id).findAny().orElse(null);

        if (user != null){
            logger.info("User found: {}", user);
            return user;
        }else {
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("User not found with Id: {}", id);
            }

            return new Website();
        }
    }

    private List<Website> getWebsites() {

        return Stream.of(new Website(1, "Telegraph"),
                new Website(2, "CNN"),
                new Website(3, "BBC"),
                new Website(4, "theHindu"),
                new Website(5, "theWire"))
                .collect(Collectors.toList());
    }
}
