package com.cinema.dto.member;

import com.cinema.domain.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MemberJoinDTO {
    private String id;
    private String password;
    private String email;
    private String name;
    private String birth;
    private String phone;
    private int years14More;
    private int useTermsAgree;
    private int personalInfoAgree;

    private Date joinDate;

    public Member toEntity() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MM/dd");
        String formattedDate = LocalDate.now().format(formatter); // 오늘 날짜 포맷팅
        return Member.builder()
                .id(id)
                .password(password)
                .email(email)
                .name(name)
                .birth(birth)
                .phone(phone)
                .years14More(years14More)
                .useTermsAgree(useTermsAgree)
                .personalInfoAgree(personalInfoAgree)
                .joinDate(formattedDate)
                .build();
    }

}
