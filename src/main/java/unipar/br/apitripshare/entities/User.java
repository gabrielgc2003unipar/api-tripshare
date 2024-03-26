package unipar.br.apitripshare.entities;

import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import unipar.br.apitripshare.dto.LoginRequestDTO;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", updatable = false, nullable = false)
    private UUID id;
    @Column(unique = true)
    private String username;
    private String password;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<unipar.br.apitripshare.entities.Role> roles;

    public User() {
    }

    public User(UUID id, String username, String password, Set<unipar.br.apitripshare.entities.Role> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<unipar.br.apitripshare.entities.Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<unipar.br.apitripshare.entities.Role> roles) {
        this.roles = roles;
    }

    public boolean isLoginCorrect(LoginRequestDTO loginRequestDTO, BCryptPasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(loginRequestDTO.password(), this.password);
    }

    public enum Role {
        BASIC(1L),
        ADMIN(2L);
        long id;

        Role(long l) {
        }

        void Values(long id) {
            this.id = id;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }
    }
}
