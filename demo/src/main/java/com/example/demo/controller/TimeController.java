package com.example.demo.controller;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("time")
public class TimeController {
    private final int DEFAULT_TZ = 3;
    private Map<String, Integer> users = new HashMap<String, Integer>();

    private Map<String, Integer> getTimeJson(String ip){
        int time = (int)(System.currentTimeMillis() / 1000);
        int timezone = users.get(ip);
        Map<String, Integer> outTime = new HashMap<String, Integer>();
/*
        Map<String, Integer> outTime = new HashMap<String, Integer>() {{
            put("hour", 0);
            put("min" , 0);
            put("sec" , 0);
        }};
*/
        outTime.put("sec", time % 60);
        outTime.put("min", (time / 60) % 60);
        outTime.put("hour", ((time / 3600) % 24) + timezone);

        if(outTime.get("hour") < 0)
            outTime.put("hour", outTime.get("hour") + 24);

        return outTime;
    }


    @GetMapping()
    public Map<String, Integer> getURLValue(HttpServletRequest request){
        String ip = request.getRemoteAddr();

        /*  uncomment this part if you want to set timezone using GET request "localhost:8080/time/timezone=<value>"*/

        String tz  =  request.getParameter("timezone");
        if(tz != null){
            try {
                int tzInt = Integer.parseInt(tz);
                if(tzInt > -13 && tzInt < 14) users.put(ip, tzInt);
            }
            catch(Exception e){
                System.out.println("Error format");
            }
        }

        if(users.get(ip) == null)
            users.put(ip, DEFAULT_TZ);
        return getTimeJson(ip);
    }

    @GetMapping("/timer")
    public ModelAndView timer(){
        System.out.println("timer work");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("test");
        return mav;
    }

    @PostMapping("")
    public String setTZ(@RequestParam String timezone, HttpServletRequest request){
        String ip = request.getRemoteAddr();
//        if(users.get(ip) == null)
//            users.put(ip, DEFAULT_TZ);
        try {
            int tzInt = Integer.parseInt(timezone);
            if(tzInt > -13 && tzInt < 14)
                users.put(ip, tzInt);
            else
                System.out.println("Error value");
        }
        catch(Exception e){
            System.out.println("Error format");
        }
        System.out.println(timezone);
        return "Set time zone: " + timezone + "\nip: "+ ip;
    }

}

