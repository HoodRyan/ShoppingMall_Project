package com.shop.controller;

import com.shop.dto.MemberFormDto;
import com.shop.entity.Member;
import com.shop.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = "/new")
    public String memberForm(Model model){  // 회원가입 페이지로 이동
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/memberForm";
    }

//    @PostMapping(value = "/new")
//    public String memberForm(MemberFormDto memberFormDto){
//
//        Member member = Member.createMember(memberFormDto, passwordEncoder);
//        memberService.saveMember(member);
//
//        return "redirect:/";
//    }

    // 회원 가입 성공시 메인 페이지로 리다이렉트
    // 회원 정보 검증 및 중복 회원 가입 조건에 실패 시 다시 회원 가입 페이지로 돌아가 실패 원인 출력
    @PostMapping(value = "/new")
    public String newMember(@Valid MemberFormDto memberFormDto,
                            BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){ // 에러가 있다면 회원 가입 페이지로 이동
            return "member/memberForm";
        }

        try{
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
        }catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage()); // 중복 회원 가입 예외 발생 시 에러 메시지를 뷰로 전달
            return "member/memberForm";
        }

        return "redirect:/";
    }
}
