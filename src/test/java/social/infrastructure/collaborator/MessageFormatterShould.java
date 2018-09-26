package social.infrastructure.collaborator;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import social.domain.UserMessage;

import java.util.concurrent.TimeUnit;

public class MessageFormatterShould {

    private static final long NOW = System.currentTimeMillis();
    private static final String TEXT = "I love the weather today";
    private static final String ALICE = "Alice";

    private MessageFormatter messageFormatter;

    @Before
    public void setUp() {
        messageFormatter = new MessageFormatter();
    }

    @Test public void
    format_messages_for_read() {
        Assertions
            .assertThat(formatForRead(NOW))
            .isEqualTo("I love the weather today (0 seconds ago)");

        Assertions
            .assertThat(formatForRead(NOW - TimeUnit.SECONDS.toMillis(1)))
            .isEqualTo("I love the weather today (1 second ago)");

        Assertions
            .assertThat(formatForRead(NOW - TimeUnit.SECONDS.toMillis(2)))
            .isEqualTo("I love the weather today (2 seconds ago)");

        Assertions
            .assertThat(formatForRead(NOW - TimeUnit.SECONDS.toMillis(2) - 20))
            .isEqualTo("I love the weather today (2 seconds ago)");

        Assertions
            .assertThat(formatForRead(NOW - TimeUnit.MINUTES.toMillis(1)))
            .isEqualTo("I love the weather today (1 minute ago)");

        Assertions
            .assertThat(formatForRead(NOW - TimeUnit.MINUTES.toMillis(5)))
            .isEqualTo("I love the weather today (5 minutes ago)");
    }

    @Test public void
    format_messages_for_wall() {
        Assertions
            .assertThat(formatForTimeline(NOW))
            .isEqualTo("Alice - I love the weather today (0 seconds ago)");

        Assertions
            .assertThat(formatForTimeline(NOW - TimeUnit.SECONDS.toMillis(1)))
            .isEqualTo("Alice - I love the weather today (1 second ago)");

        Assertions
            .assertThat(formatForTimeline(NOW - TimeUnit.SECONDS.toMillis(2)))
            .isEqualTo("Alice - I love the weather today (2 seconds ago)");

        Assertions
            .assertThat(formatForTimeline(NOW - TimeUnit.SECONDS.toMillis(2) - 20))
            .isEqualTo("Alice - I love the weather today (2 seconds ago)");

        Assertions
            .assertThat(formatForTimeline(NOW - TimeUnit.MINUTES.toMillis(1)))
            .isEqualTo("Alice - I love the weather today (1 minute ago)");

        Assertions
            .assertThat(formatForTimeline(NOW - TimeUnit.MINUTES.toMillis(5)))
            .isEqualTo("Alice - I love the weather today (5 minutes ago)");
    }

    private String formatForRead(long time) {
        return messageFormatter.formatForRead(new UserMessage(TEXT, time), NOW);
    }

    private String formatForTimeline(long time) {
        return messageFormatter.formatForWall(ALICE, new UserMessage(TEXT, time), NOW);
    }
}
