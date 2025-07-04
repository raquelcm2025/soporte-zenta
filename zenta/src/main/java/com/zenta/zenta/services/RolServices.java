package com.zenta.zenta.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zenta.zenta.entity.Rol;
import com.zenta.zenta.repository.RolRepository;

@Service
public class RolServices {
	@Autowired
	private RolRepository user;
	
	public List<Rol> listarTodos(){
		return user.findAll();
	}
}
