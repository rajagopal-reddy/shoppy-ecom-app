package com.ecom.shoppy.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users",
		uniqueConstraints = {
				@UniqueConstraint(columnNames = "username"),
				@UniqueConstraint(columnNames = "email")
		})
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long userId;

	private String firstName;
	private String lastName;

	@NotBlank
	@Size(max = 20)
	@Column(name = "username", nullable = false, unique = true)
	private String userName;

	@NotBlank
	@Size(max = 50)
	@Email
	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@NotBlank
	@Size(max = 120)
	@Column(name = "password", nullable = false)
	@JsonIgnore
	private String password;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Cart cart;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Order> orders;

	@ManyToMany(fetch = FetchType.EAGER, cascade = {
			CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
	})
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Collection<Role> roles = new HashSet<>();

	@Column(name = "account_non_locked", nullable = false)
	private boolean accountNonLocked = true;

	@Column(name = "account_non_expired", nullable = false)
	private boolean accountNonExpired = true;

	@Column(name = "credentials_non_expired", nullable = false)
	private boolean credentialsNonExpired = true;

	@Column(name = "enabled", nullable = false)
	private boolean enabled = true;

	@Column(name = "credentials_expiry_date")
	private LocalDate credentialsExpiryDate;

	@Column(name = "account_expiry_date")
	private LocalDate accountExpiryDate;

	@Column(name = "two_factor_secret")
	private String twoFactorSecret;

	@Column(name = "is_two_factor_enabled", nullable = false)
	private boolean isTwoFactorEnabled = false;

	@Column(name = "sign_up_method")
	private String signUpMethod;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "role_id", referencedColumnName = "role_id")
	@JsonBackReference
	@ToString.Exclude
	private Role role;

	@CreationTimestamp
	@Column(name = "created_date", updatable = false)
	private LocalDateTime createdDate;

	@UpdateTimestamp
	@Column(name = "update_date")
	private LocalDateTime updateDate;

	public User(String userName, String email, String password) {
		this.userName = userName;
		this.email = email;
		this.password = password;
	}

	public User(String userName, String email) {
		this.userName = userName;
		this.email = email;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof User)) return false;
		User user = (User) o;
		return userId != null && userId.equals(user.userId);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}