package com.example.sqs_superherosra.repos;

import com.example.sqs_superherosra.entities.Superhero;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SuperheroRepository extends MongoRepository<Superhero, String> {
	Superhero findByName(String name);

	void deleteByName(String name);

	Superhero findByUniverse(String universe);

	//	Optional<Superhero> findByName(String name);
}
