package com.in28minutes.rest.webservices.restfulwebservices.user;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.in28minutes.rest.webservices.restfulwebservices.helloworld.HelloWorldBean;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class UserJPAResource {
	
	/*@Autowired
	private UserDaoService service;*/
	
	@Autowired
	private UserRepsitory userRepsitory;
	
	@Autowired
	private PostRepsitory postRepsitory;
	
	// GET /users
	//retrieveAllUsers
	@GetMapping ("/jpa/users")
	public List<User> retrieveAllUsers(){
		return userRepsitory.findAll();
	}
	
	//GET /user/{id}
	//retrieveUser(int id)
	@GetMapping ("/jpa/users/{id}")
	public Resource<User> retrieveUser(@PathVariable int id) {
		Optional<User> user = userRepsitory.findById(id); //the optional part to ensure that even if user is null, a proper object is returned. 
		if(!user.isPresent()) {
			throw new UserNotFoundException("id- " + id);
		}
		Resource<User> resource = new Resource(user.get()); //building a resource in order to show misc options when the user comes to /users/{id}
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers()); //used for linking to other classes and resources. 
		resource.add(linkTo.withRel("all-users"));
		return resource;
	}
	
	@PostMapping("/jpa/users")
	public ResponseEntity<Object> createUser(@RequestBody @Valid User user) {
		User savedUser = userRepsitory.save(user); //important to note that the @GenerateValue annotation 
		//creates the users in sequence, so it will try to start from id of 1, that is why we had to change the IDs
		//of the other users to other numbers before we could process; otherwise a 401 error would be thrown. 
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(savedUser.getId())
			.toUri();
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping ("/jpa/users/{id}")
	public void deleteUser(@PathVariable int id) {
		userRepsitory.deleteById(id);//this thing does exception handling all by itself
		
		//return null;
	}
	
	//for retrieving posts
	@GetMapping ("/jpa/users/{id}/posts")
	public List<Post> retrieveAllUsers(@PathVariable int id){
		Optional<User> userOptional = userRepsitory.findById(id);
		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("id- " + id);
		}
		return userOptional.get().getPosts();
	}
	
	//for making a post through jpa
	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Object> createPost(@PathVariable int id, @RequestBody Post post) {
		Optional<User> userOptional = userRepsitory.findById(id);
		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("id- " + id);
		}
		User user = userOptional.get();
		
		post.setUser(user);
		
		postRepsitory.save(post);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(post.getId())
			.toUri();
		return ResponseEntity.created(location).build();
	}
	
	/*@GetMapping(path = "/hello-world-internationalized")
	public String helloWorldInternatinoalized() {
		return "Hello World";
	} */
}
