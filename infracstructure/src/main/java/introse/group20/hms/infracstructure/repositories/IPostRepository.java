package introse.group20.hms.infracstructure.repositories;

import introse.group20.hms.infracstructure.models.PostModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface IPostRepository extends JpaRepository<PostModel, UUID> {

}
