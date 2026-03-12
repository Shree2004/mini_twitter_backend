package java.repository;

import java.entity.Like;
import java.entity.Post;
import java.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByUserAndPost(User user, Post post);
    Long countByPost(Post post);
    Optional<Like> findByUserAndPost(User user, Post post);
}
