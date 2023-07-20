package com.example.webstore.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoBo {
    private Long id;
    private String account;
    private String password;
    private String touxiang;
    private String introduction;
    private String wechat;
    private String goodsaddress;
    private String nickname;
}
