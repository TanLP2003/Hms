package introse.group20.hms.infracstructure.adapters;

import introse.group20.hms.application.adapters.IChatBoxAdapter;
import introse.group20.hms.application.adapters.IMessageAdapter;
import introse.group20.hms.core.entities.Message;
import introse.group20.hms.infracstructure.models.MessageModel;
import introse.group20.hms.infracstructure.repositories.IMessageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class MessageAdapter implements IMessageAdapter {
    @Autowired
    IMessageRepository messageRepository;
    @Autowired
    ModelMapper modelMapper;
    @Override
    public Message saveMessage(Message message) {
        MessageModel newMessage = modelMapper.map(message, MessageModel.class);
        MessageModel savedMessage = messageRepository.save(newMessage);
        return modelMapper.map(savedMessage, Message.class);
    }

    @Override
    public List<Message> getConversationAdapter(UUID conversationId) {
        List<MessageModel> messageModelList = messageRepository.findByConversationId(conversationId);
        return messageModelList.stream()
                .map(messageModel -> modelMapper.map(messageModel, Message.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Message> getAllMessagesAdapter() {
        List<MessageModel> messageModelList = messageRepository.findAll();
        return messageModelList.stream()
                .map(messageModel -> modelMapper.map(messageModel, Message.class))
                .collect(Collectors.toList());
    }
}
