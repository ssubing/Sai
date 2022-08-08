package com.sai.model.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sai.model.dto.notification.CreateNotificationRequestDto;
import com.sai.model.dto.reply.CreateReplyRequestDto;
import com.sai.model.dto.reply.GetReplyResponseDto;
import com.sai.model.dto.reply.ReplyDto;
import com.sai.model.dto.reply.UpdateReplyRequestDto;
import com.sai.model.entity.Board;
import com.sai.model.entity.NotiType;
import com.sai.model.entity.Reply;
import com.sai.model.entity.User;
import com.sai.model.repository.BoardRepository;
import com.sai.model.repository.NotificationRepository;
import com.sai.model.repository.ReplyRepository;
import com.sai.model.repository.UserRepository;

@Service
@Transactional
public class ReplyServiceImpl implements ReplyService {
	@Autowired
	ReplyRepository replyRepository;

	@Autowired
	BoardRepository boardRepository;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	NotificationRepository notiRepository;
	
	@Autowired
	NotificationService notiService;
	
	

	@Override
	public String createReply(Long boardId, CreateReplyRequestDto createReplyRequestDTO) {
		Board board = boardRepository.findById(boardId).get();
		User user = userRepository.findById(createReplyRequestDTO.getUserId()).get();
		Reply reply = Reply.builder().replyContent(createReplyRequestDTO.getReplyContent()).board(board).user(user)
				.replyRegDateTime(LocalDateTime.now()).build();
		replyRepository.save(reply);
		
		// 댓글 작성 후 알림 발송
		// 게시글 주인에게 댓글 작성자가 보낸다.
		// 타입은 댓글이다.
		
		CreateNotificationRequestDto cnrd = 
				CreateNotificationRequestDto.builder()
											.notiToUserId(board.getUser().getUserId())
											.notiFromUserId(createReplyRequestDTO.getUserId())
											.notiContent(createReplyRequestDTO.getReplyContent())
											.notiType(NotiType.COMMENT)
											.build();
		
		notiService.createNoti(cnrd);

		return "success";

	}

	@Override
	public GetReplyResponseDto getReply(Long id, Pageable pageable) {
		GetReplyResponseDto result = new GetReplyResponseDto();
		List<Reply> dbResult = replyRepository.findRepliesByBoard(boardRepository.findById(id).get(), pageable);
		List<ReplyDto> list = new ArrayList<ReplyDto>();
		// for문 돌려서 Reply -> ReplyDTO 변환시킨 후 list에 삽입
		for (Reply reply : dbResult) {
			ReplyDto item = new ReplyDto(reply, userRepository.findById(reply.getUser().getUserId()).get(),
					boardRepository.findById(reply.getBoard().getBoardId()).get());
			list.add(item);
		}
		result.setReplyList(list);
		return result;
	}

	@Override
	public String getUserIdByReplyId(Long replyId) {
//		System.out.println("조회 디버깅");
		String userId = replyRepository.findById(replyId).get().getUser().getUserId();
		System.out.println(userId);
		return userId;
	}

	@Override
	public void deleteReplyByReplyId(Long replyId) {
		replyRepository.deleteById(replyId);
	}

	@Override
	public void updateReply(UpdateReplyRequestDto updateReplyRequestDTO) {
		Reply reply = replyRepository.findById(updateReplyRequestDTO.getReplyId()).get();
		reply.update(updateReplyRequestDTO.getReplyContent());
	}
}
