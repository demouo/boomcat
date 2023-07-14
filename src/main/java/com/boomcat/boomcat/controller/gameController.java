package com.boomcat.boomcat.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.boomcat.boomcat.domain.Game;
import com.boomcat.boomcat.domain.User;
import com.boomcat.boomcat.result.Result;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.boomcat.boomcat.service.GameService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/game")
public class gameController {
    @Autowired
    GameService gameService;

    @PostMapping("/init")
    Result initAGame(@RequestBody JSONObject jsonObject) {
        String type = (String) jsonObject.get("type");
        JSONArray data = jsonObject.getJSONArray("users");
        String js = JSONObject.toJSONString(data);
        ArrayList<User> users = (ArrayList<User>) JSONObject.parseArray(js, User.class);
        //new the game
        Game game = gameService.initGame(type, users);
        JSONObject jo = JSONObject.parseObject(JSONObject.toJSONString(game));
        return Result.success(jo);
    }
}
