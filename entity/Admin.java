package com.wei.entity;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    private int adminId;
    private String adminName;
    private String adminSex;
    private String adminPhone;
    private String adminEmail;
}
