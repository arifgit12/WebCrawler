package in.arifalimondal.reportservice.security;

import in.arifalimondal.reportservice.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CustomUserDetails implements UserDetails {

    private String username;
    private String password;

    private Set<GrantedAuthority> authorities;

    public CustomUserDetails(User userCredential) {
        this.username = userCredential.getName();
        this.password = userCredential.getPassword();
        authorities = new HashSet<>();
    }

    public CustomUserDetails(String username, Set<GrantedAuthority> tokenAuthorities) {
        this.username = username;
        authorities = new HashSet<>();
        authorities.addAll(tokenAuthorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
