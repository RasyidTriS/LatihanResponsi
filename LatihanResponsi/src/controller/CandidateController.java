package controller;

import model.AndroidDeveloper;
import model.Candidate;
import model.CandidateDAO;
import model.CandidateDAOImpl;
import model.WebDeveloper;
import java.util.List;

public class CandidateController {
    private final CandidateDAO candidateDAO;
    
    public CandidateController() {
        this.candidateDAO = new CandidateDAOImpl();
    }
    
    public void addCandidate(String name, String path, double writingScore, double codingScore, double interviewScore) throws Exception {
        // Input validation
        validateInput(name, writingScore, codingScore, interviewScore);
        
        // Create appropriate candidate type based on path
        Candidate candidate;
        if (path.equals("Android Developer")) {
            candidate = new AndroidDeveloper(name, writingScore, codingScore, interviewScore);
        } else {
            candidate = new WebDeveloper(name, writingScore, codingScore, interviewScore);
        }
        
        // Save to database
        candidateDAO.insert(candidate);
    }
    
    public void updateCandidate(int id, String name, String path, double writingScore, double codingScore, double interviewScore) throws Exception {
        // Input validation
        validateInput(name, writingScore, codingScore, interviewScore);
        
        // Create appropriate candidate type based on path
        Candidate candidate;
        if (path.equals("Android Developer")) {
            candidate = new AndroidDeveloper(id, name, writingScore, codingScore, interviewScore);
        } else {
            candidate = new WebDeveloper(id, name, writingScore, codingScore, interviewScore);
        }
        
        // Update in database
        candidateDAO.update(candidate);
    }
    
    public void deleteCandidate(int id) throws Exception {
        candidateDAO.delete(id);
    }
    
    public List<Candidate> getAllCandidates() throws Exception {
        return candidateDAO.getAll();
    }
    
    public Candidate getCandidateById(int id) throws Exception {
        return candidateDAO.getById(id);
    }
    
    private void validateInput(String name, double writingScore, double codingScore, double interviewScore) throws IllegalArgumentException {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        
        if (writingScore < 0 || writingScore > 100) {
            throw new IllegalArgumentException("Writing score must be between 0 and 100");
        }
        
        if (codingScore < 0 || codingScore > 100) {
            throw new IllegalArgumentException("Coding score must be between 0 and 100");
        }
        
        if (interviewScore < 0 || interviewScore > 100) {
            throw new IllegalArgumentException("Interview score must be between 0 and 100");
        }
    }
}