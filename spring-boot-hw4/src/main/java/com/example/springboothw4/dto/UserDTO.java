package com.example.springboothw4.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	private Long id;
	private String login;
	private String nickname;
	private List<ProductDTO> productDTOs;
}
