package site.doto.global.logtracer;

import lombok.Getter;

import java.util.Stack;
import java.util.UUID;

@Getter
public class TraceStatus {
    private final String id;

    private int level;

    private final Stack<String> messageStack = new Stack<>();

    private final Stack<Long> startTimeMsStack = new Stack<>();

    public TraceStatus(String message) {
        this.id = createId();
        this.level = 0;
        this.messageStack.push(message);
    }

    private String createId() {
        return UUID.randomUUID().toString().substring(0,8);
    }

    public void beNextLevel(String message) {

        this.level++;
        this.messageStack.push(message);
    }

    public void bePreviousId() {
        this.level--;
    }

    public boolean isFirstLevel() {
        return level == 0;
    }

    public void setStartTimeMs(Long startTimeMs) {
        this.startTimeMsStack.push(startTimeMs);
    }
}
