package cn.badminton.tool.web.controller;

import cn.badminton.tool.support.common.RaceMainTypeEnum;
import cn.badminton.tool.web.entity.RaceSchemeEntity;
import cn.badminton.tool.support.Result;
import cn.badminton.tool.web.service.RaceSchemeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("raceScheme")
public class RaceSchemeController {

    @Autowired
    private RaceSchemeService schemeService;

    @RequestMapping(value = "list",method = RequestMethod.GET)
    public Result<List<RaceSchemeEntity>> schemeList(){

        List<RaceSchemeEntity> list = schemeService.list();

        return Result.ok(list);
    }

    @RequestMapping(value = "mainTypeList",method = RequestMethod.GET)
    public Result<List<Map<String, Object>>> mainTypeList(){
        List<Map<String, Object>> maps = new ArrayList<>(4);

        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("raceMainTypeName", RaceMainTypeEnum.DOUBLE.getInfo());
        map1.put("raceMainType",RaceMainTypeEnum.DOUBLE.getType());
        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("raceMainTypeName", RaceMainTypeEnum.SINGLE.getInfo());
        map2.put("raceMainType",RaceMainTypeEnum.SINGLE.getType());
        HashMap<String, Object> map3 = new HashMap<>();
        map3.put("raceMainTypeName", RaceMainTypeEnum.ANTAGONISM.getInfo());
        map3.put("raceMainType",RaceMainTypeEnum.ANTAGONISM.getType());
        HashMap<String, Object> map4 = new HashMap<>();
        map4.put("raceMainTypeName", RaceMainTypeEnum.TEAM.getInfo());
        map4.put("raceMainType",RaceMainTypeEnum.TEAM.getType());

        maps.add(map1);
        maps.add(map2);
        maps.add(map3);
        maps.add(map4);

        return Result.ok(maps);
    }
}
