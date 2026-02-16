package dev.nelit.server.security;

import dev.nelit.server.dto.user.UserResponseDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TelegramUserDetails implements UserDetails {

    private final UserResponseDTO user;

    private final List<GrantedAuthority> authorities;

    public TelegramUserDetails(UserResponseDTO user, List<String> roleNames) {
        this.user = user;
        this.authorities = roleNames.stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

        if (this.authorities.isEmpty()) {
            this.authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    public UserResponseDTO getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return user.getTelegramData().getTelegramId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}