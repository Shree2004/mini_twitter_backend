package java.service;


import java.entity.Comment;
import java.entity.Post;
import java.entity.User;
import java.repository.CommentRepository;
import java.repository.PostRepository;
import java.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    public Comment createComment(Long userId, Long postId, String content){

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User Not Found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new RuntimeException("Post not Found"));

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUser(user);
        comment.setPost(post);

        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByPost(Long postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new RuntimeException("Post not Found"));

        return commentRepository.findByPostPostId(postId);
    }

    public void deleteComment(Long userId, Long postId, Long commentId){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User Not Found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new RuntimeException("Post not Found"));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()->new RuntimeException("Comment not found"));

        if (!comment.getUser().getUserId().equals(userId)) {
            throw new RuntimeException("You cannot delete someone else's comment");
        }

        if (!comment.getPost().getPostId().equals(postId)) {
            throw new RuntimeException("Comment does not belong to this post");
        }

        commentRepository.delete(comment);

    }

}
