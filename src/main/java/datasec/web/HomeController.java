package datasec.web;

import datasec.domain.LoggedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by Michał on 2017-06-02.
 */

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    LoggedUser loggedUser;

    @RequestMapping(method = GET)
    public String home(Model model) {

        if (loggedUser.isLogged()) {

            model.addAttribute("login", loggedUser.getLogin());
            return "home";
        }

        return "redirect:/login";
    }
}
