package com.sai.model.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Family {

	@Id
	@Column(name = "family_id")
	private String familyId;

	@Column(name = "family_name")
	private String familyName;

	@Column(name = "family_image_path")
	private String familyImagePath;

	@Column(name = "family_image_name")
	private String familyImageName;

	@Column(name = "family_image_type")
	private String familyImageType;

	@Builder.Default
	@OneToMany(mappedBy = "family", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<User> users = new ArrayList<>();

	public void addUser(User user) {
		this.users.add(user);
		if (user.getFamily() != this)
			user.setFamily(this);
	}

}
