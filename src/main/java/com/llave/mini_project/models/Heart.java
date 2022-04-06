package com.llave.mini_project.models;

import com.llave.mini_project.dto.BlogRequestDto;
import com.llave.mini_project.security.UserDetailsImpl;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
public class Heart {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long Id;

    @JoinColumn(name = "userId", nullable = false)
    @ManyToOne()
    private User user;

    @JoinColumn(name="blogId", nullable = false)
    @ManyToOne
    private Blog blog;

    public Heart(User user, Blog blog){
        this.user=user;
        this.blog=blog;
    }

}