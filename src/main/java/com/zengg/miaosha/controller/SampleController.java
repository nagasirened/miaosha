package com.zengg.miaosha.controller;

import com.zengg.miaosha.config.redis.mould.realize.GoodsKey;
import com.zengg.miaosha.config.redis.mould.realize.MiaoshaKey;
import com.zengg.miaosha.config.redis.mould.realize.UserKey;
import com.zengg.miaosha.model.MiaoshaUser;
import com.zengg.miaosha.model.User;
import com.zengg.miaosha.model.vo.GoodsVO;
import com.zengg.miaosha.service.GoodsService;
import com.zengg.miaosha.service.RabbitMQSender;
import com.zengg.miaosha.service.RedisService;
import com.zengg.miaosha.service.UserService;
import com.zengg.miaosha.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: miaosha
 * @description: 测试环境搭建
 * @author: ZengGuangfu
 * @create 2019-02-11 09:36
 */

@Controller
@RequestMapping(value = "/taxi")
public class SampleController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @Autowired
    private GoodsService goodsService;

    /**
     * 测试thymeleaf
     */
    @RequestMapping(value = "/thymeleaf")
    public String testThymeleaf(Model model){
        model.addAttribute("name","huangyu");
        return "hello";
    }

    /**
     * 测试mybatis
     */
    @RequestMapping(value = "/dbget")
    @ResponseBody
    public Result<User> get(){
        User user = userService.getUserById(1);
        return Result.success(user);
    }

    /**
     * 测试事务
     */
    @RequestMapping(value = "/dbtx")
    @ResponseBody
    public Result<Boolean> deTX(){
        boolean tx = userService.tx();
        return Result.success(tx);
    }

    /**
     * 测试refis get
     */
    @RequestMapping(value = "/redis/get")
    @ResponseBody
    public Result<User> redisGet(){
        User v1 = redisService.get(UserKey.getById, "2", User.class);
        return Result.success(v1);
    }

    /**
     * 测试refis
     */
    @RequestMapping(value = "/redis/set")
    @ResponseBody
    public Result<Boolean> redisSet(){
        User user = new User(4, "huangyu");
        boolean bool = redisService.set(UserKey.getById,"2", user);
        return Result.success(bool);
    }

    /**
     * 压测带参数的请求
     */
    @RequestMapping(value = "/info")
    @ResponseBody
    public Result<MiaoshaUser> info(Model model ,MiaoshaUser user){
        return Result.success(user);
    }


    /**
     * 压测带参数的请求
     */
    @RequestMapping(value = "/mq")
    @ResponseBody
    public void mq(Model model ,MiaoshaUser user){
        rabbitMQSender.sendHeader(" third test data");
    }


    @RequestMapping(value = "matrix/{petid}",method = RequestMethod.GET)
    @ResponseBody
    public void matrixVariableTest(@PathVariable String petid, @MatrixVariable int q){
        System.out.println(petid);
        System.out.println(q);
    }

}











