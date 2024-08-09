package cn.badminton.tool.web.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class PartnerDTO {

    private PlayerDTO p1;

    private PlayerDTO p2;

}
