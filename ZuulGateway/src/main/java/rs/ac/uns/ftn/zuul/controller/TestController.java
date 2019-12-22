package rs.ac.uns.ftn.zuul.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

// TODO Delete whole class
@RestController
public class TestController {

    @GetMapping("/zuultest")
    public String test(){
        System.out.println("Inside secured()");
        return "Hello user !!! : " + new Date();
    }
}
