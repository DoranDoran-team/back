package com.korit.dorandoran.entity;

import com.korit.dorandoran.dto.request.auth.SignUpRequestDto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="user")
@Table(name="user")
@ToString
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
	private String mileage;
    private String statusMessage;

	public UserEntity(SignUpRequestDto dto) {
        this.userId = dto.getUserId();
        this.password = dto.getPassword();
        this.name = dto.getName();
        this.telNumber = dto.getTelNumber();
        this.joinPath = dto.getJoinPath();
        this.snsId = dto.getSnsId();
		this.birth = dto.getBirth();
    }
}
