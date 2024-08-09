package cn.badminton.tool.web.controller;

import cn.badminton.tool.support.auth.WXAppletAuthenticationToken;
import cn.badminton.tool.support.common.RaceMainTypeEnum;
import cn.badminton.tool.web.entity.WXUserEntity;
import cn.badminton.tool.web.params.UserProfileParam;
import cn.badminton.tool.web.service.AuthUserService;
import cn.badminton.tool.support.Result;
import cn.badminton.tool.web.service.RaceBattleService;
import cn.badminton.tool.web.vo.BattleStatisticsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("user")
public class WXUserController {

    @Autowired
    private AuthUserService authUserService;

    @Autowired
    private RaceBattleService battleService;

    @RequestMapping(value = "get",method = RequestMethod.GET)
    public Result<WXUserEntity> getUserInfo(){
        WXAppletAuthenticationToken principal = (WXAppletAuthenticationToken) SecurityContextHolder.getContext().getAuthentication().getDetails();
        WXUserEntity byOpenid = authUserService.getByOpenid(principal.getOpenid());
        return Result.ok(byOpenid);
    }

    @RequestMapping(value = "battleStatistics",method = RequestMethod.GET)
    public Result<Map<String, BattleStatisticsVO>> battleStatistics(){
        WXAppletAuthenticationToken principal = (WXAppletAuthenticationToken) SecurityContextHolder.getContext().getAuthentication().getDetails();
        BattleStatisticsVO temp = new BattleStatisticsVO();

        BattleStatisticsVO vo1 = battleService.getBattleStatistics(principal.getUid(), RaceMainTypeEnum.DOUBLE.getType());
        vo1 = vo1 == null ? temp : vo1;
        BattleStatisticsVO vo2 = battleService.getBattleStatistics(principal.getUid(),RaceMainTypeEnum.SINGLE.getType());
        vo2 = vo2 == null ? temp : vo2;
        Map<String, BattleStatisticsVO> map = new HashMap<>();
        map.put("doubles",vo1);
        map.put("singles",vo2);
        return Result.ok(map);
    }

    @RequestMapping(value = "uploadUserInfo",method = RequestMethod.POST)
    public Result uploadUserInfo(@RequestBody UserProfileParam param){
        WXAppletAuthenticationToken principal = (WXAppletAuthenticationToken) SecurityContextHolder.getContext().getAuthentication().getDetails();
        WXUserEntity build = WXUserEntity.builder()
                .uid(principal.getUid())
                .openid(principal.getOpenid())
                .nickName(param.getNickName())
                .avatarUrl(param.getAvatarUrl())
                .gender(param.getGender())
                .build();
        authUserService.updateById(build);
        return Result.ok();
    }
}
