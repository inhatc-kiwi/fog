package com.fog.member.dto;

import com.fog.member.constant.Address;
import com.fog.member.constant.Bank;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OauthAddInfoDto {
 
    private Address address;
    
    private String pnum;   
        
    private Bank bname;
    
    private String bnumber;

}
