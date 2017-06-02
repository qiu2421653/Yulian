package com.naga.yulian.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naga.yulian.dao.FsCreateLoveMapper;
import com.naga.yulian.entity.Experience;
import com.naga.yulian.entity.Media;
import com.naga.yulian.vo.FsCreateLoveParameter;

@Service("FsCreateLoveService")
public class FsCreateLoveService {
    @Autowired
    private FsCreateLoveMapper fsCreateLoveMapper;

    public int insert(FsCreateLoveParameter fsCreateLoveParameter) {

        int returnResult = 0;

        Experience experienceVo = new Experience();

        experienceVo.setStatus((short) 10);

        experienceVo.setCreaterId(fsCreateLoveParameter.getUserID());

        int i = fsCreateLoveMapper.updateExperience(experienceVo);
           
        Media mediaVo = new Media();

        String uuid = UUID.randomUUID().toString();
        // 设值
        experienceVo.setUuid(uuid);

        experienceVo.setName(fsCreateLoveParameter.getLoveDesc());

        experienceVo.setRemark("");

        experienceVo.setCreateDate(new Date());

        i = fsCreateLoveMapper.createLove(experienceVo);
        if (i == 1) {
            mediaVo.setUuid(UUID.randomUUID().toString());

            mediaVo.setFkId(uuid);

            mediaVo.setPath(fsCreateLoveParameter.getThumb());

            mediaVo.setContent(fsCreateLoveParameter.getLoveDesc());

            mediaVo.setHeight(fsCreateLoveParameter.getHeight());

            mediaVo.setWidth(fsCreateLoveParameter.getWidth());

            returnResult = fsCreateLoveMapper.createLovePath(mediaVo);

        } else {
            returnResult = 0;
        }

        return returnResult;
    }
}
