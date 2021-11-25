package com.fpt.myweb.service;

import com.fpt.myweb.dto.request.ListUserRequest;
import com.fpt.myweb.dto.request.UserRequet;
import com.fpt.myweb.dto.response.ChartStaffRes;
import com.fpt.myweb.dto.response.ResetPassRes;
import com.fpt.myweb.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface UserService {
    //get all user
    public List<UserRequet> getAllUser(Integer page);

    //get by id
    public UserRequet getUser(long id);

    //create new User
    public User addUser(UserRequet userRequet, MultipartFile file) throws ParseException, IOException;
    public boolean checkPhone(String phone);

    //Delete User
    public UserRequet deleteUser(long id);

    public ResetPassRes resetPass(String phone);

    public UserRequet changePass(long id , String newPass);

    public UserRequet edit(UserRequet userRequet, MultipartFile file) throws ParseException, IOException;
//edit user by id
    public UserRequet editUserById(UserRequet userRequet, MultipartFile file) throws ParseException, IOException;

    public UserRequet editResult(long id);
    public UserRequet changeTypeTakeCare(long id , Long doctorId);
    public List<ListUserRequest> getAllDoctorByVila(Long vilageId, Integer page);
    public int countAllDoctorByVila(Long villageId);


    // search
    public List<ListUserRequest> searchByRole(Long role_id,String text, Integer page);
    public int countSearchByRole(Long roleId,String text);
    public int countByRole(long role_id);

    public List<ListUserRequest> searchByTesxt(String text, Integer page);
    public List<ListUserRequest> searchByTextWithRole(Long roleId,String text , Integer page);
    public int countByTextWithRole(Long roleId,String text);

    public List<ListUserRequest> searchByTextForStaff(String text , Long villageId, Integer page);

    public List<ListUserRequest> searchByTextPatientsCuredForStaff(String text , Long villageId, Integer page);
    public int countByTesxtPatientsCuredForStaff(String text, Long villageId);

    public int countByTesxtForStaff(String text, Long villageId);

    public int countByTesxt(String text);


    public Page<User> getAllUserByPage(Integer page);

    public User login(String phone, String password);

    public boolean importUserPatient(MultipartFile file) throws IOException, ParseException;

    public void importUserStaff(MultipartFile file) throws IOException, ParseException;
    public void importUserDoctor(MultipartFile file) throws IOException, ParseException;
//    public void CreateCell(Row row, int columnCount, Object value, CellStyle style) ;
    //export
    public void exportUserPatient(HttpServletResponse file,String time) throws IOException, ParseException;

    // oke
    public List<ListUserRequest> notSentAndSentReport(String time,Long villageId,String text,String key,Integer page);

    public int countNotSentAndSentReport(String time,Long villageId,String text,String key);

//    public List<ListUserRequest> sentReport(String time,Long villageId,String text,Integer page);
//    public int countSentReport(String time,Long villageId,String text);

    public List<UserRequet> toTestCovid(String time,Long villageId) throws ParseException;
    public List<ListUserRequest> getAllPatientForDoctor(String doctorId,Integer page);
    public int countAllPatientForDoctor(String doctorId);
    public List<UserRequet> getAllPatientForStaff(Long VillageId);

    public List<ListUserRequest> getAllPatientForStaff(Long villageId,String search,String key,Integer page);

    public int countByPatientsForStaff(Long villageId,String search,String key);

//    public List<UserRequet> getNewPatientOneDay1(String time,Long villageId,Integer page) throws ParseException;

//    public List<ListUserRequest> getAllPatientsCuredForStaff(Long VillageId,Integer page);
//    public int countAllPatientCuredForStaff(Long villageId);

    public List<UserRequet> getNewPatientOneDay(String time,Long villageId) throws ParseException;

    public List<UserRequet> getCuredPatientOneDay(String time,Long villageId) throws ParseException;

    public List<UserRequet> getAllPatientCuredForStaff(Long VillageId);

    public List<ChartStaffRes> getChartForStaff(String startDate, String endDate,Long villageId) throws ParseException;

    public int totalCurrentF0(Long villageId);
}
