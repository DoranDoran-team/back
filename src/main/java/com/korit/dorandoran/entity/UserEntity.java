package com.korit.dorandoran.entity;

import com.korit.dorandoran.dto.request.auth.ChangePwRequestDto;
import com.korit.dorandoran.dto.request.auth.SignUpRequestDto;
import com.korit.dorandoran.dto.response.auth.GetSignInResponseDto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user")
@Table(name = "user")
public class UserEntity {

	@Id
	private String userId;

	private String password;
	private String name;
	private String telNumber;
	private String birth;
	private String profileImage;
	private String joinPath;
	private String snsId;
	private String nickName;
	private Boolean role;
	private Integer mileage;
	private String statusMessage;

	// 신고 관련
	private Integer accuseCount;
	private Boolean accuseState;
	private String accuseTime;

	public UserEntity(SignUpRequestDto dto) {
		this.userId = dto.getUserId();
		this.password = dto.getPassword();
		this.name = dto.getName();
		this.telNumber = dto.getTelNumber();
		this.joinPath = dto.getJoinPath();
		this.snsId = dto.getSnsId();
		this.birth = dto.getBirth();
		this.nickName = dto.getNickName();
		this.role = dto.getRole();
		this.accuseCount = dto.getAccuseCount();
		this.mileage = dto.getMileage();
	}

	public UserEntity(ChangePwRequestDto dto) {
		this.userId = dto.getUserId();
		this.telNumber = dto.getTelNumber();
		this.password = dto.getPassword();
	}

	public UserEntity(GetSignInResponseDto dto) {
		this.userId = dto.getUserId();
		this.profileImage = dto.getProfileImage();
		this.name = dto.getName();
		this.telNumber = dto.getTelNumber();
		this.nickName = dto.getNickName();
		this.role = dto.getRole();
		this.mileage = dto.getMileage();
		this.statusMessage = dto.getStatusMessage();
		this.accuseCount = dto.getAccuseCount();
		this.accuseState = dto.getAccuseState();
		this.accuseTime = dto.getAccuseTime();
	}

}
