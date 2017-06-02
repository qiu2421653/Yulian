package com.naga.yulian.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naga.yulian.dao.FsGetForkLoveStoryMapper;
import com.naga.yulian.vo.FsGetForkLoveStoryOutDtoVo;

/**
 * 获取对应标签集合
 * 
 * @author miaowei
 *
 */
@Service("FsGetForkLoveStoryService")
public class FsGetForkLoveStoryService {

	@Autowired
	private FsGetForkLoveStoryMapper fsGetForkLoveStoryMapper;

	/**
	 * tagList
	 * 
	 */
	public List<FsGetForkLoveStoryOutDtoVo> getFsGetTagListVoList(String userID) {
		Map<String, String> a = new HashMap<String, String>();
		a.put("userid", userID);
		return fsGetForkLoveStoryMapper.getFsGetForkLoveStory(a);
	}

	/**
	 * tagList
	 * 
	 */
	public Map<String, String> getForkAndFun(String userID) {
		Map<String, String> p = new HashMap<String, String>();
		p.put("userid", userID);
		Map<String, Object> a = new HashMap<String, Object>();
		a = fsGetForkLoveStoryMapper.getForkAndFun(p);
		if(a==null){
			p.put("forkNum","0");
			p.put("funsNum","0");
			
		}else{
			p.put("forkNum", ((Number) a.get("forkNum")).toString());
			p.put("funsNum", ((Number) a.get("funsNum")).toString());
			
		}
		return p;
	}

	/**
	 * 
	 */
	public Map<String, String> getForkFunAndIsFollow(String userId, String createrId) {
		Map<String, String> p = new HashMap<String, String>();
		p.put("userid", userId);
		p.put("createrid", createrId);
		Map<String, Object> a = new HashMap<String, Object>();
		a = fsGetForkLoveStoryMapper.getForkFunAndIsFollow(p);
		p.put("forkNum", ((Number) a.get("forkNum")).toString());
		p.put("funsNum", ((Number) a.get("funsNum")).toString());
		p.put("isFollow", ((Number) a.get("isFollow")).toString());
		return p;
	}

}
