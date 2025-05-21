package model;

public abstract class Candidate implements CandidateEvaluator {
    private int id;
    private String name;
    private double writingScore;
    private double codingScore;
    private double interviewScore;
    
    // Konstanta
    protected static final double MINIMUM_PASSING_SCORE = 85.0;
    
    // Constructor
    public Candidate() {
    }
    
    public Candidate(String name, double writingScore, double codingScore, double interviewScore) {
        this.name = name;
        this.writingScore = writingScore;
        this.codingScore = codingScore;
        this.interviewScore = interviewScore;
    }
    
    public Candidate(int id, String name, double writingScore, double codingScore, double interviewScore) {
        this.id = id;
        this.name = name;
        this.writingScore = writingScore;
        this.codingScore = codingScore;
        this.interviewScore = interviewScore;
    }
    
    // Getters and Setters (Encapsulation)
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public double getWritingScore() {
        return writingScore;
    }
    
    public void setWritingScore(double writingScore) {
        if (writingScore < 0 || writingScore > 100) {
            throw new IllegalArgumentException("Score must be between 0 and 100");
        }
        this.writingScore = writingScore;
    }
    
    public double getCodingScore() {
        return codingScore;
    }
    
    public void setCodingScore(double codingScore) {
        if (codingScore < 0 || codingScore > 100) {
            throw new IllegalArgumentException("Score must be between 0 and 100");
        }
        this.codingScore = codingScore;
    }
    
    public double getInterviewScore() {
        return interviewScore;
    }
    
    public void setInterviewScore(double interviewScore) {
        if (interviewScore < 0 || interviewScore > 100) {
            throw new IllegalArgumentException("Score must be between 0 and 100");
        }
        this.interviewScore = interviewScore;
    }
    
    // Abstract methods for child classes to implement
    protected abstract double getWritingWeight();
    protected abstract double getCodingWeight();
    protected abstract double getInterviewWeight();
    
    // Implementation of CandidateEvaluator
    @Override
    public double calculateFinalScore() {
        return (writingScore * getWritingWeight() +
                codingScore * getCodingWeight() +
                interviewScore * getInterviewWeight());
    }
    
    @Override
    public boolean isAccepted() {
        return calculateFinalScore() >= MINIMUM_PASSING_SCORE;
    }
    
    public String getStatus() {
        return isAccepted() ? "DITERIMA" : "TIDAK DITERIMA";
    }
}