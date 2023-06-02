package com.epcafes.service;

import com.epcafes.dto.UserDto;
import com.epcafes.model.User;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);
}
