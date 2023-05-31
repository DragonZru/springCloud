package com.spring.web.example;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/example")
public class ExampleController {


    /**
     * 测试接口.
     */
    @GetMapping
    public Object example(HttpServletRequest request) {

        return "port:" + request.getServerPort() + "\nsession id:" + request.getSession().getId();
    }

    @GetMapping("/str")
    public Object returnStr(@RequestParam(defaultValue = "sss") String str) {
        return str;
    }
}
