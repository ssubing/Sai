package com.sai.model.dto.notification;

import lombok.Data;

@Data
public class CreateNotificationRequestDTO {
	private String notiToUserId;
	
	private String notiFromUserId;
	
	private String notiContent;
	
	private String notiType;
	
	private Long notiContentId;
}
