package com.ecom.shoppy.controller;

import com.ecom.shoppy.dto.UserDto;
import com.ecom.shoppy.exception.AlreadyExistsExceptoin;
import com.ecom.shoppy.exception.ResourceNotFoundException;
import com.ecom.shoppy.model.User;
import com.ecom.shoppy.request.CreateUserRequest;
import com.ecom.shoppy.request.UserUpdateRequest;
import com.ecom.shoppy.response.ApiResponse;
import com.ecom.shoppy.service.user.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {

	private final IUserService userService;
	
	@GetMapping("/{userId}")
	public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId){
		try {
			User user = userService.getUserById(userId);
			UserDto userDto = userService.convertUserToDto(user);
			return ResponseEntity.ok(new ApiResponse("Success", userDto));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@PostMapping("/add")	
	public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request){
		try {
			User user = userService.createUser(request);
			UserDto userDto = userService.convertUserToDto(user);
			return ResponseEntity.ok(new ApiResponse("Created user success !", userDto));
			
		} catch (AlreadyExistsExceptoin e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
		}		
	}
	
	@PostMapping("/update/{userId}")
	public ResponseEntity<ApiResponse> updateUser(@RequestBody UserUpdateRequest request, @PathVariable Long userId){
		try {
			User user = userService.updateUser(request, userId);
			UserDto userDto = userService.convertUserToDto(user);
			return ResponseEntity.ok(new ApiResponse("Update user success !", userDto));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), null));
		}
		
	}

	@PostMapping("/update-role")
	public ResponseEntity<String> updateUserRole(@RequestParam Long userId,
												 @RequestParam String roleName) {
		userService.updateUserRole(userId, roleName);
		return ResponseEntity.ok("User role updated");
	}
	
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId){
		try {
			userService.deleteUser(userId);
			return ResponseEntity.ok(new ApiResponse("Delete user success !", null));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), null));
		}
		
	}
	
}