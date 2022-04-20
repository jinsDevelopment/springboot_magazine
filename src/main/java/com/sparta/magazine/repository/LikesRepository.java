package com.sparta.magazine.repository;

import com.sparta.magazine.model.Likes;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, Long> {

	int countAllByPosts_Id(Long postId);

	Optional<Likes> findAllByPostsIdAndUserId(Long postId, String userId);

}
