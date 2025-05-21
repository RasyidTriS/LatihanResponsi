package model;

public class AndroidDeveloper extends Candidate {
    // Weights for Android Developer
    private static final double WRITING_WEIGHT = 0.2;
    private static final double CODING_WEIGHT = 0.5;
    private static final double INTERVIEW_WEIGHT = 0.3;
    
    // Constructors
    public AndroidDeveloper() {
        super();
    }
    
    public AndroidDeveloper(String name, double writingScore, double codingScore, double interviewScore) {
        super(name, writingScore, codingScore, interviewScore);
    }
    
    public AndroidDeveloper(int id, String name, double writingScore, double codingScore, double interviewScore) {
        super(id, name, writingScore, codingScore, interviewScore);
    }
    
    // Implementation of abstract methods
    @Override
    protected double getWritingWeight() {
        return WRITING_WEIGHT;
    }
    
    @Override
    protected double getCodingWeight() {
        return CODING_WEIGHT;
    }
    
    @Override
    protected double getInterviewWeight() {
        return INTERVIEW_WEIGHT;
    }
    
    @Override
    public String toString() {
        return "Android Developer";
    }
}