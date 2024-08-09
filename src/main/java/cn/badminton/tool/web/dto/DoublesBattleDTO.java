//package cn.badminton.tool.web.dto;
//
//import lombok.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@ToString
//@EqualsAndHashCode()
//public class DoublesBattleDTO {
//
//    private Integer roundNum;
//
//    private PartnerDTO partnerDTO1;
//    private PartnerDTO partnerDTO2;
//
//    public DoublesBattleDTO(PlayerDTO p1, PlayerDTO p2, PlayerDTO p3, PlayerDTO p4){
//        partnerDTO1 = new PartnerDTO();
//        partnerDTO2 = new PartnerDTO();
//
//        List<PlayerDTO> ppp = new ArrayList<>(4);
//
//        ppp.add(p1);
//        ppp.add(p2);
//        ppp.add(p3);
//        ppp.add(p4);
//
//        partnerDTO1.setP1(ppp.get(0));
//        partnerDTO1.setP2(ppp.get(1));
//        partnerDTO2.setP1(ppp.get(2));
//        partnerDTO2.setP2(ppp.get(3));
//    }
//
//}
