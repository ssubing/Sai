package com.sai.model.dto;

import java.util.List;

import com.sai.model.dto.board.ViewBoardResponseDto;
import com.sai.model.dto.boardMedia.ViewBoardMediaResponseDto;
import com.sai.model.dto.boardTagged.ViewBoardTaggedResponseDto;
import com.sai.model.dto.poll.ViewPollResponseDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReadBoardResponseDto {
	private ViewBoardResponseDto viewBoardResponseDto;
	private List<ViewBoardMediaResponseDto> viewBoardMediaResponseDto;
	private ViewPollResponseDto viewPollResponseDto;
	private List<ViewBoardTaggedResponseDto> viewBoardTaggedResponseDto;
	private boolean isBoardLiked;
}
