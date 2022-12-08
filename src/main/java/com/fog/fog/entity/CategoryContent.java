package com.fog.fog.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fog.fog.dto.CategoryDto;
import com.fog.member.entity.Member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Category_Content")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class CategoryContent {
	@Id
	@Column(name = "cc_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	// 카테고리 내용
	@Column(name = "content")
	private String content;
	
	// 카테고리 공개,비공개
	@Column(name = "categoryYn")
	private char categoryYn;
	
	// 다대일관계 카테고리
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	// 다대일관계 회원
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;
	
	public static CategoryContent createContent(CategoryDto categoryDto) {
		CategoryContent content = new CategoryContent();
		content.setContent(categoryDto.getContent());
		content.setCategoryYn(categoryDto.getCategoryYn());
		
		return content;
	}
}
