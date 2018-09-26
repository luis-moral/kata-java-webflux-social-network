package social.infrastructure.collaborator;

import social.application.collaborator.Clock;

public class SystemClock implements Clock {

    @Override
    public long currentTime() {
        return System.currentTimeMillis();
    }
}
