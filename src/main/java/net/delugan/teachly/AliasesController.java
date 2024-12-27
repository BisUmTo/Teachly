package net.delugan.teachly;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AliasesController {
    @GetMapping("/")
    public String home() {
        return "redirect:/dashboard";
    }

    @GetMapping("/api/v1")
    public String docs() {
        return "redirect:/swagger-ui/index.html";
    }
}
