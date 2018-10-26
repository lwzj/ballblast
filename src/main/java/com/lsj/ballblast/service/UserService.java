package com.lsj.ballblast.service;

import com.lsj.ballblast.dto.Result;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface UserService {
    Result login(String code, String encryptedData, String iv, String channel, String ip, String shareImg, Integer shareId, String version)  throws IOException;
}
