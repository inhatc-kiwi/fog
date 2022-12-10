package com.fog.hitCount.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fog.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "hitCount")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HitCount {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // 방문자수 id

	@Column(length = 500)
	private String date; // 조회한 날짜

	@Column(length = 500)
	private String fogId; // 조회된 사용자 ID

	public void save(String fogId, String formatedNow) {
		this.fogId = fogId;
		this.date = formatedNow;
	}
}
