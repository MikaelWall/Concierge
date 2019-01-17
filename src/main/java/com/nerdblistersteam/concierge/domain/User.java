package com.nerdblistersteam.concierge.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    @Size(min = 8, max = 20)
    @Column(nullable = false, unique = true)
    @JsonIgnore
    private String email;

    @NonNull
    @Column(length = 100)
    @JsonIgnore
    private String password;

    @NonNull
    @Column(nullable = false)
    @JsonIgnore
    private boolean enabled;

    @OneToOne
    @PrimaryKeyJoinColumn
    @JsonIgnore
    private Schedule schedule;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    @JsonIgnore
    private Set<Role> roles = new HashSet<>();

    @NonNull
    @NotEmpty(message = "You must enter a first name.")
    private String firstName;

    @NonNull
    @NotEmpty(message = "You must enter a last name.")
    private String lastName;

    @Transient
    @Setter(AccessLevel.NONE)
    private String fullName;

    @Transient
    @JsonIgnore
    @NotEmpty(message = "Please enter Password Confirmation")
    private String confirmPassword;

    @JsonIgnore
    private String activationCode;

    public String getFullName(){
        return firstName + " " + lastName;
    }

    public void addRole(Role role) {
        roles.add(role);
    }

//    public void addRoles(Set<Role> roles) {
//        roles.forEach(this::addRole);
//    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return email;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
