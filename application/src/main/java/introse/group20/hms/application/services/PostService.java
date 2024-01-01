package introse.group20.hms.application.services;

import introse.group20.hms.application.adapters.IPostAdapter;
import introse.group20.hms.application.services.interfaces.IPostService;
import introse.group20.hms.core.entities.Post;
import introse.group20.hms.core.exceptions.BadRequestException;
import introse.group20.hms.core.exceptions.NotFoundException;
import org.apache.logging.log4j.message.StringFormattedMessage;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

public class PostService implements IPostService {
    private IPostAdapter postAdapter;
    public  PostService(IPostAdapter postAdapter){
        this.postAdapter = postAdapter;
    }
    @Override
    public List<Post> getAll() {
        return postAdapter.getAllAdapter();
    }

    @Override
    public List<Post> getPostOfDoctor(UUID doctorId) {
        return postAdapter.getPostOfDoctorAdapter(doctorId);
    }

    @Override
    public List<Post> getPostByCategory(UUID categoryId) {
        return postAdapter.getPostByCategoryAdapter(categoryId);
    }

    @Override
    public Post getPostById(UUID postId) throws NotFoundException {
        Optional<Post> postOptional = postAdapter.getPostByIdAdapter(postId);
        if (postOptional.isPresent()){
            return postOptional.get();
        }
        else throw new NotFoundException(String.format("Cannot find post with id %s",postId));
    }

    @Override
    public Post createPost(UUID doctorId, UUID categoryId, Post post) throws BadRequestException{
        return postAdapter.createPostAdapter(doctorId,categoryId,post);
    }


    @Override
    public void updatePost(UUID userId, Post post) throws NotFoundException, BadRequestException {
        postAdapter.updatePostAdapter(userId, post);
    }

    @Override
    public void deletePost(UUID userId, UUID postId) throws BadRequestException {
        postAdapter.deletePostAdapter(userId, postId);
    }
}
