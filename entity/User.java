package com.wei.entity;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {
	private int userId;
	private int returnById;
	private String userName;
	private String userSex;
	private String userPhone;
	private String userAccount;


	public User(int userId, String userName, String userSex, String userPhone) {
		this.userId = userId;
		this.userName = userName;
		this.userSex = userSex;
		this.userPhone = userPhone;
	}

	public User(String userName, String userSex, String userPhone, String userAccount) {
		this.userName = userName;
		this.userSex = userSex;
		this.userPhone = userPhone;
		this.userAccount = userAccount;
	}

	public User(int userId, String userName, String userSex, String userPhone, String userAccount) {
		this.userId = userId;
		this.userName = userName;
		this.userSex = userSex;
		this.userPhone = userPhone;
		this.userAccount = userAccount;
	}
}
