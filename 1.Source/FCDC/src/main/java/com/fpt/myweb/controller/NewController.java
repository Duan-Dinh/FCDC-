package com.fpt.myweb.controller;

import com.fpt.myweb.common.Contants;
import com.fpt.myweb.dto.request.NewRequet;
import com.fpt.myweb.dto.request.UserRequet;
import com.fpt.myweb.dto.response.CommonRes;
import com.fpt.myweb.dto.response.NewRes;
import com.fpt.myweb.dto.response.UserRes;
import com.fpt.myweb.entity.Role;
import com.fpt.myweb.entity.User;
import com.fpt.myweb.exception.AppException;
import com.fpt.myweb.exception.ErrorCode;
import com.fpt.myweb.service.NewService;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping(value = "/new")
public class NewController {

    @Autowired
    private NewService newService;
    @Autowired
    ObjectFactory<HttpSession> httpSessionFactory;
    @GetMapping("/get")
    public ResponseEntity<CommonRes> getNew(@PathParam("page") Integer page) {
        CommonRes commonRes = new CommonRes();
        try {


                commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
                commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
                List<NewRequet> newList = newService.getAllNew(page);
                NewRes newRes = new NewRes();
                newRes.setNewRequets(newList);
                newRes.setTotal(newService.countByTesxt());
                commonRes.setData(newRes);



        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }
    //Top 6 new
    @GetMapping("/getTop")
    public ResponseEntity<CommonRes> getTopNew(@PathParam("page") Integer page) {
        CommonRes commonRes = new CommonRes();
        try {

                commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
                commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
                List<NewRequet> newList = newService.getTopNew(page);
                commonRes.setData(newList);



        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }

    // getOne
    @GetMapping(value = "/getNew/{id}")
    public ResponseEntity<CommonRes> getUser1(@PathVariable("id") long id) {
        CommonRes commonRes = new CommonRes();
        try {


                commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
                commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
                NewRequet newRequet = newService.getNew(id);
                commonRes.setData(newRequet);



        } catch (AppException a){
            commonRes.setResponseCode(a.getErrorCode());
            commonRes.setMessage(a.getErrorMessage());
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }

    @PostMapping("/add")
    public ResponseEntity<CommonRes> addNew(NewRequet newRequet,@RequestParam("file") MultipartFile file) {
        CommonRes commonRes = new CommonRes();
        try {

                commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
                commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
                newService.addNew(newRequet,file);
                commonRes.setData(newService);



        } catch (AppException a){
            commonRes.setResponseCode(a.getErrorCode());
            commonRes.setMessage(a.getErrorMessage());
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }

    @PutMapping("/edit")
    public ResponseEntity<CommonRes> editNew(NewRequet newRequet, @RequestParam("file") MultipartFile file) {
        CommonRes commonRes = new CommonRes();
        try {

                commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
                commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
                newService.editNew(newRequet,file);
                commonRes.setData(newService);

        } catch (AppException a){
            commonRes.setResponseCode(a.getErrorCode());
            commonRes.setMessage(a.getErrorMessage());
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<CommonRes> deleteNew(@PathParam("id") Integer id) {
        CommonRes commonRes = new CommonRes();
        try {
                commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
                commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
                newService.deleteNew(id);


        } catch (AppException a){
            commonRes.setResponseCode(a.getErrorCode());
            commonRes.setMessage(a.getErrorMessage());
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }

    // get usser by title in New
    @GetMapping("/searchTitle")
    public ResponseEntity<CommonRes> getAllByText(@PathParam("key") String key,@PathParam("page") Integer page) {
        CommonRes commonRes = new CommonRes();
        try {
                commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
                commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
                List<NewRequet> newRequets = newService.searchByTitle(key,page);
                NewRes newRes = new NewRes();
                newRes.setNewRequets(newRequets);
                newRes.setTotal(newService.countsearchByTitle(key));
                commonRes.setData(newRes);


        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }
}
