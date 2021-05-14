package com.example.springboothw4.rest;

import com.example.springboothw4.dto.ProductDTO;
import com.example.springboothw4.dto.UserDTO;
import com.example.springboothw4.entities.Product;
import com.example.springboothw4.entities.User;
import com.example.springboothw4.services.UserService;
import com.example.springboothw4.services.exceptions.NotFoundException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User API", description = "API to manipulate user resources")
@RestController
@RequestMapping("/api/v1/user")
public class UserRestController {

	private UserService service;

	@Autowired
	public void setService(UserService service) {
		this.service = service;
	}

	@GetMapping(path = "/{id}/id", produces = "application/json")
	public UserDTO findById(@PathVariable("id") Long id) {
		return service.findById(id);
	}

	@GetMapping(path = "/list", produces = "application/json")
	public List<UserDTO> findAll() {
		return service.findAll();
	}

	@PostMapping(consumes = "application/json", produces = "application/json")
	public User createUser(@RequestBody User user) {
		service.createOrUpdate(user);
		return user;
	}

	@PutMapping(consumes = "application/json", produces = "application/json")
	public User updateUser(@RequestBody User user) {
		service.createOrUpdate(user);
		return user;
	}

	@PutMapping(consumes = "application/json", produces = "application/json")
	public UserDTO updateUserDTO(@RequestBody UserDTO userDTO) {
		service.update(userDTO);
		return userDTO;
	}


	@DeleteMapping("/{id}/id")
	public void deleteById(@PathVariable("id") Long id) {
		service.deleteById(id);
	}

	@ExceptionHandler
	public ResponseEntity<String> notFoundExceptionHandler(NotFoundException e) {
		return new ResponseEntity<>("Entity not found", HttpStatus.NOT_FOUND);
	}

}
