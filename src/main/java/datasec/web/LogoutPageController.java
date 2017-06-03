package datasec.web;

import datasec.domain.LoggedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Michał on 2017-06-01.
 */

@Controller
@RequestMapping("/logout")
public class LogoutPageController {

    @Autowired
    LoggedUser loggedUser;

    @RequestMapping(method = GET)
    public String logoutGet() {

        loggedUser.setLogged(false);
        loggedUser.setLogin("");
        return "logout";
    }

    @RequestMapping(method = POST)
    public String logoutPost() {

        loggedUser.setLogged(false);
        loggedUser.setLogin("");
        return "logout";
    }
}
