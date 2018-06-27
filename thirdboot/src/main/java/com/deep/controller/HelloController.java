package com.deep.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.Date;

@RestController
public class HelloController {
    @Value("${person.name}")
    private String name;

    @Value("${person.age}")
    private int age;

    @GetMapping("/say")
    public String sayHi(){
        String test = "Hello world,my name is {0},and ~ i''m {1,number,integer} years old,现在时间是{2}";
        String result = MessageFormat.format(test,name,age,new Date());
        return result;
    }

    @GetMapping("/say2")
    public String sayHi2(){
        return "Hello World!!! My name is saq，nice to meet you!";
    }

    @RequestMapping(value="say3",method = RequestMethod.GET)
    public String sayHi3(){
        return "这是最新的方法";
    }

    @GetMapping("/say4")
    public String sayHi4(String ss){
        return "postMapping test" + ss;
    }
}
