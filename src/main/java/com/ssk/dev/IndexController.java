package com.ssk.dev;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Index Page 처리 담당 Controller.
 *
 * @author ssk
 */
@Controller
@RequestMapping("/")
public class IndexController {

    /**
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     *
     * @return page page path
     * @see IndexController
     * Index Page
     */
    @RequestMapping("index")
    public String index(HttpServletRequest request, HttpServletResponse response) {
        String page = "index";

        this.index(request, response);
        return page;
    }
}
