package com.example.springboothw4.services;

import com.example.springboothw4.dto.UserDTO;
import com.example.springboothw4.dto.UserMapper;
import com.example.springboothw4.entities.User;
import com.example.springboothw4.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

	private UserRepository repository;

	@Autowired
	public void setRepository(UserRepository repository) {
		this.repository = repository;
	}

	@Transactional
	public User createOrUpdate(User user) {
		return  repository.save(user);
	}

	/*public Optional<User> findById(Long id) {
		return repository.findById(id);
	}*/

	/*//public List<User> findAll () {
		return repository.findAll();
	}*/

	@Transactional
	public void deleteById(Long id) {
		repository.deleteById(id);
	}


	public UserDTO update(UserDTO userDto) {
		User user = UserMapper.MAPPER.toUser(userDto);
		user = repository.save(user);
		return UserMapper.MAPPER.fromUser(user);
	}

	public List<UserDTO> findAll() {
		return UserMapper.MAPPER.fromUserList(repository.findAll());
	}

	public UserDTO findById(Long id) {
		return UserMapper.MAPPER.fromUser(repository.findById(id).get());
	}

}
