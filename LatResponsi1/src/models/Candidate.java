package models;

public abstract class Candidate {
    protected String name;
    protected int writing, coding, interview;

    public Candidate(String name, int writing, int coding, int interview) {
        this.name = name;
        this.writing = writing;
        this.coding = coding;
        this.interview = interview;
    }

    public abstract double calculateScore();

    public String getStatus() {
        return calculateScore() >= 85 ? "DITERIMA" : "TIDAK DITERIMA";
    }

    public String getName() { return name; }
    public int getWriting() { return writing; }
    public int getCoding() { return coding; }
    public int getInterview() { return interview; }
}
