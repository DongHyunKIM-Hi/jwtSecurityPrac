package com.example.kongservicesecurity.rest;

import com.example.kongservicesecurity.jwt.JwtUtil;
import com.example.kongservicesecurity.model.GiveRequest;
import com.example.kongservicesecurity.model.User;
import com.example.kongservicesecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @GetMapping("/user/test")
    public String getUserInfo(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @PostMapping("/user/add")
    public String addAdmin(){
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(() -> new IllegalArgumentException("가입되지 않은 사용자 입니다."));
        List<String> authority = user.getRoles();
        authority.add("ROLE_ADMIN");
        user.setRoles(authority);
        userRepository.save(user);
        return jwtUtil.createToken(user.getUsername(), user.getRoles());
    }
    @GetMapping("/admin/show/users")
    public List<User> showAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/admin/show/{user}")
    public String showUserAuthority(@PathVariable("user") String user){
      User member =userRepository.findByEmail(user).orElseThrow(() -> new IllegalArgumentException("가입되지 않은 사용자 입니다."));
      return member.getRoles().toString();
    }

    @PostMapping("/admin/give/authority")
    public String giveAuthority(@RequestBody GiveRequest giveRequest){
        User user = userRepository.findByEmail(giveRequest.getUser()).orElseThrow(()-> new IllegalArgumentException("없는 사용자 입니다."));
        List<String> authority = user.getRoles();
        user.setRoles(giveRequest.getAuthority());
        userRepository.save(user);
        return giveRequest.getUser() + " 해당 아이디에" + giveRequest.getAuthority().toString() + " 권한들이 추가됬습니다.";
    }
}
