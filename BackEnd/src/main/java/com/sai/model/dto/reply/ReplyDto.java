package com.sai.model.dto.reply;


import java.time.LocalDateTime;

import com.sai.model.entity.Board;
import com.sai.model.entity.Reply;
import com.sai.model.entity.User;

import lombok.Data;

@Data
public class ReplyDTO {
	private Long replyId;

	private String replyContent;

	private LocalDateTime replyRegDateTime;

	private String userId;

	// 프로필 사진 이미지 경로
	private String userImagePath;

	// 프로필 사진 이미지 이름
	private String userImageName;

	// 프로필 사진 이미지 속성
	private String userImageType;

	private Long boardId;

	// Entity to DTO
	public ReplyDTO(Reply reply, User user, Board board) {
		this.replyId = reply.getReplyId();
		this.replyContent = reply.getReplyContent();
		this.replyRegDateTime = reply.getReplyRegDateTime();
		this.userId = user.getUserId();
		this.userImagePath = user.getUser_image_path();
		this.userImageName = user.getUser_image_name();
		this.userImageType = user.getUser_image_type();
		this.boardId = board.getBoardId();
	}

}
