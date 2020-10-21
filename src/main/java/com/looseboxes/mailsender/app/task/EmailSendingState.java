package com.looseboxes.mailsender.app.task;

/**
 * @author hp
 */
public class EmailSendingState {
    
    private long total;
    private long processed;
    private boolean running;
    private boolean stopRequested;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getProcessed() {
        return processed;
    }

    public void setProcessed(long processed) {
        this.processed = processed;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isStopRequested() {
        return stopRequested;
    }

    public void setStopRequested(boolean stopRequested) {
        this.stopRequested = stopRequested;
    }

    @Override
    public String toString() {
        return "MailSendingState{" + "total=" + total + ", processed=" + processed + ", running=" + running + ", stopRequested=" + stopRequested + '}';
    }
}
