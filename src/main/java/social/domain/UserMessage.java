package social.domain;

import java.util.Objects;

public class UserMessage {
    private final String text;
    private final long time;

    public UserMessage(String text, long time) {
        this.text = text;
        this.time = time;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserMessage that = (UserMessage) o;
        return time == that.time &&
                Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, time);
    }
}
