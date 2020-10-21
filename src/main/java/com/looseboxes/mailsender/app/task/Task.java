package com.looseboxes.mailsender.app.task;

/**
 * @author hp
 */
public interface Task<C, S> {
    void start(C config);
    void stop();
    boolean isStopRequested();
    boolean isRunning();
    S getSnapshot();
}
