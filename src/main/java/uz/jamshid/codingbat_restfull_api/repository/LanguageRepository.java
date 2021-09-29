package uz.jamshid.codingbat_restfull_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.jamshid.codingbat_restfull_api.entity.Language;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Integer> {

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Integer id);
}
