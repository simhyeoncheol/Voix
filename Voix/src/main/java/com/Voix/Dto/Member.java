package com.Voix.Dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class Member {
	private String mid;
	private String mpw;
	private String mname;
	private String maddr;
	private String memail;
	private String mimg;
	private String mstate;
	
	private MultipartFile mfile;
}
