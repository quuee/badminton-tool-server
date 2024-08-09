package cn.badminton.tool.web.controller;

import cn.badminton.tool.support.auth.WXAppletAuthenticationToken;
import cn.badminton.tool.web.entity.RaceMainEntity;
import cn.badminton.tool.web.params.RaceFormParam;
import cn.badminton.tool.web.service.RaceService;
import cn.badminton.tool.web.vo.RaceVO;
import cn.badminton.tool.support.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("raceIndex")
public class RaceIndexController {
    private static final Logger logger = LoggerFactory.getLogger(RaceIndexController.class);

    @Autowired
    private RaceService raceService;
    /**
     * 比赛列表
     * 查询条件：自己创建的、自己有参与报名的
     *
     * @return
     */
    @RequestMapping(value = "raceList",method = RequestMethod.GET)
    public Result<Page<RaceVO>> raceVOList(@RequestParam(value = "pageNo",defaultValue = "1")Integer pageNo,
                                           @RequestParam(value = "pageLimit",defaultValue = "10")Integer pageLimit){
        WXAppletAuthenticationToken principal = (WXAppletAuthenticationToken) SecurityContextHolder.getContext().getAuthentication().getDetails();

        // TODO 分页
        Page<RaceVO> raceList = raceService.getRaceList(principal.getUid(),pageNo,pageLimit);

        return Result.ok(raceList);
    }


    @RequestMapping(value = "createRace",method = RequestMethod.POST)
    public Result<RaceMainEntity> createRace(@RequestBody RaceFormParam raceFormParam){
        logger.info("比赛表单参数: {}",raceFormParam);

        RaceMainEntity raceMain = raceService.createRace(raceFormParam);

        return Result.ok(raceMain);
    }




}
