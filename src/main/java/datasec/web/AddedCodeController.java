package datasec.web;

import datasec.domain.Code;
import datasec.domain.LoggedUser;
import datasec.domain.service.CodeService;
import datasec.exception.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Michał on 2017-05-25.
 */

@Controller
@RequestMapping("/addCode")
public class AddedCodeController {

    private CodeService codeService;
    private LoggedUser loggedUser;

    @Autowired
    public AddedCodeController(CodeService cs, LoggedUser lg) {
        this.codeService = cs;
        this.loggedUser = lg;
    }

    @RequestMapping(method = GET)
    public String getCodeFromForm(Model model) {

        if (loggedUser.isLogged()) {

            Code code = new Code();
            model.addAttribute("code", code);
            return "addCode";
        }

        return "redirect:/login";
    }

    @RequestMapping(method = POST)
    public String processAddNewWordForm(@ModelAttribute("code") @Valid Code code, BindingResult result) {

        if (loggedUser.isLogged()) {
            code.setText(HtmlUtils.htmlEscape(code.getText()));
            this.codeService.addCode(code, result);
            return "redirect:/snippets";
        }

        return "redirect:/login";
    }

    @ExceptionHandler(ApplicationException.class)
    public ModelAndView handleAppException() {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("exception");
        return mav;
    }
}