package com.fog.mypage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fog.member.entity.Member;
import com.fog.mypage.dto.CategoryDto;

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

	// 포그 타이틀
	@Column(name = "title")
	private String title;

	// 포그 내용
	@Column(name = "content")
	private String content;

	// 카테고리 공개,비공개
	@Column(name = "categoryYn")
	private String categoryYn;

	// 다대일관계 카테고리
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	// 다대일관계 회원
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;
	
	// 객체 생성
	public static CategoryContent createContent(CategoryDto categoryDto) {
		CategoryContent content = new CategoryContent();
		content.setContent(categoryDto.getContent());
		content.setCategoryYn(categoryDto.getCategoryYn());
		content.setTitle(categoryDto.getTitle());
		return content;
	}
}
