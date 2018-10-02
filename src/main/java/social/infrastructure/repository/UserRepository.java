package social.infrastructure.repository;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class UserRepository {

    private final Map<String, List<String>> usersFollowedMap;

    public UserRepository() {
        usersFollowedMap = new HashMap<>();
    }

    public void saveUserToFollow(String user, String userToFollow) {
        usersFollowedMap
            .computeIfAbsent(user, key -> new LinkedList<>())
            .add(userToFollow);
    }

    public List<String> usersFollowedBy(String user) {
        return usersFollowedMap.get(user);
    }

    public void reset() {
        usersFollowedMap.clear();
    }
}
