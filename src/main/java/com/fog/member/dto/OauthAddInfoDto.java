package com.fog.member.dto;

import com.fog.member.constant.Area;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OauthAddInfoDto {
 
    private Area area;
    
    private String pnum;   
    
    private String bnumber;

}
