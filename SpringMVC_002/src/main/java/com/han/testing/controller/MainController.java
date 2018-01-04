package com.han.testing.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

/**
 * Created by mouyaling on 16/6/12.
 */
@Controller
public class MainController {
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String index(){
        return "redirect:/hello";
    }
    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String hello(ModelMap map){
        String s="hello world";
        map.addAttribute("data",s);
        return "/showdata";
    }
}
