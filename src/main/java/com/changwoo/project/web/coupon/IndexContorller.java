package com.changwoo.project.web.coupon;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by changwooj111@gmail.com on 2019-08-11
 */
@RestController
public class IndexContorller {

    @GetMapping(path = "/")
    public String indexPage(){
        return "index page";
    }

}
