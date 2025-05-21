package model;

import java.util.List;

public interface CandidateDAO {
    void insert(Candidate candidate) throws Exception;
    void update(Candidate candidate) throws Exception;
    void delete(int id) throws Exception;
    List<Candidate> getAll() throws Exception;
    Candidate getById(int id) throws Exception;
}