package com.vintage.elasticsearch.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @Author: Vintage Wang
 * @Date: 2018-09-20
 */

@RestController
@RequestMapping("/ishare/es/")
public class ESController {

    @ResponseBody
    @RequestMapping(value = "/query",method = RequestMethod.POST)
    public String query(@RequestBody JSONObject o){

        return "";
    }
}
