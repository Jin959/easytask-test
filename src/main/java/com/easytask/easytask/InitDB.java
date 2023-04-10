package com.easytask.easytask;

import com.easytask.easytask.src.user.repository.UserRepository;
import com.easytask.easytask.src.user.entity.Role;
import com.easytask.easytask.src.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class InitDB {
    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.initUser();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;

        public void initUser() {
            User user = User.builder()
                    .email("admin@admin.com")
                    .password(passwordEncoder.encode("admin"))
                    .name("관리자")
                    .role(Role.ROLE_ADMIN)
                    .build();

            userRepository.save(user);
        }
    }

}
