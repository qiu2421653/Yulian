package com.naga.yulian.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naga.yulian.dao.ReplyExpandMapper;
import com.naga.yulian.vo.FsGetTopicCommentsResultListVo;

/**
 * 获得评论信息service
 * 
 * @author miaowei
 *
 */
@Service("FsGetTopicCommentsService")
public class FsGetTopicCommentsService {

    @Autowired
    private ReplyExpandMapper replyExpandMapper;

	public List<FsGetTopicCommentsResultListVo> fsGetTopicComments(String infoId, int commentCount,
			int commentPage) {
		// 初始化
		Map<String,Object> map = new HashMap<>();
		// 设值
		map.put("infoId", infoId);
		map.put("commentCount", commentCount);
		map.put("commentPage", commentPage*commentCount);
		// 返回
		return replyExpandMapper.fsGetTopicComments(map);
	}

    
}
