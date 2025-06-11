package medilabo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import medilabo.model.*;;

@Controller
@RequestMapping()
public class HomeController {

    @Value("${gateway.url}")
    private String gatewayUrl;

    @Value("${gateway.url-local}")
    private String gatewayUrlLocal;

    @GetMapping("/")
    public String home() {
        return "home"; // Returns the name of the view to be rendered
    }

    @GetMapping("/login")
    public String login() {
        System.out.println("---------LOGIN-----------");
        return "login"; // Returns the login view
    }

    @PostMapping("/login")
    public String loginsSubmit(User user) {
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("SYSO-GATE" + gatewayUrl + "/user/login");
        restTemplate.postForObject(gatewayUrlLocal + "/user/login", user, User.class);
        return gatewayUrlLocal + "/LOG";
    }

}
