package cn.badminton.tool.web.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode()
public class BattleDTO {
    private Integer roundNum;

    private PlayerDTO partner1Player1;
    private PlayerDTO partner1Player2;

    private PlayerDTO partner2Player1;
    private PlayerDTO partner2Player2;


    public PartnerDTO getPartner1(){
        PartnerDTO partnerDTO = new PartnerDTO();
        partnerDTO.setP1(partner1Player1);
        partnerDTO.setP2(partner1Player2);
        return partnerDTO;
    }

    public PartnerDTO getPartner2(){
        PartnerDTO partnerDTO = new PartnerDTO();
        partnerDTO.setP1(partner2Player1);
        partnerDTO.setP2(partner2Player2);
        return partnerDTO;
    }
}
