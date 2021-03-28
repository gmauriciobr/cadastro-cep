package br.com.gmauricio.swagger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class SwaggerController {
	
	@GetMapping
    public RedirectView swaggerRoute() {
        return new RedirectView("/api/swagger-ui.html");
    }

}
