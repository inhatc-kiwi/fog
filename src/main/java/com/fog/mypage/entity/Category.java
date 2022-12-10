package com.fog.mypage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
    
    public void updateCategory() {
    	
    }
}
