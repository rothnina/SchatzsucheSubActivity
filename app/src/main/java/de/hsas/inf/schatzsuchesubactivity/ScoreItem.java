package de.hsas.inf.schatzsuchesubactivity;

import java.time.LocalDateTime;

public class ScoreItem {
    private int score;
    private LocalDateTime timeStamp;

    public ScoreItem(int score, LocalDateTime timeStamp) {
        this.score = score;
        this.timeStamp = timeStamp;
    }

    public int getScore() {
        return score;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        String s = score + "\n";
        s += timeStamp.getYear()+"-"+timeStamp.getMonthValue()+"-"+timeStamp.getDayOfMonth()+" ";
        s+= timeStamp.getHour()+":"+timeStamp.getMinute()+":"+timeStamp.getSecond()+"\n";
        return s;
    }
}
