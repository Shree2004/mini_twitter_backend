package java.repository;

import java.entity.Post;
import java.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUser(User user);
    List<Post> findByUserOrderByCreatedAtDesc(User user);
    List<Post> findAllByOrderByCreatedAtDesc(List<Long> followedUserIds);
    List<Post> findByContentContainingIgnoreCase(String keyword);
    List<Post> findByUserUserIdIn(List<Long> userIds);
    List<Post> findByUser_UserIdInOrderByCreatedAtDesc(List<Long> userIds);
}