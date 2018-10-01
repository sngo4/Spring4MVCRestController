package spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import spring.dao.UserDAO;
import spring.model.User;

@RestController
public class UserRestController {
	@Autowired
	private UserDAO userDao;
	
	@GetMapping("/users")
	public List getUsers(){
		return userDao.list();
	}
	@GetMapping("/users/{id}")
	public ResponseEntity getUser(@PathVariable("id") String id){
		User user = userDao.get(id);
		if (user == null) {
			return new ResponseEntity("No User found for ID " + id, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(user, HttpStatus.OK);
	}
	@PostMapping(value = "/users")
	public ResponseEntity createUser(@RequestBody User user) {

		userDao.create(user);

		return new ResponseEntity(user, HttpStatus.OK);
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity deleteCustomer(@PathVariable String id) {

		if (null == userDao.delete(id)) {
			return new ResponseEntity("No user found for ID " + id, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity(id, HttpStatus.OK);

	}
	@PutMapping("/users/{id}")
	public ResponseEntity updateUser(@PathVariable String id, @RequestBody User user) {

		user = userDao.update(id, user);

		if (null == user) {
			return new ResponseEntity("No user found for ID " + id, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity(user, HttpStatus.OK);
	}

}
