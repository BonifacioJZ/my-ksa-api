package com.bonifacio.my_ksa_api.persistence.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column
    @NotEmpty
    @NotBlank
    @Size(max = 32)
    private String username;
    @Column
    @Email
    @NotBlank
    @NotEmpty
    private String email;
    @ManyToMany(fetch = FetchType.EAGER,targetEntity = RoleEntity.class,cascade = {CascadeType.DETACH})
    @JoinTable(name = "user_role",joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles;
    @Column
    @NotEmpty
    @NotBlank
    private String password;
    @Column(name = "create_at")
    @CreationTimestamp
    private Instant createAt;
    @UpdateTimestamp
    @Column(name = "update_at")
    private Instant updateAt;
    @Column(name = "is_enabled")
    private boolean isEnabled;
    @Column(name = "account_No_Expired")
    private boolean accountNoExpired;
    @Column(name = "account_No_Locked")
    private boolean accountNoLocked;
    @Column(name = "credential_No_Expired")
    private boolean credentialNoExpired;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        this.getRoles().forEach(role->{
            authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRole().name())));
        });
        this.getRoles().stream().flatMap(role-> role.getPermissions().stream())
                .forEach(permission->{
                    authorityList.add(new SimpleGrantedAuthority(permission.getPermission()));
                });
        return authorityList;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNoExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNoLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialNoExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }
}
