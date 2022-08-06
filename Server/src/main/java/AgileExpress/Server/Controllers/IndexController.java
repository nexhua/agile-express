package AgileExpress.Server.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @GetMapping("/dashboard")
    public ModelAndView dashboard() {
        return new ModelAndView("index");
    }

    @GetMapping("/user")
    public ModelAndView user() {
        return new ModelAndView("index");
    }

    @GetMapping("/hello")
    public ModelAndView hello() {
        return new ModelAndView("index");
    }

    @GetMapping("")
    public ModelAndView home() {
        return new ModelAndView("index");
    }

    @GetMapping("/details/{projectID}")
    public ModelAndView projectDetail() {
        return new ModelAndView("index");
    }

    @GetMapping("/project")
    public ModelAndView getProjectPage(@RequestParam String pid) {
        return new ModelAndView("index");
    }
}
