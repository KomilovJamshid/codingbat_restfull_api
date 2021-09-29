package uz.jamshid.codingbat_restfull_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.jamshid.codingbat_restfull_api.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Integer id);
}
