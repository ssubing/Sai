package com.sai.model.entity.poll;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChoiceVoteCount {
	private Long choiceId;
	private Long voteCount;

}
