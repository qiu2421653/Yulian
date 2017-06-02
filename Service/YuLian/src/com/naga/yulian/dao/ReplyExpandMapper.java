package com.naga.yulian.dao;

import java.util.List;
import java.util.Map;

import com.naga.yulian.entity.Reply;
import com.naga.yulian.vo.FsGetCommentTopicVo;
import com.naga.yulian.vo.FsGetTopicCommentsResultListVo;

public interface ReplyExpandMapper {
    
	List<FsGetTopicCommentsResultListVo> fsGetTopicComments(Map<String, Object> map);
	
	
	List<FsGetCommentTopicVo> selectReplyByUserId(Reply record);
}