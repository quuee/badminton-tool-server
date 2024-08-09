package cn.badminton.tool.web.convert;

import cn.badminton.tool.web.entity.RaceMainEntity;
import cn.badminton.tool.web.vo.RaceVO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RaceConvert {

    RaceVO convertToVO(RaceMainEntity raceMain);
    List<RaceVO> convertToVO(List<RaceMainEntity> mainEntityList);
}
