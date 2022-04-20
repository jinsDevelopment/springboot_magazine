package com.sparta.magazine.repository;

import com.sparta.magazine.model.Likes;
import com.sparta.magazine.model.Posts;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Posts, Long> {

	List<Posts> findAllByOrderByCreatedAtDesc();

}
