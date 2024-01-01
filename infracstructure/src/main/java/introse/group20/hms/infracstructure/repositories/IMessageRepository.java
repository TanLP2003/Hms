package introse.group20.hms.infracstructure.repositories;

import introse.group20.hms.infracstructure.models.MessageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


public interface IMessageRepository extends JpaRepository<MessageModel, UUID> {
    List<MessageModel> findByConversationId(UUID conversationId);
    List<MessageModel> findBySenderIdOrReceiverId(UUID senderId, UUID receiverId);
}
