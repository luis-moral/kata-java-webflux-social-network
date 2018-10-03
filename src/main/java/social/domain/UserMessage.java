package social.domain;

import java.util.Objects;

public class UserMessage {
    private final String user;
    private final String text;
    private final long time;

    public UserMessage(String user, String text, long time) {
        this.user = user;
        this.text = text;
        this.time = time;
    }

    public String getUser() {
        return user;
    }

    public String getText() {
        return text;
    }

    public long getTime() {
        return time;
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
