package com.fog.member.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fog.member.constant.Area;
import com.fog.member.constant.Role;
import com.fog.member.dto.MemberFormDto;
import com.fog.member.dto.OauthAddInfoDto;
import com.fog.utils.entity.BaseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@DynamicUpdate
@Table(name="member")
@Getter @Setter
@NoArgsConstructor
@ToString
public class Member extends BaseEntity {
    @Id
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name="member_name")
    private String name;

    // 회원은 이메일을 통해 유일하게 구분해야 하기 때문에, 동일한 값이 DB에 저장할 수 없도록 unique 속성 지정한다.
    @Column(unique = true, name="member_email")
    private String email;
    
    @Column(name="member_password")
    private String password;
    
    // 분야
    @Enumerated(EnumType.STRING)
    @Column(name="member_area")
    private Area area;
    
    // 포그 주소
    @Column(name="member_fogid")
    private String fogid;

    // enum 타입은 기본적으로 순서가 저장되는데 순서가 바뀌면 문제가 생기므로 STRING 옵션 설정한다.
    @Enumerated(EnumType.STRING)
    private Role role;
    
    // OAuth로그인 서비스 제공자
    private String provider;
    
    @Column(unique = true)
    // OAuth로그인한 내 계정에 대한 Id
    private String providerId;

    // 프로필 사진
    private String image;
    
    // OAuth2 추가정보 여부
    private String updateOauthYn;
    
    // 조회수
    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view;
    
//    @OneToMany(mappedBy = "member")
//    private List<Cash> cashs = new ArrayList<>();

    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder){
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setArea(memberFormDto.getArea());
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);
        member.setRole(Role.ADMIN);
        member.setImage(memberFormDto.getImage());
        return member;
    }
    
    public void addInfoOAuth2(OauthAddInfoDto addInfoDto) {
    	this.area = addInfoDto.getArea();
    	this.fogid = addInfoDto.getFogid();
    }
     
    @Builder
	public Member(String email, String password, Role role, String provider, String providerId,String name, int cash, int level, int point,String image,String updateYn) {
		this.email = email;
		this.password = password;
		this.role = role;
		this.provider = provider;
		this.providerId = providerId;
		this.name = name;
		this.image = image;
	}
    
}
