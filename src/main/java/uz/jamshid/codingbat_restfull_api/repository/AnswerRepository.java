package uz.jamshid.codingbat_restfull_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.jamshid.codingbat_restfull_api.entity.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {
}
