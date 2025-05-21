package model;

public class WebDeveloper extends Candidate {
    // Weights for Web Developer
    private static final double WRITING_WEIGHT = 0.4;
    private static final double CODING_WEIGHT = 0.35;
    private static final double INTERVIEW_WEIGHT = 0.25;
    
    // Constructors
    public WebDeveloper() {
        super();
    }
    
    public WebDeveloper(String name, double writingScore, double codingScore, double interviewScore) {
        super(name, writingScore, codingScore, interviewScore);
    }
    
    public WebDeveloper(int id, String name, double writingScore, double codingScore, double interviewScore) {
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
        return "Web Developer";
    }
}