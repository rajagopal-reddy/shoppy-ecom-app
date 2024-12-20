package com.ecom.shoppy.dto;

import com.ecom.shoppy.model.Role;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private Long userId;
    private String userName;
    private String email;
    private List<OrderDto> orders;
    private CartDto cart;
    private boolean accountNonLocked;
    private boolean accountNonExpired;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private LocalDate credentialExpiryDate;
    private LocalDate accountExpiryDate;
    private String twoFactorSecret;
    private boolean isTwoFactorEnabled;
    private String signUpMethod;
    private Role role;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}