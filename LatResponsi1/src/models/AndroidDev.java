package models;

public class AndroidDev extends Candidate {
    public AndroidDev(String name, int writing, int coding, int interview) {
        super(name, writing, coding, interview);
    }

    @Override
    public double calculateScore() {
        return (writing * 0.2 + coding * 0.5 + interview * 0.3);
    }
}