package com.easytask.easytask.src.user.login;
import com.easytask.easytask.common.exception.BaseException;
import com.easytask.easytask.src.user.repository.UserRepository;
import com.easytask.easytask.src.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.easytask.easytask.common.response.BaseResponseStatus.REGISTERED_USER;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new BaseException(REGISTERED_USER));

        System.out.println("username : " + username);
        if(user != null) {
            return new PrincipalDetails(user);
        }
        return null;
    }

}
