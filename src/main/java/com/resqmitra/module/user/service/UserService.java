package com.resqmitra.module.user.service;

import java.util.List;

import com.resqmitra.module.incident.entity.Incident;
import com.resqmitra.module.user.dto.RegisterUserModel;
import com.resqmitra.module.user.entity.User;
import com.resqmitra.module.user.exception.UserAlreadyCreatedException;
import com.resqmitra.utilities.Role;

import jakarta.validation.Valid;

public interface UserService {

	User getUserById(String email);

	User save(@Valid RegisterUserModel model) throws UserAlreadyCreatedException;

	User getUserByIdAndRole(String volunteerId, Role roleVolunteer);

	List<User> getNearByVolunteer(Incident inc);
}
