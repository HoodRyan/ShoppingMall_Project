package com.shop.service;

import com.shop.entity.Member;
import com.shop.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional  // 로직을 처리하다가 에러가 발생하였다면, 변경된 데이터를 로직을 수행하기 이전 상태로 콜백
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public Member saveMember(Member member){
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) { // 이미 가입된 회원인 경우 예외 발생
        Member findMember = memberRepository.findByEmail(member.getEmail());
        if(findMember != null){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException{ // 로그인 할 유저의 email을 파라미터로 전달
        Member member = memberRepository.findByEmail(email);

        if(member==null){
            throw new UsernameNotFoundException(email);
        }

        return User.builder()   // User 객체 반환. 객체 생성 위해 생성자로 이메일, 비밀번호, role을 파라미터 넘김
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }
}
