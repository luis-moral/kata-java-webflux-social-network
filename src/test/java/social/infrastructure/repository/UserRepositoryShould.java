package social.infrastructure.repository;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

public class UserRepositoryShould {

    private static final String BOB = "Bob";
    private static final String CHARLIE = "Charlie";
    private static final String ALICE = "Alice";

    private UserRepository userRepository;

    @Before
    public void setUp() {
        userRepository = new UserRepository();
    }

    @Test public void
    save_users_followed() {
        userRepository.saveUserToFollow(BOB, CHARLIE);
        userRepository.saveUserToFollow(BOB, ALICE);

        Assertions
            .assertThat(userRepository.usersFollowedBy(BOB))
            .containsExactly(CHARLIE, ALICE);
    }
}
