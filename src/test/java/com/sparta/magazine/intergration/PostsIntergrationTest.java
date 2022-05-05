package com.sparta.magazine.intergration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.sparta.magazine.advice.RestException;
import com.sparta.magazine.dto.PostRequestDto;
import com.sparta.magazine.dto.PostResponseDto;
import com.sparta.magazine.model.Posts;
import com.sparta.magazine.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PostsIntergrationTest {

// 	@Autowired
// 	PostService postService;

// 	String userId = "aaa";		//user가 있다고 가정
// 	Posts createPosts = null;
// 	@Test
// 	@Order(1)
// 	@DisplayName("신규 게시글 추가")
// 	void createPosts(){
// 		// given

// 		String contents = "내용 테스트";
// 		String layout = "top";
// 		String imagePath = "이미지 경로";

// 		PostRequestDto requestDto = new PostRequestDto(
// 			contents,
// 			imagePath,
// 			layout
// 		);

// // when
// 		Posts posts = postService.postSave(requestDto, userId);

// // then
// 		assertNotNull(posts.getId());
// 		assertEquals(userId, posts.getUser().getId());
// 		assertEquals(contents, posts.getContents());
// 		assertEquals(imagePath, posts.getImagePath());
// 		assertEquals(layout, posts.getLayout());
// 		createPosts = posts;
// 		assertEquals(createPosts,posts);

// 	}

// 	@Test
// 	@Order(2)
// 	@DisplayName("게시글 수정")
// 	void modifyPosts(){
// 		// given
// 		String contents = "내용 테스트 수정";
// 		String layout = "bottom";
// 		String imagePath = "이미지 경로 수정";

// 		PostRequestDto requestDto = new PostRequestDto(
// 			contents,
// 			imagePath,
// 			layout
// 		);

// // when
// 		Posts posts = postService.modify(createPosts.getId(),requestDto, userId);

// // then
// 		assertNotNull(posts.getId());
// 		assertEquals(userId, posts.getUser().getId());
// 		assertEquals(contents, posts.getContents());
// 		assertEquals(imagePath, posts.getImagePath());
// 		assertEquals(layout, posts.getLayout());
// 		createPosts = posts;
// 		assertEquals(createPosts,posts);

// 	}

// 	@Test
// 	@Order(3)
// 	@DisplayName("게시글 삭제")
// 	void deletePosts(){
// 		// given
// 		Long postId = createPosts.getId();

// 		// when
// 		postService.delete(postId, userId);

// 		// then
// 		Exception exception = assertThrows(RestException.class, () -> {
// 			postService.getBoardDetail(postId,userId);
// 		});

// 		assertEquals("해당 postId가 존재하지 않습니다.",exception.getMessage());

// 	}

}
