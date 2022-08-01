package com.sai.model.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sai.model.dto.reply.CreateReplyRequestDTO;
import com.sai.model.dto.reply.GetReplyResponseDTO;
import com.sai.model.dto.reply.ReplyDTO;
import com.sai.model.dto.reply.UpdateReplyRequestDTO;
import com.sai.model.entity.Board;
import com.sai.model.entity.Reply;
import com.sai.model.entity.User;
import com.sai.model.repository.BoardRepository;
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

	@Override
	public String createReply(Long boardId, CreateReplyRequestDTO createReplyRequestDTO) {
//		Optional<Board> boardItem = boardRepository.findById(id);
//		reply.setBoard(boardItem.get());
//
//		replyRepository.save(reply);
//		return reply;
		System.out.println(LocalTime.now());
//LocalTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
		Board board = boardRepository.findById(boardId).get();
		User user = userRepository.findById(createReplyRequestDTO.getUserId()).get();
		Reply reply = Reply.builder().replyContent(createReplyRequestDTO.getReplyContent()).board(board).user(user)
				.replyRegDateTime(LocalDateTime.now()).build();
		replyRepository.save(reply);

		return "success";

	}

	@Override
	public GetReplyResponseDTO getReply(Long id) {
		GetReplyResponseDTO result = new GetReplyResponseDTO();
		List<Reply> dbResult = replyRepository.findRepliesByBoard(boardRepository.findById(id).get());
		List<ReplyDTO> list = new ArrayList<ReplyDTO>();
		// for문 돌려서 Reply -> ReplyDTO 변환시킨 후 list에 삽입
		for (Reply reply : dbResult) {
			ReplyDTO item = new ReplyDTO(reply, userRepository.findById(reply.getUser().getUserId()).get(),
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
	public void updateReply(UpdateReplyRequestDTO updateReplyRequestDTO) {
		Reply reply = replyRepository.findById(updateReplyRequestDTO.getReplyId()).get();
		reply.update(updateReplyRequestDTO.getReplyContent());
	}
}
