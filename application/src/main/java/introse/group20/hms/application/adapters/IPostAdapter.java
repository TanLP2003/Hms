package introse.group20.hms.application.adapters;

import introse.group20.hms.core.entities.Post;
import introse.group20.hms.core.exceptions.BadRequestException;
import introse.group20.hms.core.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IPostAdapter {
    List<Post> getAllAdapter();
    List<Post> getPostOfDoctorAdapter(UUID doctorId);
    List<Post> getPostByCategoryAdapter(UUID categoryId);
    Optional<Post> getPostByIdAdapter(UUID postId) throws NotFoundException;
    Post createPostAdapter(UUID doctorId, UUID CategoryID, Post post) throws BadRequestException;
    void updatePostAdapter(Post post) throws NotFoundException;
    void deletePostAdapter(UUID postId);
}
