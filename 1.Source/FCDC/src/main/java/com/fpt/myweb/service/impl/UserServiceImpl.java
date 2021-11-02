package com.fpt.myweb.service.impl;


import com.fpt.myweb.common.Contants;
import com.fpt.myweb.convert.UserConvert;
import com.fpt.myweb.dto.request.UserRequet;
import com.fpt.myweb.entity.Role;
import com.fpt.myweb.entity.User;
import com.fpt.myweb.entity.Village;
import com.fpt.myweb.exception.AppException;
import com.fpt.myweb.exception.ErrorCode;
import com.fpt.myweb.repository.RoleRepository;
import com.fpt.myweb.repository.UserRepository;
import com.fpt.myweb.repository.VillageRepository;
import com.fpt.myweb.service.SmsService;
import com.fpt.myweb.service.UserService;
import com.fpt.myweb.utils.GetUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private VillageRepository villageRepository;

    @Autowired
    private UserConvert userConvert;

    @Autowired
    private Environment env;

    @Autowired
    private SmsService smsService;


    @Override
    public List<UserRequet> getAllUser() {
        List<User> userList = userRepository.findAll();
        List<UserRequet> userRequets = new ArrayList<>();
        for (User user : userList) {
            userRequets.add(userConvert.convertToUserRequest(user));
        }
        return userRequets;
    }

    @Override
    public UserRequet getUser(long id) {
        User user = userRepository.findById(id).orElseThrow(()
                -> new AppException(ErrorCode.NOT_FOUND_ID.getKey(), ErrorCode.NOT_FOUND_ID.getValue() + id));
        UserRequet userRequet = userConvert.convertToUserRequest(user);
        return userRequet;
    }

    @Override
    public User addUser(UserRequet userRequet) throws ParseException {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Role role = roleRepository.findById(userRequet.getRole_id()).orElseThrow(()
                -> new AppException(ErrorCode.NOT_FOUND_ROLE_ID.getKey(), ErrorCode.NOT_FOUND_ROLE_ID.getValue() + userRequet.getRole_id()));
        Village village = villageRepository.findById(userRequet.getVillage_id()).orElseThrow(()
                -> new AppException(ErrorCode.NOT_FOUND_VILLAGE_ID.getKey(), ErrorCode.NOT_FOUND_VILLAGE_ID.getValue() + userRequet.getVillage_id()));
        User user = userConvert.convertToUser(userRequet);
        user.setRole(role);
        user.setVillage(village);
        user.setFullname(userRequet.getFullname());
        user.setPassword(passwordEncoder.encode(userRequet.getPassword()));
        user.setAddress(userRequet.getAddress());
        user.setEmail(userRequet.getEmail());
        Date date = new SimpleDateFormat(Contants.DATE_FORMAT).parse(userRequet.getBirthOfdate());
        user.setBirthOfdate(date);
        user.setGender(userRequet.getGender());
        user.setPhone(userRequet.getPhone());
        user.setImageUrl(userRequet.getImageUrl());
        user.setCreatedDate(new Date());
        user.setIs_active("0");
        if (userRequet.getStartOfDate() != null) {
            Date date1 = new SimpleDateFormat(Contants.DATE_FORMAT).parse(userRequet.getStartOfDate());
            user.setDateStart(date1);
        }
        User user1 = userRepository.save(user);
        return user1;
    }

    @Override
    public UserRequet deleteUser(long id) {
        User user = userRepository.findById(id).orElseThrow(()
                -> new AppException(ErrorCode.NOT_FOUND_ID.getKey(), ErrorCode.NOT_FOUND_ID.getValue() + id));
        UserRequet userRequet = userConvert.convertToUserRequest(user);
        userRepository.delete(user);
        return userRequet;
    }

    @Override
    public UserRequet edit(UserRequet userRequet) throws ParseException {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = userRepository.findById(userRequet.getId()).orElseThrow(()
                -> new AppException(ErrorCode.NOT_FOUND_ID.getKey(), ErrorCode.NOT_FOUND_ID.getValue() + userRequet.getId()));
        Role role = roleRepository.findById(userRequet.getRole_id()).orElseThrow(()
                -> new AppException(ErrorCode.NOT_FOUND_ROLE_ID.getKey(), ErrorCode.NOT_FOUND_ROLE_ID.getValue() + userRequet.getRole_id()));
        Village village = villageRepository.findById(userRequet.getVillage_id()).orElseThrow(()
                -> new AppException(ErrorCode.NOT_FOUND_VILLAGE_ID.getKey(), ErrorCode.NOT_FOUND_VILLAGE_ID.getValue() + userRequet.getVillage_id()));
        user.setRole(role);
        user.setVillage(village);
        user.setFullname(userRequet.getFullname());
        user.setPassword(passwordEncoder.encode(userRequet.getPassword()));
        user.setAddress(userRequet.getAddress());
        user.setEmail(userRequet.getEmail());
        Date date = new SimpleDateFormat(Contants.DATE_FORMAT).parse(userRequet.getBirthOfdate());
        user.setBirthOfdate(date);
        user.setGender(userRequet.getGender());
        user.setPhone(userRequet.getPhone());
        user.setImageUrl(userRequet.getImageUrl());
        user.setModifiedDate(new Date());
        Date date1 = new SimpleDateFormat(Contants.DATE_FORMAT).parse(userRequet.getStartOfDate());
        user.setDateStart(date1);
        UserRequet userRequet1 = userConvert.convertToUserRequest(userRepository.save(user));
        return userRequet1;
    }

    @Override
    public List<UserRequet> searchByRole(Long role_id, Integer page) {
        if (page == null) {
            page = 0;
        } else {
            page--;
        }
        Pageable pageable = PageRequest.of(page, Contants.PAGE_SIZE);
        List<User> searchList = userRepository.findAllUserByRoleId(role_id, pageable);
        List<UserRequet> userRequets = new ArrayList<>();
        for (User user : searchList) {
            userRequets.add(userConvert.convertToUserRequest(user));
        }
        return userRequets;
    }

    @Override
    public int countByRole(long role_id) {
        Role role = roleRepository.findById(role_id).orElseThrow(()
                -> new AppException(ErrorCode.NOT_FOUND_ROLE_ID.getKey(), ErrorCode.NOT_FOUND_ROLE_ID.getValue() + role_id));
        List<User> searchList = userRepository.findByRole(role);
        if (searchList == null) {
            return 0;
        }
        return searchList.size();
    }

    @Override
    public List<UserRequet> searchByTesxt(String text, Integer page) {
        if (page == null) {
            page = 1;
        } else {
            page--;
        }
        Pageable pageable = PageRequest.of(page, Contants.PAGE_SIZE);
        List<User> searchList = userRepository.findByFullnameContaining(text, pageable);
        List<UserRequet> userRequets = new ArrayList<>();
        for (User user : searchList) {
            userRequets.add(userConvert.convertToUserRequest(user));
        }
        return userRequets;
    }

    @Override
    public int countByTesxt(String text) {
        List<User> searchList = userRepository.findByFullnameContaining(text);
        if (searchList == null) {
            return 0;
        }
        return searchList.size();
    }

    @Override
    public Page<User> getAllUserByPage(Integer page) {
        List<UserRequet> userRequets = new ArrayList<>();
        if (page == null) {
            page = 0;
        } else {
            page--;
        }
        Pageable pageable = PageRequest.of(page, Contants.PAGE_SIZE);
        Page<User> searchList = userRepository.findAll(pageable);
        return searchList;
    }

    @Override
    public User login(String phone, String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = userRepository.findByPhone(phone);
        if (passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    @Override
    public void importUserPatient(MultipartFile file) throws IOException, ParseException {
        String path = env.getProperty("folder.user.imports");
        File fileUpload = new File(path);
        if (!fileUpload.exists()) {
            fileUpload.mkdir();
        }
        if (fileUpload != null) {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            // save file
            InputStream inputStream = null;
            File fileUrl = null;
            inputStream = file.getInputStream();
            String name = file.getResource().getFilename();
            path += System.currentTimeMillis() + "_" + name;
            fileUrl = new File(path);
            OutputStream outStream = new FileOutputStream(fileUrl);
            FileCopyUtils.copy(inputStream, outStream);
            // import user
            FileInputStream inputStreamImport = new FileInputStream(fileUrl);

            Workbook workbook = new XSSFWorkbook(inputStreamImport);
            Sheet firstSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = firstSheet.iterator();

            while (iterator.hasNext()) {
                Row nextRow = iterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();
                User user = new User();
                cellIterator.next();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    if (cell.getColumnIndex() == 1) {
                        user.setFullname(cell.getStringCellValue());
                    } else if (cell.getColumnIndex() == 2) {
                        user.setGender(cell.getStringCellValue());
                    } else if (cell.getColumnIndex() == 6) {
                        String address = cell.getStringCellValue();
                        Village village = villageRepository.findByName(address);
                        if (village != null) {
                            user.setVillage(village);
                        }
                    } else if (cell.getColumnIndex() == 5) {
                        user.setPhone(cell.getStringCellValue());
                    } else if (cell.getColumnIndex() == 4) {
                        user.setEmail(cell.getStringCellValue());
                    } else if (cell.getColumnIndex() == 7) {
                        user.setAddress(cell.getStringCellValue());
                    } else if (cell.getColumnIndex() == 3) {
                        try {
                            Date date = new SimpleDateFormat(Contants.DATE_FORMAT).parse(cell.getStringCellValue());
                            user.setBirthOfdate(date);
                        } catch (Exception e) {

                        }
                    } else if (cell.getColumnIndex() == 8) {
                        try {
                            Date date = new SimpleDateFormat(Contants.DATE_FORMAT).parse(cell.getStringCellValue());
                            user.setDateStart(date);
                        } catch (Exception e) {

                        }
                    }

                }
                Role role = roleRepository.findByName("Patient");
                user.setRole(role);
                // check User by phone
                User userCheck = userRepository.findByPhone(user.getPhone());
                String pass = GetUtils.generateRandomPassword(8);
                user.setPassword(passwordEncoder.encode(pass));
                if (userCheck == null) {
                    user.setCreatedDate(new Date());
                    userRepository.save(user);
                } else {
                    userCheck.setFullname(user.getFullname());
                    userCheck.setGender(user.getGender());
                    userCheck.setVillage(user.getVillage());
                    userCheck.setEmail(user.getEmail());
                    userCheck.setAddress(user.getAddress());
                    userCheck.setBirthOfdate(user.getBirthOfdate());
                    userCheck.setModifiedDate(new Date());
                    userCheck.setRole(user.getRole());
                    userCheck.setDateStart(user.getDateStart());
                    userRepository.save(userCheck);
                }
                // send pass to user with phone
                // test
//                smsService.sendGetJSON("0385422617", "Hello Quảng!");

            }

            workbook.close();
            inputStream.close();

        }
    }

    @Override
    public void importUserStaff(MultipartFile file) throws IOException, ParseException {
        String path = env.getProperty("folder.user.imports");
        File fileUpload = new File(path);
        if (!fileUpload.exists()) {
            fileUpload.mkdir();
        }
        if (fileUpload != null) {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            // save file
            InputStream inputStream = null;
            File fileUrl = null;
            inputStream = file.getInputStream();
            String name = file.getResource().getFilename();
            path += System.currentTimeMillis() + "_" + name;
            fileUrl = new File(path);
            OutputStream outStream = new FileOutputStream(fileUrl);
            FileCopyUtils.copy(inputStream, outStream);
            // import user
            FileInputStream inputStreamImport = new FileInputStream(fileUrl);

            Workbook workbook = new XSSFWorkbook(inputStreamImport);
            Sheet firstSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = firstSheet.iterator();

            while (iterator.hasNext()) {
                Row nextRow = iterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();
                User user = new User();
                cellIterator.next();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    if (cell.getColumnIndex() == 1) {
                        user.setFullname(cell.getStringCellValue());
                    } else if (cell.getColumnIndex() == 2) {
                        user.setGender(cell.getStringCellValue());
                    } else if (cell.getColumnIndex() == 6) {
                        String address = cell.getStringCellValue();
                        Village village = villageRepository.findByName(address);
                        if (village != null) {
                            user.setVillage(village);
                        }
                    } else if (cell.getColumnIndex() == 5) {
                        user.setPhone(cell.getStringCellValue());
                    } else if (cell.getColumnIndex() == 4) {
                        user.setEmail(cell.getStringCellValue());
                    } else if (cell.getColumnIndex() == 7) {
                        user.setAddress(cell.getStringCellValue());
                    } else if (cell.getColumnIndex() == 3) {
                        try {
                            Date date = new SimpleDateFormat(Contants.DATE_FORMAT).parse(cell.getStringCellValue());
                            user.setBirthOfdate(date);
                        } catch (Exception e) {

                        }
                    } else if (cell.getColumnIndex() == 8) {
                        try {
                            Date date = new SimpleDateFormat(Contants.DATE_FORMAT).parse(cell.getStringCellValue());
                            user.setDateStart(date);
                        } catch (Exception e) {

                        }
                    }

                }
                Role role = roleRepository.findByName("Staff");
                user.setRole(role);
                // check User by phone
                User userCheck = userRepository.findByPhone(user.getPhone());
                String pass = GetUtils.generateRandomPassword(8);
                user.setPassword(passwordEncoder.encode(pass));
                if (userCheck == null) {
                    user.setCreatedDate(new Date());
                    userRepository.save(user);
                } else {
                    userCheck.setFullname(user.getFullname());
                    userCheck.setGender(user.getGender());
                    userCheck.setVillage(user.getVillage());
                    userCheck.setEmail(user.getEmail());
                    userCheck.setAddress(user.getAddress());
                    userCheck.setBirthOfdate(user.getBirthOfdate());
                    userCheck.setModifiedDate(new Date());
                    userCheck.setRole(user.getRole());
                    userCheck.setDateStart(user.getDateStart());
                    userRepository.save(userCheck);
                }
                // send pass to user with phone
                // test
//                smsService.sendGetJSON("0385422617", "Hello Quảng!");

            }

            workbook.close();
            inputStream.close();

        }
    }

    @Override
    public List<UserRequet> notSentReport(String time, Integer page) {
        if (page == null) {
            page = 1;
        } else {
            page--;
        }
        Pageable pageable = PageRequest.of(page, Contants.PAGE_SIZE);
        List<User> searchList = userRepository.notSentReport(time, pageable);
        List<UserRequet> userRequets = new ArrayList<>();
        for (User user : searchList) {
            userRequets.add(userConvert.convertToUserRequest(user));
        }
        return userRequets;
    }

    @Override
    public List<UserRequet> toTestCovid(String time) throws ParseException {
        Long role_id = 3L;
        Role role = roleRepository.findById(role_id).orElseThrow(()
                -> new AppException(ErrorCode.NOT_FOUND_ROLE_ID.getKey(), ErrorCode.NOT_FOUND_ROLE_ID.getValue() + role_id));
        List<User> searchList = userRepository.findByRole(role);
        Date date = new SimpleDateFormat(Contants.DATE_FORMAT).parse(time);
        List<UserRequet> userRequets = new ArrayList<>();

        for (User user : searchList) {
            Long totalDate = date.getTime() - user.getDateStart().getTime();

            if (TimeUnit.MILLISECONDS.toDays(date.getTime() - user.getDateStart().getTime()) == 7) {
                userRequets.add(userConvert.convertToUserRequest(user));
            }

        }
        return userRequets;
    }


}
