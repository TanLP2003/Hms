package introse.group20.hms.application.services;

import introse.group20.hms.application.adapters.IChatBoxAdapter;
import introse.group20.hms.application.adapters.IMessageAdapter;
import introse.group20.hms.application.services.interfaces.IChatBoxService;
import introse.group20.hms.application.services.interfaces.IMessageService;
import introse.group20.hms.core.entities.Message;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MessageService implements IMessageService {
    @Autowired
    IMessageAdapter messageAdapter;
    @Autowired
    IChatBoxAdapter chatBoxAdapter;

    public MessageService(IChatBoxAdapter chatBoxAdapter, IMessageAdapter messageAdapter){
        this.chatBoxAdapter = chatBoxAdapter;
        this.messageAdapter = messageAdapter;
    }
    @Override
    public List<Message> getConversation(UUID senderId, UUID receiverId) {
        return chatBoxAdapter.getConversationIdAdapter(senderId, receiverId, false)
                .map(messageAdapter::getConversationAdapter)
                .orElse(new ArrayList<>());
    }

    @Override
    public Message saveMessage(Message message) {
        UUID conversationID = chatBoxAdapter.getConversationIdAdapter(message.getSenderId(), message.getReceiverId(), true)
                .orElseThrow();
        message.setConversationId(conversationID);
        return messageAdapter.saveMessage(message);
    }
//    @Override
//    public List<Message> getConversation(UUID patientId, UUID doctorId) {
//        return null;
//    }
//
//    @Override
//    public Message SendMessage(UUID doctorId, UUID patientId, Message message) {
//        return null;
//    }
//
//    @Override
//    public Message getLatestMessage(UUID doctorId, UUID patientId) {
//        return null;
//    }
}
