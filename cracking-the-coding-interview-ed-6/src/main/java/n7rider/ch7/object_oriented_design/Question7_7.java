package n7rider.ch7.object_oriented_design;

import java.util.List;
import java.util.Map;

/**
 * Chat Server: Explain how you would design a chat server. In particular, provide details about the
 * various backend components, classes, and methods. What would be the hardest problems to solve?
 *
 * After completing:
 * While creating code, I realized that blocking users or phrases will result in odd behavior in a chat room.
 * It works best only for threaded chats or social media.
 *
 * After comparing with solution:
 * I missed the online/offline status feature
 * I didn't mention how user data is stored. The book uses a map, but that doesn't happen in real-life.
 * I couldn't think of a blocker or a set of hardest problems. The book mentions maintaining accurate online status,
 * conflict of information, scaling across multiple servers, protecting against Ddos, spamming. Just take an
 * aspect and consider its worst-case use, and see how you can tackle it.
 */
public class Question7_7 {
    abstract class ChatServer {
        abstract boolean signUp(String userInfoJson);
        abstract boolean signIn(String userNameAndPasswordHash);
    }

    abstract class User {
        List<String> chatroomIds;
        List<String> blockedUserIds;
        List<String> blockedPhrases;
        Map<String, Integer> chatOffsets;

        abstract void sendMessage(String messageType, String messageContent);
        abstract List<String> receiveMessages(String chatId, int offset);
    }

    abstract class Chat {
        String chatId;

        abstract void generateChatId();
    }

    abstract class ChatRoom extends Chat {
        List<String> userIds;
        List<String> adminUserIds;
        abstract void addUsers(List<User> users);
        abstract void deleteUsers(List<User> users);

        @Override
        abstract void generateChatId();
    }

    abstract class PersonalChat extends Chat {
        List<String> userIds;

        @Override
        abstract void generateChatId();
    }


}

/**
 *
 * Components:
 * ChatServer // for simplicity, assume a single server
 * User
 *   ChatRoom[]
 *
 * Chat
 *   ChatRoom
 *   PersonalChat // 2...n people can have a group DM chat - like what Slack does
 * Message
 *  Text
 *  Picture
 *  Video
 *
 * Fields and methods:
 * ChatServer
 *   signup()
 *   signIn()

 *
 * Chat
 *   sendMessage(Message type, content)
 *     filterContent(Message type, content)
 *
 *   receiveMessage(current offset)
 *
 *
 *
 *
 * ChatRoom
 *   addPeopleToChatRoom(chatId, List<User>)
 *   sendMessage(Message type, content)
 *   List<User> admin
 *   String bio
 *
 *
 * PersonalChat
 *   sendMessage(Message type, content)
 *
 * User
 *   updateProfile()
 *   createChatRoom(List<Users>)
 *   createPersonalChat(List<Users>)
 *
 * Flow:
 * ChatServer - instantiate
 *
 * User signUp() // n users
 *   create Map<chat-id, offset>
 *   create profile info
 *
 * createChatRoom(List<Users>)
 *   set creator as admin // can create another method to update admin
 *   have option to change public/private
 *
 *
 * createPersonalChat(List<Users>)
 *
 * sendMessage(Message type, content)
 *   filterContent(Message type, content)
 *   create new offset
 *   update sender's offset to current
 *
 * receiveMessage(current offset)
 *   filterBlocks(Message type, content)
 *   fetch all new message from current offset
 *
 * chatSearch()
 *   search by chat name - along with filters, search only public rooms
 *   joinRoom(chat-id)
 *
 */