package demo.controller;

import demo.util.resType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/AI")
public class AIController {

    public resType accompany(@RequestParam("id")String id){
        return new resType();
    }

}
