package com.jojodu.book.springboot.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController = 컨트롤러를 json을 반환하는 컨트롤러로 만들어줍니다.
//예전에는 @ResponseBody를 각 메소드마다 선언했던것을 한번에 사용 할 수 있도록 해줍니다.
@RestController
public class HelloController {

    //@GetMapping = http method인 get의 요청을 받을 수 있는 api를 만들어줍니다.
    //예전에는 @RequestMapping(method=RequestMethod.GET) 으로 쓰이던것.
    @GetMapping("/hello")
    public String Hello(){
        return "hello";
    }
}
