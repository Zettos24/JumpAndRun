package de.zettos.jumpandrun.timer;

public class Time {

    private int hours;
    private int minutes;
    private int seconds;

    public Time(int hours, int minutes, int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public String updateBySecond(){

        seconds++;
        if (seconds >= 60) {
            minutes += seconds / 60;
            seconds = seconds % 60;
        }

        if (minutes >= 60) {
            hours += minutes / 60;
            minutes = minutes % 60;
        }

        StringBuilder builder = new StringBuilder();

        if(getHours() > 0){
            builder.append(String.format("§9%02d §7hours : ", getHours()));
        }

        builder.append(String.format("§9%02d §7min : §9%02d §7sec",getMinutes(),getSeconds()));

        return builder.toString();
    }

}
