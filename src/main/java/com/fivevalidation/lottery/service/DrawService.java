package com.fivevalidation.lottery.service;

import com.fivevalidation.lottery.dto.response.DrawResult;

import java.util.List;

public interface DrawService {
    List<DrawResult> getDrawResult();
}
