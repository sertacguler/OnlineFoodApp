package com.bilgeadam.auth;

import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
public class BasicAuthenticationController {
    @RequestMapping(method = RequestMethod.GET,path = "/basicauthentication")
    public AuthenticationBean auth(){
        return new AuthenticationBean("You are authenticated!");
    }
}
