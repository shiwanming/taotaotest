package com.taotao.sso.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${TOKEN_KEY}")
    private String TOKEN_KEY;

    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult login(String username, String password,
                              HttpServletResponse response, HttpServletRequest request){
        TaotaoResult result = userService.login(username,password);
        if(result.getStatus()==200){
            CookieUtils.setCookie(request,response,TOKEN_KEY,result.getData().toString());
        }

        return result;
    }

    @RequestMapping("/user/check/{param}/{type}")
    @ResponseBody
    public TaotaoResult checkUserData(@PathVariable String param,@PathVariable Integer type){
        TaotaoResult result =userService.checkData(param,type);
        return result;
    }

    @RequestMapping(value = "/user/register",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult register(TbUser user){
        TaotaoResult result = userService.register(user);
        return result;
    }
}
