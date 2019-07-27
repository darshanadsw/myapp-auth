package com.myapp.auth.repositories;

import com.myapp.auth.domain.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends CrudRepository<Person,String> {

    Person findByEmailIgnoreCase(@Param("email") String email);

}
