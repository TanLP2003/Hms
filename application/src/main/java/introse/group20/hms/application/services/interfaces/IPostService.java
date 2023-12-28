package introse.group20.hms.application.services.interfaces;

import introse.group20.hms.core.entities.Post;
import introse.group20.hms.core.exceptions.BadRequestException;
import introse.group20.hms.core.exceptions.NotFoundException;

import java.util.List;
import java.util.UUID;

public interface IPostService {
    List<Post> getAll();
    List<Post> getPostOfDoctor(UUID doctorId);
    List<Post> getPostByCategory(UUID categoryId);
    Post getPostById(UUID postId) throws NotFoundException;
    Post createPost(UUID doctorId, UUID categoryId, Post post) throws BadRequestException;
    void updatePost(Post post) throws NotFoundException;
    void deletePost(UUID postId);
}
