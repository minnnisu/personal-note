package minnnisu.personalnote.service;

import lombok.RequiredArgsConstructor;
import minnnisu.personalnote.constant.ErrorCode;
import minnnisu.personalnote.domain.User;
import minnnisu.personalnote.exception.CustomErrorException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws CustomErrorException {
            User user = userService.findByUsername(username);
            if (user == null) {
                throw new CustomErrorException(ErrorCode.UserNotFoundException);
            }
            return user;
        };
}
