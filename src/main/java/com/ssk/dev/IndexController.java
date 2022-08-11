package com.ssk.dev;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by sik371@ktnet.co.kr 2022-08-11 오후 3:48
 */
@Controller
@RequestMapping("/")
public class IndexController {
    @RequestMapping("index")
    public String index(HttpServletRequest request, HttpServletResponse response) {

        return "index";
    }
}
