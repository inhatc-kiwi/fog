package com.fog.mypage.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fog.member.entity.Member;
import com.fog.mypage.dto.CategoryUpdateDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="category")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Category {
    @Id
    @Column(name="category_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    // 카테고리 종류
    @Column(unique = true,name="category_type")
    private String type;
    
    // 다대일 관계 - 외래키(사용자)
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    
    
    // 카테고리 생성 메소드
    public static List<Category> createCategory() {
    	String [] deType = {"프로필","프로젝트","대외활동","수상자격 / 자격증", "보유기술"};
    	List<Category> categorys = new ArrayList<Category>();
    	
    	for (int i = 0; i < deType.length; i++) {
    		Category category = new Category();
        	category.setType(deType[i]);
        	categorys.add(category);
		}
    	
    	return categorys;
    }
    

}