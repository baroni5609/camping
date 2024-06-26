package com.campTeam.webapp.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.campTeam.webapp.domain.CustomUser;
import com.campTeam.webapp.domain.MemberDTO;
import com.campTeam.webapp.domain.MemberUpdateDTO;
import com.campTeam.webapp.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Controller
@SessionAttributes("memberUpdateDTO")
@RequestMapping("/member")
@Slf4j
public class MemberUpdateSessController {
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping("/updateSess.do")
	public String update(Model model, HttpSession session) {
		
		// Spring Security Principal(Session) 조회
		Object principal = SecurityContextHolder.getContext()
											.getAuthentication()
											.getPrincipal();
		
		CustomUser customUser = (CustomUser)principal;
		log.info("principal : {}", principal);
		log.info("id : {}", customUser.getUsername()); // 로그인 아이디
		
		String id = customUser.getUsername();
		
		MemberDTO memberDTO;	
		
		// session.removeAttribute("memberUpdateDTO");
				
		log.info("session memberUpdateDTO : {}", session.getAttribute("memberUpdateDTO"));
		
		if (session.getAttribute("memberUpdateDTO") == null) {
			
			memberDTO = memberService.selectMember(id);
			
			if (memberDTO == null) {
				// 에러 처리
				model.addAttribute("errMsg", "회원 정보가 존재하지 않습니다.");
				return "/error/error";
				
			} else {
				
				log.info("memberDTO : {}", memberDTO);
				
				MemberUpdateDTO memberUpdateDTO = new MemberUpdateDTO(memberDTO);
				log.info("memberUpdateDTO : {}", memberUpdateDTO);
				session.setAttribute("memberUpdateDTO", memberUpdateDTO);
				
				log.info("session memberUpdateDTO : {}", session.getAttribute("memberUpdateDTO"));
			} //
			
		} 
		
		return "/member/updateSess";	
	}
	
	@PostMapping("/updateSessProc.do")
	public String updateProc(@ModelAttribute("memberUpdateDTO") MemberUpdateDTO memberUpdateDTO, 
						RedirectAttributes ra, HttpSession session,
						SessionStatus sessionStatus) {
				
		log.info("updateSessProc.do : {}", memberUpdateDTO);
		
		// Spring Security Pricipal(Session) 조회
		Object principal = SecurityContextHolder.getContext()
											.getAuthentication()
											.getPrincipal();
		
		CustomUser customUser = (CustomUser)principal;
		log.info("principal : {}", principal);
		log.info("id : {}", customUser.getUsername()); // 로그인 아이디
		
		String id = customUser.getUsername();
		memberUpdateDTO.setId(id);
		
		// 신규 패쓰워드가 공백이 아니라면 패쓰워드 변경
		// 공백이면 패쓰워드 변경 의사가 없는 것으로 간주하여 기존 패쓰워드 그대로 사용
		if (memberUpdateDTO.getPassword1().trim().equals("") != true) {
			// 패쓰워드 암호화		
			// 주의) 변경된 패쓰워드(password1 혹은 password2) => 암호화 => 기존 패쓰워드(password)에 대입  
			memberUpdateDTO.setPassword(bCryptPasswordEncoder.encode(memberUpdateDTO.getPassword1()));				
		}
		
		log.info("updateProc.do-1(암호화 이후) : {}", memberUpdateDTO);
		
		boolean result = memberService.updateMember(memberUpdateDTO);
		
		String msg = result == true ? "회원정보 수정에 성공하였습니다." 
					: "회원정보 수정에 실패하였습니다.";
		log.info("result : {}", msg);
		
		// 세션 소멸
//		if (result == true) {
//			session.removeAttribute("memberUpdateDTO"); // 특정 세션 종료
//			// session.invalidate(); // 모든 세션 종료
//		}
		
		// @SessionAttributes("memberUpdateDTO") 활용시
		// 세션 소멸시
		if (sessionStatus.isComplete() == false) {
			sessionStatus.setComplete();
		}
		
		ra.addFlashAttribute("msg", msg);
				
		return "redirect:/member/updateSess.do"; // 임시 조치
		// return "redirect:/welcome";
	}	
	
}