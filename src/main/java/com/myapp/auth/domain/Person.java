package com.myapp.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Person {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(unique = true)
    private String email;
    private String name;
    private String password;
    private String role = "USER";
    private boolean enabled = true;
    private LocalDate birthday;
    @Column(insertable = true,updatable = false)
    private LocalDateTime created;
    private LocalDateTime modified;

    @PrePersist
    void prePersist(){
        this.setCreated(LocalDateTime.now());
    }

    @PreUpdate
    void onPreUpdate(){
        this.setModified(LocalDateTime.now());
    }

}
