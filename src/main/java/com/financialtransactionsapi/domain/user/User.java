package com.financialtransactionsapi.domain.user;

import com.financialtransactionsapi.dtos.UserDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity(name="users")
@Table(name="users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="userId")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String userDocument;
    @Column(unique = true)
    private String email;
    private String password;
    private BigDecimal balance;
    @Enumerated(EnumType.STRING)
    private UserType userType;
    
    public User(UserDTO data) {
        this.firstName = data.firstName();
        this.lastName = data.lastName();
        this.userDocument = data.document();
        this.balance = data.balance();
        this.userType = data.userType();
        this.email = data.email();
        this.password = data.password();
    }
}
