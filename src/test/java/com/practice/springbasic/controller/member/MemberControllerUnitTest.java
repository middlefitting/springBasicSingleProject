package com.practice.springbasic.controller.member;

import com.practice.springbasic.controller.member.dto.EmailCheckForm;
import com.practice.springbasic.controller.member.dto.NicknameCheckForm;
import com.practice.springbasic.controller.utils.CheckUtil;
import com.practice.springbasic.domain.member.Member;
import com.practice.springbasic.service.member.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class MemberControllerUnitTest {

    @Mock
    MemberService memberService;
    @InjectMocks
    MemberControllerImpl memberController;
    private Member member;
    private Member FailedEmailParsingMember;
    private Member FailedPasswordParsingMember;
    private Member FailedNicknameParsingMember;

    @BeforeEach
    public void initialize() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    public void createMember() {
        member = memberSample("middlefitting@google.com", "%middlefitting", "middlefitting");
        FailedEmailParsingMember = memberSample("middle", "%middlefitting", "middlefitting");
        FailedPasswordParsingMember = memberSample("middlefitting@google.com", "%mid", "middlefitting");
        FailedNicknameParsingMember = memberSample("middlefitting@google.com", "%middlefitting", "");
    }

    private Member memberSample(String email, String password, String nickname) {
        return Member.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .build();
    }

    @Test
    public void duplicateNicknameCheckFailed() throws Exception{
        when(memberService.duplicateNickname(ArgumentMatchers.anyString())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> {memberController.duplicateNicknameCheck(new NicknameCheckForm(member.getNickname()));});
    }

    @Test
    public void duplicateNicknameCheckSuccess() throws Exception{
        when(memberService.duplicateNickname(ArgumentMatchers.anyString())).thenReturn(false);

        memberController.duplicateNicknameCheck(new NicknameCheckForm(member.getNickname()));
    }

    @Test
    public void duplicateEmailCheckFailed() throws Exception{
        when(memberService.duplicateEmail(ArgumentMatchers.anyString())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> {memberController.duplicateEmailCheck(new EmailCheckForm(member.getNickname()));});
    }

    @Test
    public void duplicateEmailCheckSuccess() throws Exception{
        when(memberService.duplicateEmail(ArgumentMatchers.anyString())).thenReturn(false);

        memberController.duplicateEmailCheck(new EmailCheckForm(member.getNickname()));
    }

    @Test
    public void bindingResultHaveError() throws Exception{
        assertThrows(IllegalArgumentException.class, () -> {CheckUtil.bindingResultCheck(true);});
    }

    @Test
    public void bindingResultDontHaveError() throws Exception{
        CheckUtil.bindingResultCheck(false);
    }

    @Test
    public void memberNullCheckFailed() throws Exception{
        assertThrows(IllegalArgumentException.class, () -> {memberController.memberNullCheck(null);});
    }

    @Test
    public void memberNullCheckSuccess() throws Exception{
        memberController.memberNullCheck(member);
    }
}
