package introse.group20.hms.infracstructure.adapters;

import introse.group20.hms.application.adapters.IPostAdapter;
import introse.group20.hms.core.entities.Category;
import introse.group20.hms.core.entities.Doctor;
import introse.group20.hms.core.entities.Post;
import introse.group20.hms.core.exceptions.BadRequestException;
import introse.group20.hms.infracstructure.models.CategoryModel;
import introse.group20.hms.infracstructure.models.DoctorModel;
import introse.group20.hms.infracstructure.models.PostModel;
import introse.group20.hms.infracstructure.repositories.ICategoryRepository;
import introse.group20.hms.infracstructure.repositories.IDoctorRepository;
import introse.group20.hms.infracstructure.repositories.IPostRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PostAdapter implements IPostAdapter {
    @Autowired
    private IPostRepository postRepository;
    @Autowired
    private ICategoryRepository categoryRepository;
    @Autowired
    private IDoctorRepository doctorRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private EntityManager entityManager;
    @Override
    public List<Post> getAllAdapter() {
        List<PostModel> postModels = postRepository.findAll();
        return postModels.stream()
                .map(postModel -> modelMapper.map(postModel,Post.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Post> getPostOfDoctorAdapter(UUID doctorId) {
        return null;
    }

    @Override
    public List<Post> getPostByCategoryAdapter(UUID categoryId) {
        return null;
    }

    @Override
    public Optional<Post> getPostByIdAdapter(UUID postId) {
        return Optional.empty();
    }

    @Override
    @Transactional
    public Post createPostAdapter(UUID doctorId, UUID CategoryID, Post post) throws BadRequestException {
        Optional<CategoryModel> categoryModel = categoryRepository.findById(CategoryID);
        Optional<DoctorModel> doctorModel = doctorRepository.findById(doctorId);
        if (categoryModel.isPresent() && doctorModel.isPresent()){
            //map to Model
            CategoryModel category = categoryModel.get();
            DoctorModel doctor = doctorModel.get();
            PostModel postModel = modelMapper.map(post,PostModel.class);
            postModel.setCategory(category);
            postModel.setDoctor(doctor);
            category.getPosts().add(postModel);
            //save to db
            entityManager.merge(postModel);
            //map back to Entity
            Doctor doc = modelMapper.map(doctor,Doctor.class);
            Category cate = modelMapper.map(category,Category.class);
            Post newpost = modelMapper.map(postModel,Post.class);
            newpost.setDoctor(doc);
            newpost.setCategory(cate);
            return newpost;
        }
        else{
            throw new BadRequestException("Wrong CategoryID");
        }
    }

    @Override
    public void updatePostAdapter(Post post) {

    }

    @Override
    public void deletePostAdapter(UUID postId) {

    }
}
