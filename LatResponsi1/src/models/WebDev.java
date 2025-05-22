package models;

public class WebDev extends Candidate {
    public WebDev(String name, int writing, int coding, int interview) {
        super(name, writing, coding, interview);
    }

    @Override
    public double calculateScore() {
        return (writing * 0.4 + coding * 0.35 + interview * 0.25);
    }
}