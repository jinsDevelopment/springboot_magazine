package com.sparta.magazine.model;

import com.sparta.magazine.dto.PostRequestDto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter // get 함수를 일괄적으로 만들어줍니다.
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
public class Posts extends Timestamped{

	// ID가 자동으로 생성 및 증가합니다.
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	// nullable: null 허용 여부
	@Column(columnDefinition = "TEXT", nullable = false)
	private String contents;

	@Column(columnDefinition = "mediumtext")
	private String imagePath;

	@Column(nullable = false)
	private String layout;

	@OneToMany(mappedBy="posts", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Likes> likes = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	public Posts(PostRequestDto requestDto, String userId){
		this.contents = requestDto.getContents();
		this.imagePath = requestDto.getImagePath();
		this.layout = requestDto.getLayout();
		this.user = new User();
		this.user.setId(userId);
	}

	public void update(PostRequestDto requestDto) {
		this.imagePath = requestDto.getImagePath();
		this.contents = requestDto.getContents();
		this.layout = requestDto.getLayout();
	}

}
