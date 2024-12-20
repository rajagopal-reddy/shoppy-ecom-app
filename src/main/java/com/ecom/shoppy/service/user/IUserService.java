package com.ecom.shoppy.service.user;

import com.ecom.shoppy.dto.UserDto;
import com.ecom.shoppy.model.User;
import com.ecom.shoppy.request.CreateUserRequest;
import com.ecom.shoppy.request.UserUpdateRequest;

public interface IUserService {

	User getUserById(Long userId);
	User createUser(CreateUserRequest request);
	User updateUser(UserUpdateRequest request, Long userId);
	void updateUserRole(Long userId, String roleNme);
	void deleteUser(Long userId);
	UserDto convertUserToDto(User user);
}