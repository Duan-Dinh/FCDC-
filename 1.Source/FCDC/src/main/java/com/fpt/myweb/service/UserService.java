package com.fpt.myweb.service;

import com.fpt.myweb.dto.request.UserRequet;
import com.fpt.myweb.entity.User;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface UserService {
    //get all user
    public List<UserRequet> getAllUser();

    //get by id
    public UserRequet getUser(long id);

    //create new User
    public User addUser(UserRequet userRequet) throws ParseException, IOException;

    //Delete User
    public UserRequet deleteUser(long id);

    public UserRequet edit(UserRequet userRequet) throws ParseException;

    public UserRequet editResult(long id);
    public UserRequet changeTypeTakeCare(long id);
    public List<UserRequet> getAllDoctorByVila(Long vilageId);


    // search
    public List<UserRequet> searchByRole(Long role_id);

    public int countByRole(long role_id);

    public List<UserRequet> searchByTesxt(String text);

    public int countByTesxt(String text);

    public Page<User> getAllUserByPage(Integer page);

    public User login(String phone, String password);

    public void importUserPatient(MultipartFile file) throws IOException, ParseException;

    public void importUserStaff(MultipartFile file) throws IOException, ParseException;
    public void importUserDoctor(MultipartFile file) throws IOException, ParseException;
//    public void CreateCell(Row row, int columnCount, Object value, CellStyle style) ;
    //export
    public void exportUserPatient(HttpServletResponse file,String time) throws IOException, ParseException;

    // oke
    public List<UserRequet> notSentReport(String time);
    public List<UserRequet> sentReport(String time);

    public List<UserRequet> toTestCovid(String time) throws ParseException;
    public List<UserRequet> getAllPatientForDoctor(String doctorId);

}
