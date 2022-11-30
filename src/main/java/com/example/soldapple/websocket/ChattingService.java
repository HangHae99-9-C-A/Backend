//package com.example.soldapple.websocket;
//
//import com.example.soldapple.redis.RedisSubscriber;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.redis.core.HashOperations;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.listener.ChannelTopic;
//import org.springframework.data.redis.listener.RedisMessageListenerContainer;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RequiredArgsConstructor
//@Service
//public class ChatService {
//    private final RedisMessageListenerContainer redisMessageListener;
//    private final RedisSubscriber redisSubscriber;
//    private static final String CHAT_ROOMS = "CHAT_ROOM";
//    public static final String ENTER_INFO = "ENTER_INFO"; // 채팅룸에 입장한 클라이언트의 sessionId와 채팅룸 id를 맵핑한 정보 저장
//    private final RedisTemplate<String, Object> redisTemplate;
//    private HashOperations<String, String, ChatRoom> opsHashChatRoom;
//    // 채팅방의 대화 메시지를 발행하기 위한 redis topic 정보. 서버별로 채팅방에 매치되는 topic정보를 Map에 넣어 roomId로 찾을수 있도록 한다.
//    private Map<String, ChannelTopic> topics;
//    private HashOperations<String, String, String> hashOpsEnterInfo;
//    @PostConstruct
//    private void init() {
//        opsHashChatRoom = redisTemplate.opsForHash();
//        hashOpsEnterInfo=redisTemplate.opsForHash();
//
//        topics = new HashMap<>();
//    }
//    private final ChatRoomRepository chatRoomRepository;
//
//    public List<ChatRoom> findAllRoom() {
//        return chatRoomRepository.findAll();
//    }
//
//    public ChatRoom findRoomById(String id) {
//        ChatRoom chatRoom=(ChatRoom)chatRoomRepository.findById(id).orElseThrow();
//        return chatRoom;
//    }
//
//    /**
//     * 채팅방 생성 : 서버간 채팅방 공유를 위해 redis hash에 저장한다.
//     */
//    public ChatRoom createChatRoom(Long userId1, Long userId2) {
//        String name=userId1+"와 "+userId2;
//        ChatRoom chatRoom = ChatRoom.create(name,userId1,userId2);
//        opsHashChatRoom.put(CHAT_ROOMS, chatRoom.getId(), chatRoom);
//        chatRoomRepository.save(chatRoom);
//        return chatRoom;
//    }
//    /**
//     * 채팅방 입장 : redis에 topic을 만들고 pub/sub 통신을 하기 위해 리스너를 설정한다.
//     */
//    public void enterChatRoom(String roomId) {
//        ChannelTopic topic = topics.get(roomId);
//        if (topic == null)
//            topic = new ChannelTopic(roomId);
//        redisMessageListener.addMessageListener(redisSubscriber, topic);
//        topics.put(roomId, topic);
//    }
//
//    public ChannelTopic getTopic(String roomId) {
//        return topics.get(roomId);
//    }
//    public List<ChatRoom> getCustomerEnterRooms(Long userId){
//        return chatRoomRepository.findChatRoomsByUserId1OrUserId2(userId,userId);
//    }
//    public List<ChatRoom> getStoreEnterRooms(Long userId){
//        return chatRoomRepository.findChatRoomsByUserId1OrUserId2(userId,userId);
//    }
//
//    public void deleteById(ChatRoom chatRoom){
//        chatRoomRepository.delete(chatRoom);
//    }
//
//    /**
//     * destination정보에서 roomId 추출
//     */
//    public String getRoomId(String destination) {
//        int lastIndex = destination.lastIndexOf('/');
//        if (lastIndex != -1)
//            return destination.substring(lastIndex + 1);
//        else
//            return "";
//    }
//
//    // 유저가 입장한 채팅방ID와 유저 세션ID 맵핑 정보 저장
//    public void setUserEnterInfo(String sessionId, String roomId) {
//        hashOpsEnterInfo.put(ENTER_INFO, sessionId, roomId);
//    }
//
//    // 유저 세션으로 입장해 있는 채팅방 ID 조회
//    public String getUserEnterRoomId(String sessionId) {
//        return hashOpsEnterInfo.get(ENTER_INFO, sessionId);
//    }
//
//    // 유저 세션정보와 맵핑된 채팅방ID 삭제
//    public void removeUserEnterInfo(String sessionId) {
//        hashOpsEnterInfo.delete(ENTER_INFO, sessionId);
//    }
//}
