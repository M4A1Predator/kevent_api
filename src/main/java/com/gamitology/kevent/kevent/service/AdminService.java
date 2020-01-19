package com.gamitology.kevent.kevent.service;

import com.gamitology.kevent.kevent.dto.UserRegisDto;
import com.gamitology.kevent.kevent.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {

    User registerAdmin(UserRegisDto userDto) throws  Exception;

}
