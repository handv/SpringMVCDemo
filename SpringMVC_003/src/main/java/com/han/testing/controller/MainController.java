package com.han.testing.controller;
import org.springframework.stereotype.Controller;
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
        return "redirect:/show";
    }
    @RequestMapping(value = "/show",method = RequestMethod.GET)
    public String hello(){
        return "/showdata";
    }
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    @ResponseBody
    public String hello(@RequestParam(value="num1")String num1, @RequestParam(value="num2")String num2){
        BigDecimal a = new BigDecimal(num1);
        BigDecimal b = new BigDecimal(num2);
        String data = a.add(b).toString();

        return data;
    }
}
