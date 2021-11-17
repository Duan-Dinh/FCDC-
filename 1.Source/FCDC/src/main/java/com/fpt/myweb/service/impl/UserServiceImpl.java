package com.fpt.myweb.service.impl;


import com.fpt.myweb.common.Contants;
import com.fpt.myweb.convert.UserConvert;
import com.fpt.myweb.dto.request.UserRequet;
import com.fpt.myweb.dto.response.ChartStaffRes;
import com.fpt.myweb.dto.response.DetailOneDayRes;
import com.fpt.myweb.dto.response.ResetPassRes;
import com.fpt.myweb.entity.FileDB;
import com.fpt.myweb.entity.Role;
import com.fpt.myweb.entity.User;
import com.fpt.myweb.entity.Village;
import com.fpt.myweb.exception.AppException;
import com.fpt.myweb.exception.ErrorCode;
import com.fpt.myweb.repository.FileDBRepository;
import com.fpt.myweb.repository.RoleRepository;
import com.fpt.myweb.repository.UserRepository;
import com.fpt.myweb.repository.VillageRepository;
import com.fpt.myweb.service.SmsService;
import com.fpt.myweb.service.UserService;
import com.fpt.myweb.utils.GetUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.Style;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.text.DateFormat;
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

    @Autowired
    private FileDBRepository fileDBRepository;

    @Override
    public List<UserRequet> getAllUser() {
        List<User> userList = userRepository.findAll();
        List<UserRequet> userRequets = new ArrayList<>();
        for (User user : userList) {
            if (Integer.parseInt(user.getIs_active()) == 1) {
                UserRequet userRequet = userConvert.convertToUserRequest(user);
                userRequets.add(userRequet);
            }
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
    public User addUser(UserRequet userRequet, MultipartFile file) throws ParseException, IOException {

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
        user.setImageUrl(userRequet.getImageUrl()

        );


        user.setCreatedDate(new Date());
        user.setIs_active("1");
        if (userRequet.getStartOfDate() != null) {
            Date date1 = new SimpleDateFormat(Contants.DATE_FORMAT).parse(userRequet.getStartOfDate());
            user.setDateStart(date1);
        }
        if (userRequet.getRole_id() == 4L) {
            user.setResult("F0");
        }
        user.setTypeTakeCare("1");
        // luu file
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes());

        FileDB fileDB = fileDBRepository.save(FileDB);
        user.setFiles(fileDB);


        User user1 = userRepository.save(user);
        smsService.sendGetJSON(user.getPhone(), "Tài khoản của bạn đã được khởi tạo");
        return user1;
    }

    @Override
    public boolean checkPhone(String phone) {
        return false;
    }

    @Override
    public UserRequet deleteUser(long id) {
        User user = userRepository.findById(id).orElseThrow(()
                -> new AppException(ErrorCode.NOT_FOUND_ID.getKey(), ErrorCode.NOT_FOUND_ID.getValue() + id));
        user.setIs_active("0");
        UserRequet userRequet = userConvert.convertToUserRequest(user);
        userRepository.save(user);
        return userRequet;
    }

    @Override
    public ResetPassRes resetPass(String phone) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = userRepository.findUsersByPhone(phone);
        if(user!=null){
            String pas = GetUtils.generateRandomPassword(6);
            user.setPassword(passwordEncoder.encode(pas));
            ResetPassRes resetPassRes = new ResetPassRes();
            resetPassRes.setPhone(user.getPhone());
            resetPassRes.setPass(pas);
            userRepository.save(user);
            return resetPassRes;
        }
        return null;
    }

    @Override
    public UserRequet changePass(long id, String newPass) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = userRepository.findById(id).orElseThrow(()
                -> new AppException(ErrorCode.NOT_FOUND_ID.getKey(), ErrorCode.NOT_FOUND_ID.getValue() + id));
        user.setPassword(passwordEncoder.encode(newPass));
        userRepository.save(user);
        UserRequet userRequet = userConvert.convertToUserRequest(user);

        return userRequet;
    }

    @Override
    public UserRequet edit(UserRequet userRequet, MultipartFile file) throws ParseException, IOException {
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
        Date date1 = new SimpleDateFormat(Contants.DATE_FORMAT).parse(userRequet.getStartOfDate());
        user.setDateStart(date1);
        // luu file
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes());
        FileDB.setId(user.getFiles().getId());
        FileDB fileDB = fileDBRepository.save(FileDB);

        user.setFiles(fileDB);


        UserRequet userRequet1 = userConvert.convertToUserRequest(userRepository.save(user));
        return userRequet1;
    }

    @Override
    public UserRequet editUserById(UserRequet userRequet, MultipartFile file) throws ParseException, IOException {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = userRepository.findById(userRequet.getId()).orElseThrow(()
                -> new AppException(ErrorCode.NOT_FOUND_ID.getKey(), ErrorCode.NOT_FOUND_ID.getValue() + userRequet.getId()));

        // luu file
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes());
        FileDB.setId(user.getFiles().getId());
        FileDB fileDB = fileDBRepository.save(FileDB);

        user.setFiles(fileDB);

        UserRequet userRequet1 = userConvert.convertToUserRequest(userRepository.save(user));
        return userRequet1;
    }

    @Override
    public UserRequet editResult(long id) {
        User user = userRepository.findById(id).orElseThrow(()
                -> new AppException(ErrorCode.NOT_FOUND_ID.getKey(), ErrorCode.NOT_FOUND_ID.getValue() + id));

        if(user.getResult().equals("-")){
            user.setResult("F0");
        }else{
            user.setResult("-");
        }
        user.setModifiedDate(new Date());
        UserRequet userRequet = userConvert.convertToUserRequest(user);
        userRepository.save(user);
        return userRequet;
    }

    @Override
    public UserRequet changeTypeTakeCare(long id,Long doctorId) {
        User user = userRepository.findById(id).orElseThrow(()
                -> new AppException(ErrorCode.NOT_FOUND_ID.getKey(), ErrorCode.NOT_FOUND_ID.getValue() + id));
        user.setTypeTakeCare(String.valueOf(doctorId));
        UserRequet userRequet = userConvert.convertToUserRequest(user);
        userRepository.save(user);
        return userRequet;
    }

    @Override
    public List<UserRequet> getAllDoctorByVila(Long vilageId) {
        Village village = villageRepository.findById(vilageId).orElseThrow(()
                -> new AppException(ErrorCode.NOT_FOUND_VILLAGE_ID.getKey(), ErrorCode.NOT_FOUND_VILLAGE_ID.getValue() + vilageId));
        List<User> searchList = userRepository.findAllByVillage(village);
        List<UserRequet> userRequets = new ArrayList<>();
        for (User user : searchList) {
            if (Integer.parseInt(user.getIs_active()) == 1 && (user.getRole().getId()) == 3) {
                userRequets.add(userConvert.convertToUserRequest(user));
            }
        }
        return userRequets;
    }


    @Override
    public List<UserRequet> searchByRole(Long role_id) {

        List<User> searchList = userRepository.findAllUserByRoleId(role_id);
        List<UserRequet> userRequets = new ArrayList<>();
        for (User user : searchList) {
            if (Integer.parseInt(user.getIs_active()) == 1) {
                userRequets.add(userConvert.convertToUserRequest(user));
            }
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
    public List<UserRequet> searchByTesxt(String text) {

        List<User> searchList = userRepository.findByFullnameContaining(text);
        List<UserRequet> userRequets = new ArrayList<>();
        for (User user : searchList) {
            if (Integer.parseInt(user.getIs_active()) == 1) {
                userRequets.add(userConvert.convertToUserRequest(user));
            }
        }
        return userRequets;
    }

    @Override
    public List<UserRequet> searchByTextWithRole(String text, Long roleId) {
        List<User> searchList = userRepository.findByFullnameContaining(text);
        List<UserRequet> userRequets = new ArrayList<>();
        for (User user : searchList) {
            if (Integer.parseInt(user.getIs_active()) == 1 && user.getRole().getId() == roleId) {
                userRequets.add(userConvert.convertToUserRequest(user));
            }
        }
        return userRequets;
    }

    @Override
    public List<UserRequet> searchByTextForStaff(String text, Long villageId) {
        Village village = villageRepository.findById(villageId).orElseThrow(()
                -> new AppException(ErrorCode.NOT_FOUND_VILLAGE_ID.getKey(), ErrorCode.NOT_FOUND_VILLAGE_ID.getValue() + villageId));
        List<User> userList = userRepository.findAllByVillage(village);
        List<UserRequet> userRequets = new ArrayList<>();
        for (User user : userList) {
            if (Integer.parseInt(user.getIs_active()) == 1 && user.getRole().getId() == 4L && user.getFullname().contains(text)) {
                userRequets.add(userConvert.convertToUserRequest(user));
            }
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
        if (passwordEncoder.matches(password, user.getPassword()) && Integer.parseInt(user.getIs_active()) == 1) {
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
                Role role = roleRepository.findByName("patient");
                user.setRole(role);
                user.setResult("F0");
                user.setIs_active("1");
                user.setTypeTakeCare("-1");
                // check User by phone
                User userCheck = userRepository.findByPhone(user.getPhone());

                user.setPassword(passwordEncoder.encode("123"));

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
                    userCheck.setResult("F0");
                    user.setTypeTakeCare("-1");
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
                Role role = roleRepository.findByName("staff");
                user.setRole(role);
                user.setIs_active("1");
                user.setTypeTakeCare("-1");
                // check User by phone
                User userCheck = userRepository.findByPhone(user.getPhone());
                user.setPassword(passwordEncoder.encode("123"));
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
                    userCheck.setIs_active("1");
                    user.setTypeTakeCare("-1");
                    userRepository.save(userCheck);
                }
                // send pass to user with phone
                // test
//               smsService.sendGetJSON("0385422617", "Hello Quảng!");

            }

            workbook.close();
            inputStream.close();

        }
    }

    @Override
    public void importUserDoctor(MultipartFile file) throws IOException, ParseException {
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
                    }

                }
                Role role = roleRepository.findByName("doctor");
                user.setRole(role);
                user.setIs_active("1");
                user.setTypeTakeCare("-1");
                // check User by phone
                User userCheck = userRepository.findByPhone(user.getPhone());

                user.setPassword(passwordEncoder.encode("123"));

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
                    user.setTypeTakeCare("-1");
                    userCheck.setIs_active("1");
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

    private void CreateCell(XSSFSheet sheet, Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    @Override
    public void exportUserPatient(HttpServletResponse response, String time) throws IOException, ParseException {

        Object value = null;
        //List<UserRequet> listUser = toTestCovid(time);
        List<UserRequet> listUser = new ArrayList<>();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("User");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(17);
        font.setBoldweight((short) 18);
        style.setFont(font);
        CreateCell(sheet, row, 0, "Họ và Tên", style);
        CreateCell(sheet, row, 1, "Giới Tính", style);
        CreateCell(sheet, row, 2, "Ngày Sinh", style);
        CreateCell(sheet, row, 3, "Email", style);
        CreateCell(sheet, row, 4, "Số điện thoại", style);
        CreateCell(sheet, row, 5, "Ngày phát hiện", style);
        CreateCell(sheet, row, 6, "Phường,Xã", style);
        int rowCount = 1;
        for (UserRequet user : listUser) {
            CellStyle styleOfRow = workbook.createCellStyle();
            XSSFFont fontt = workbook.createFont();
            fontt.setFontHeight(14);
            style.setFont(fontt);
            row = sheet.createRow(rowCount++);
            int columCount = 0;
            CreateCell(sheet, row, columCount++, user.getFullname(), styleOfRow);
            CreateCell(sheet, row, columCount++, user.getGender(), styleOfRow);
            CreateCell(sheet, row, columCount++, user.getBirthOfdate(), styleOfRow);
            CreateCell(sheet, row, columCount++, user.getEmail(), styleOfRow);
            CreateCell(sheet, row, columCount++, user.getPhone(), styleOfRow);
            CreateCell(sheet, row, columCount++, user.getStartOfDate(), styleOfRow);
            CreateCell(sheet, row, columCount++, user.getAddress(), styleOfRow);
        }

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    @Override
    public List<UserRequet> notSentReport(String time,Long villageId) {
        List<User> searchList = userRepository.notSentReport(time);
        List<UserRequet> userRequets = new ArrayList<>();
        for (User user : searchList) {
            if (Integer.parseInt(user.getIs_active()) == 1&& user.getVillage().getId() == villageId.longValue()) {
                userRequets.add(userConvert.convertToUserRequest(user));
            }
        }
        return userRequets;
    }

    @Override
    public List<UserRequet> sentReport(String time) {
        List<User> searchList = userRepository.sentReport(time);
        List<UserRequet> userRequets = new ArrayList<>();
        for (User user : searchList) {
            if (Integer.parseInt(user.getIs_active()) == 1) {
                userRequets.add(userConvert.convertToUserRequest(user));
            }
        }
        return userRequets;
    }

    @Override
    public List<UserRequet> toTestCovid(String time,Long villageId) throws ParseException {
        Long role_id = 4L;
        Role role = roleRepository.findById(role_id).orElseThrow(()
                -> new AppException(ErrorCode.NOT_FOUND_ROLE_ID.getKey(), ErrorCode.NOT_FOUND_ROLE_ID.getValue() + role_id));
        List<User> searchList = userRepository.findByRole(role);
        Date date = new SimpleDateFormat(Contants.DATE_FORMAT).parse(time);
        List<UserRequet> userRequets = new ArrayList<>();
        for (User user : searchList) {
            if ((TimeUnit.MILLISECONDS.toDays(date.getTime() - user.getDateStart().getTime()) == 14 || TimeUnit.MILLISECONDS.toDays(date.getTime() - user.getDateStart().getTime()) == 21) && user.getResult().equals("F0")&& user.getVillage().getId() == villageId.longValue()) {
                if (Integer.parseInt(user.getIs_active()) == 1) {
                    userRequets.add(userConvert.convertToUserRequest(user));
                }
            }
        }
        return userRequets;
    }

    @Override
    public List<UserRequet> getAllPatientForDoctor(String doctorId) {
        Long role_id = 4L;
        Role role = roleRepository.findById(role_id).orElseThrow(()
                -> new AppException(ErrorCode.NOT_FOUND_ROLE_ID.getKey(), ErrorCode.NOT_FOUND_ROLE_ID.getValue() + role_id));
        List<User> userList = userRepository.findByRole(role);
        List<UserRequet> userRequets = new ArrayList<>();
        for (User user : userList) {
            if (Integer.parseInt(user.getIs_active()) == 1 && user.getTypeTakeCare().equals(doctorId)) {
                userRequets.add(userConvert.convertToUserRequest(user));
            }
        }
        return userRequets;
    }

    @Override
    public List<UserRequet> getAllPatientForStaff(Long VillageId) {
        Village village = villageRepository.findById(VillageId).orElseThrow(()
                -> new AppException(ErrorCode.NOT_FOUND_VILLAGE_ID.getKey(), ErrorCode.NOT_FOUND_VILLAGE_ID.getValue() + VillageId));
        List<User> userList = userRepository.findAllByVillage(village);
        List<UserRequet> userRequets = new ArrayList<>();
        for (User user : userList) {
            if (Integer.parseInt(user.getIs_active()) == 1 && user.getRole().getId() == 4L && user.getResult().equals("F0")) {
                userRequets.add(userConvert.convertToUserRequest(user));
            }
        }
        return userRequets;
    }

    @Override
    public List<UserRequet> getNewPatientOneDay(String time,Long villageId) throws ParseException {
        Village village = villageRepository.findById(villageId).orElseThrow(()
                -> new AppException(ErrorCode.NOT_FOUND_VILLAGE_ID.getKey(), ErrorCode.NOT_FOUND_VILLAGE_ID.getValue() + villageId));
        List<User> searchList = userRepository.findAllByVillage(village);
        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        List<UserRequet> userRequets = new ArrayList<>();
        for (User user : searchList) {
            if (sdf.format(user.getCreatedDate()).equals(time) && user.getRole().getId() == 4L && user.getResult().equals("F0") ) {
                if (Integer.parseInt(user.getIs_active()) == 1) {
                    userRequets.add(userConvert.convertToUserRequest(user));
                }
            }
        }
        return userRequets;
    }

    @Override
    public List<UserRequet> getCuredPatientOneDay(String time, Long villageId) throws ParseException {
        Village village = villageRepository.findById(villageId).orElseThrow(()
                -> new AppException(ErrorCode.NOT_FOUND_VILLAGE_ID.getKey(), ErrorCode.NOT_FOUND_VILLAGE_ID.getValue() + villageId));
        List<User> searchList = userRepository.findAllByVillage(village);
        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        List<UserRequet> userRequets = new ArrayList<>();
        for (User user : searchList) {
            if(user.getModifiedDate()!=null){
            if (sdf.format(user.getModifiedDate()).equals(time) && user.getRole().getId() == 4L && user.getResult().equals("-") ) {
                if (Integer.parseInt(user.getIs_active()) == 1) {
                    userRequets.add(userConvert.convertToUserRequest(user));
                }
            }}
        }
        return userRequets;
    }

    @Override
    public List<UserRequet> getAllPatientCuredForStaff(Long VillageId) {
        Village village = villageRepository.findById(VillageId).orElseThrow(()
                -> new AppException(ErrorCode.NOT_FOUND_VILLAGE_ID.getKey(), ErrorCode.NOT_FOUND_VILLAGE_ID.getValue() + VillageId));
        List<User> userList = userRepository.findAllByVillage(village);
        List<UserRequet> userRequets = new ArrayList<>();
        for (User user : userList) {
            if (Integer.parseInt(user.getIs_active()) == 1 && user.getRole().getId() == 4L && user.getResult().equals("-")) {
                userRequets.add(userConvert.convertToUserRequest(user));
            }
        }
        return userRequets;
    }

    @Override
    public List<ChartStaffRes> getChartForStaff(String startDate, String endDate,Long villageId) throws ParseException {
        List<Date> dates = new ArrayList<Date>();
        DateFormat formatter ;
        formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date  srtDate = (Date)formatter.parse(startDate);
        Date  edDate = (Date)formatter.parse(endDate);
        long interval = 24*1000 * 60 * 60; // 1 hour in millis
        long endTime =edDate.getTime() ; // create your endtime here, possibly using Calendar or Date
        long curTime = srtDate.getTime();
        while (curTime <= endTime) {
            dates.add(new Date(curTime));
            curTime += interval;
        }
        List<ChartStaffRes> chartStaffResList = new ArrayList<>();

        for(int i=0;i<dates.size();i++){
            Date lDate =(Date)dates.get(i);
            String ds = formatter.format(lDate);
            DetailOneDayRes detailOneDayRes = new DetailOneDayRes();
            detailOneDayRes.setTotalNewF0(getNewPatientOneDay(ds,villageId).size());
            detailOneDayRes.setTotalCured(getCuredPatientOneDay(ds,villageId).size());
            detailOneDayRes.setTotalCurrentF0(getAllPatientForStaff(villageId).size());
            ChartStaffRes chartStaffRes1 = new ChartStaffRes();
            chartStaffRes1.setDate(ds);
            chartStaffRes1.setDetailOneDayRes(detailOneDayRes);
            chartStaffResList.add(chartStaffRes1);
        }
        return chartStaffResList;
    }

}
