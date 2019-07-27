package com.in28minutes.rest.webservices.restfulwebservices.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //creates a spring jpa repository that can then be used to update the database etc. 
public interface PostRepsitory extends JpaRepository<Post, Integer>{ //<User = what is being managed, Integer = primary key it is being managed from or with>

}
