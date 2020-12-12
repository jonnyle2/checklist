package com.jonnyle.checklist;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {
    
    @GetMapping(value = "/any")
    public String someMethod(){
        return "any";
    }
}