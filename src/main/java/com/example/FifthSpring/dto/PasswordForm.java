package com.example.FifthSpring.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PasswordForm {

    @NotBlank(message = "기존 패스워드를 입력해주세요")
    private String old;

    @Size(min=8,message = "8글자 이상 입력해주세요")
    @NotBlank(message = "새로운 패스워드를 입력해주세요")
    private String password;

    @Size(min=8,message = "8글자 이상 입력해주세요")
    @NotBlank(message = "새로운 패스워드를 입력해주세요")
    private String passwordConfirm;
}
