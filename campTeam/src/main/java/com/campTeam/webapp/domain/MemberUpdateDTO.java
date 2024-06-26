package com.campTeam.webapp.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
// @ToString
public class MemberUpdateDTO extends MemberDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 회원 패쓰워드(수정) */
	private String password1;
	
	/** 회원 패쓰워드(확인) */
	private String password2;

	public MemberUpdateDTO(MemberDTO memberDTO) {

		this.setId(memberDTO.getId());
		this.setPassword(memberDTO.getPassword());
		this.setName(memberDTO.getName());
		this.setGender(memberDTO.getGender());
		this.setAge(memberDTO.getAge());
		this.setEmail(memberDTO.getEmail());
		this.setMobile(memberDTO.getMobile());
		this.setPhone(memberDTO.getPhone());
		this.setZip(memberDTO.getZip());
		this.setRoadAddress(memberDTO.getRoadAddress());
		this.setJibunAddress(memberDTO.getJibunAddress());
		this.setDetailAddress(memberDTO.getDetailAddress());
		this.setBirthday(memberDTO.getBirthday());
		this.setJoindate(memberDTO.getJoindate());
		this.setEnabled(memberDTO.getEnabled());
	}
	
	public MemberUpdateDTO(MemberVO memberDTO) {

		this.setId(memberDTO.getId());
		this.setPassword(memberDTO.getPassword());
		this.setName(memberDTO.getName());
		this.setGender(memberDTO.getGender());
		this.setAge(memberDTO.getAge());
		this.setEmail(memberDTO.getEmail());
		this.setMobile(memberDTO.getMobile());
		this.setPhone(memberDTO.getPhone());
		this.setZip(memberDTO.getZip());
		this.setRoadAddress(memberDTO.getRoadAddress());
		this.setJibunAddress(memberDTO.getJibunAddress());
		this.setDetailAddress(memberDTO.getDetailAddress());
		this.setBirthday(memberDTO.getBirthday());
		this.setJoindate(memberDTO.getJoindate());
		this.setEnabled(memberDTO.getEnabled());
	}

	@Override
	public String toString() {
		return "MemberUpdateDTO [password1=" + password1 + ", password2=" + password2 + ", getId()=" + getId()
				+ ", getPassword()=" + getPassword() + ", getName()=" + getName() + ", getGender()=" + getGender()
				+ ", getEmail()=" + getEmail() + ", getMobile()=" + getMobile() + ", getPhone()=" + getPhone()
				+ ", getAge()=" + getAge() + ", getZip()=" + getZip() + ", getRoadAddress()=" + getRoadAddress() + ", "
				+ "getJibunAddress()=" + getJibunAddress() + "getDetailAddress()=" + getDetailAddress()
				+ ", getBirthday()=" + getBirthday() + ", getJoindate()=" + getJoindate() 
				+ ", getEnabled()="+ getEnabled() + "]";
	}

}