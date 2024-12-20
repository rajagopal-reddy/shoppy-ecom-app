package com.ecom.shoppy.service.user;

import com.ecom.shoppy.dto.UserDto;
import com.ecom.shoppy.enums.AppRole;
import com.ecom.shoppy.exception.AlreadyExistsExceptoin;
import com.ecom.shoppy.exception.ResourceNotFoundException;
import com.ecom.shoppy.model.Role;
import com.ecom.shoppy.model.User;
import com.ecom.shoppy.repository.RoleRepository;
import com.ecom.shoppy.repository.UserRepository;
import com.ecom.shoppy.request.CreateUserRequest;
import com.ecom.shoppy.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final ModelMapper modelMapper;
	
	@Override
	public User getUserById(Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not foud !"));
	}

	@Override
	public User createUser(CreateUserRequest request) {
		return Optional.of(request)
				.filter(user -> !userRepository.existsByEmail(request.getEmail()))
				.map(req -> {
					User user = new User();
					user.setEmail(request.getEmail());
					user.setPassword(request.getPassword());
					user.setFirstName(request.getFirstName());
					user.setLastName(request.getLastName());
					return userRepository.save(user);
				})
				.orElseThrow(() -> new AlreadyExistsExceptoin(request.getEmail() + " already exists !"));
	}

	@Override
	public User updateUser(UserUpdateRequest request, Long userId) {

		return userRepository.findById(userId)
				.map(existingUser -> {
					existingUser.setFirstName(request.getFirstName());
					existingUser.setLastName(request.getLastName());
					return userRepository.save(existingUser);
				})
				.orElseThrow(() -> new ResourceNotFoundException("User not found !"));
		
	}

	@Override
	public void updateUserRole(Long userId, String roleName) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
		AppRole appRole;
		try {
			appRole = AppRole.valueOf(roleName);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Invalid role name: " + roleName);
		}
		Role role = roleRepository.findByRoleName(appRole)
				.orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
		user.setRole(role);
		userRepository.save(user);
	}

	@Override
	public void deleteUser(Long userId) {
		userRepository.findById(userId).ifPresentOrElse(userRepository :: delete, () -> {
			throw new ResourceNotFoundException("User not found !");
		});
	}
	
	@Override
	public UserDto convertUserToDto(User user) {
		return modelMapper.map(user, UserDto.class);
	}

	
	
	
	
	
	
	
	
	
}