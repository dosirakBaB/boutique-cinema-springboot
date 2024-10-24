package com.cinema.service;

import com.cinema.domain.Member;
import com.cinema.domain.MemberRole;
import com.cinema.dto.member.MemberJoinDTO;
import com.cinema.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class MemberServiceImpl  implements MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public MemberServiceImpl(MemberRepository memberRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.memberRepository = memberRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

    }

    // 저장 메서드
    @Transactional
    public void save(MemberJoinDTO dto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd");
        String formattedDate = LocalDate.now().format(formatter); // 오늘 날짜 포맷팅
        Member member = Member.builder()
                .id(dto.getId())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .name(dto.getName())
                .birth(dto.getBirth())
                .phone(dto.getPhone())
                .years14More(dto.getYears14More())
                .useTermsAgree(dto.getUseTermsAgree())
                .personalInfoAgree(dto.getPersonalInfoAgree())
                .joinDate(formattedDate)
                .build();

        if ("admin".equals(dto.getId())) {
            member.addRole(MemberRole.ADMIN);
        } else {
            member.addRole(MemberRole.USER);
        }
        memberRepository.save(member); // Member 객체 저장
    }

    // 아이디 중복 체크 메서드
    public boolean findById(String id) {
        Optional<Member> member = memberRepository.findById(id);
        return member.isEmpty(); // 존재하지 않으면 true, 존재하면 false
    }

 //회원리스트
    @Override
    public Page<Member>findAllMembers(Pageable pageable) throws Exception {
        return memberRepository.findAll(pageable); //페이지 점보를 포함
    }

     @Override
    public Page<Member> findBySearchCondition(String condition, Pageable pageable) throws Exception {
        // 조건에 따른 조회 로직 구현
        return memberRepository.findByCondition(condition, pageable);
    }

     @Override
    public void updateTreatedStatus(String id, Integer isTreated) {
        //회원을 ID로 조회
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found"));
         //우대 여부 업데이트
        member.setIsTreated(isTreated);
         //업데이트된 회원 정보 저장
        memberRepository.save(member); // 업데이트된 회원 정보 저장
    }

}
