package com.example.medicinebackend.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberResponseDto {
    private String nickname;
    private String profileImage;

}
