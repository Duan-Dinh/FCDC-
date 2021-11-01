package com.fpt.myweb.service;

import com.fpt.myweb.dto.request.UserRequet;
import com.fpt.myweb.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface UserService {
    //get all user
    public List<UserRequet> getAllUser();
    //get by id
    public UserRequet getUser(long id);
    //create new User
    public User addUser(UserRequet userRequet) throws ParseException;
    //Delete User
    public UserRequet deleteUser(long id);
    public UserRequet edit(UserRequet userRequet) throws ParseException;
    // search
    public List<UserRequet> searchByRole(Long role_id, Integer page);

    public int countByRole(long role_id);

    public List<UserRequet> searchByTesxt(String text, Integer page);

    public int countByTesxt(String text);

    public Page<User> getAllUserByPage(Integer page);

    public User login(String phone, String password);

    public void importUserPatient(MultipartFile file) throws IOException, ParseException;

    public void importUserStaff(MultipartFile file) throws IOException, ParseException;

}
