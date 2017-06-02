package com.naga.yulian.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naga.yulian.dao.FsSearchUserMapper;
import com.naga.yulian.vo.FsSearchUserDTOVo;
import com.naga.yulian.vo.FsSearchUserRecommendVo;

/**
 * 获取对应标签集合
 * 
 * @author miaowei
 *
 */
@Service("FsSearchUserService")
public class FsSearchUserService {

    @Autowired
    private FsSearchUserMapper fsSearchUserMapper;

    /**
     * tagList
     * 
     */
    public List<FsSearchUserDTOVo> getFsGetTagListVoList(String userID, String content) {
        Map<String, String> a = new HashMap<String, String>();
        a.put("userID", userID);
        a.put("content", content);
        List<FsSearchUserDTOVo> listReturn = new ArrayList<FsSearchUserDTOVo>();
        FsSearchUserDTOVo fs = new FsSearchUserDTOVo();
        List<FsSearchUserRecommendVo> lista = fsSearchUserMapper.getFsSearchUserVoList(a);
        fs.setRecommend(lista);
        listReturn.add(fs);
        return listReturn;
    }

}
