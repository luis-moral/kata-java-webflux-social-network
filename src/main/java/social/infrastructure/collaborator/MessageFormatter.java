package social.infrastructure.collaborator;

import social.domain.UserMessage;

import java.util.concurrent.TimeUnit;

public class MessageFormatter {

    private enum TimeAgo {
        MINUTE("minute", "minutes", TimeUnit.MINUTES.toMillis(1)),
        SECOND("second", "seconds", TimeUnit.SECONDS.toMillis(1));

        private final String singular;
        private final String plural;
        private final long millisecondsPerUnit;

        TimeAgo(String singular, String plural, long millisecondsPerUnit) {
            this.singular = singular;
            this.plural = plural;
            this.millisecondsPerUnit = millisecondsPerUnit;
        }

        public String getNameByTimePassed(long milliseconds) {
            return
                (milliseconds >= getMillisecondsPerUnit() && milliseconds < (getMillisecondsPerUnit() * 2))
                ? singular
                : plural;
        }

        public long getMillisecondsPerUnit() {
            return millisecondsPerUnit;
        }

        public long getUnitsByTimePassed(long milliseconds) {
            return milliseconds / getMillisecondsPerUnit();
        }

        public static TimeAgo getByTimePassed(long milliseconds) {
            TimeAgo timeAgo = SECOND;

            for (int i = 0; i< values().length; i++) {
                if (milliseconds >= values()[i].getMillisecondsPerUnit()) {
                    timeAgo = values()[i];
                    break;
                }
            }

            return timeAgo;
        }
    }

    private static final String AGO = "ago";
    private static final String USER_SEPARATOR = " - ";

    public String formatForRead(UserMessage message, long now) {
        return message.getText() + " " + formatTimePassed(message.getTime(), now);
    }

    public String formatForWall(String user, UserMessage message, long now) {
        return user + USER_SEPARATOR + formatForRead(message, now);
    }

    private String formatTimePassed(long messageDate, long now) {
        long timePassed = Math.max(now - messageDate, 0);
        TimeAgo timeAgo = TimeAgo.getByTimePassed(timePassed);

        return "(" + timeAgo.getUnitsByTimePassed(timePassed) + " " + timeAgo.getNameByTimePassed(timePassed) + " " + AGO + ")";
    }
}
