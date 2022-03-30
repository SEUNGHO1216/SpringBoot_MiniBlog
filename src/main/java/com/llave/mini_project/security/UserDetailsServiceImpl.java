package com.llave.mini_project.security;

import com.llave.mini_project.models.User;
import com.llave.mini_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    //결국 UserDetailsService라는 스프링시큐리티 인터페이스를 implements한 메소드이다!
    //이걸 통해서 해당 userdetails 상세정보를 뿌려준다(있다면)
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new UsernameNotFoundException("Can't find " + nickname));
        return new UserDetailsImpl(user);

    }
}
