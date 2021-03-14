package com.zhb.nettychat.service;

import com.zhb.nettychat.model.vo.ResponseJson;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Service
public interface FileUploadService {

    ResponseJson upload(MultipartFile file, HttpServletRequest request);
}
