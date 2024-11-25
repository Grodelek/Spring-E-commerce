package org.project.ecommerce.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@Getter
@Setter
@Table(name = "users")
public class User implements UserDetails{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Column(name = "username")
        private String username;
        @Column(name = "password")
        private String password;
        @Column(name = "enabled")
        private int enabled;
        @OneToOne(mappedBy = "user")
        private Cart cart;
        @ElementCollection(fetch = FetchType.EAGER)
        @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "users_id"))
        @Column(name = "role")
        private List<String> roles;

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
                return roles.stream()
                        .map(role -> new SimpleGrantedAuthority(role))
                        .collect(Collectors.toList());
        }


        @Override
        public boolean isAccountNonExpired() {
                return UserDetails.super.isAccountNonExpired();
        }

        @Override
        public boolean isAccountNonLocked() {
                return UserDetails.super.isAccountNonLocked();
        }

        @Override
        public boolean isCredentialsNonExpired() {
                return UserDetails.super.isCredentialsNonExpired();
        }

        @Override
        public boolean isEnabled() {
              return enabled == 1;
        }
}
