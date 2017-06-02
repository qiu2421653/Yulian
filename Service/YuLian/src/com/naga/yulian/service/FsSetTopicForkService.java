package com.naga.yulian.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naga.yulian.dao.PraiseExpandMapper;
import com.naga.yulian.dao.PraiseMapper;
import com.naga.yulian.entity.Praise;
import com.naga.yulian.vo.FsGetTopicForksResultListVo;

/**
 * 点赞/取消赞service
 * 
 * @author wangyan
 *
 */
@Service("FsSetTopicForkService")
public class FsSetTopicForkService {

	@Autowired
    private PraiseMapper praiseMapper;
	
	@Autowired
	private PraiseExpandMapper praiseExpandMapper;
	
	/**
	 * 
	 * @param userId
	 * @param infoId
	 * @param isFork
	 * @return
	 */
    public int fsSetTopicFork(String userId, String infoId, boolean isFork,int status) {
    	// 返回值
    	int resultCount = 0;
    	// 判断是否点赞
    	if(isFork){
    		// 点赞
    		// 实例化
    		Praise praiseEntity = new Praise();
    		// 设值
    		praiseEntity.setUuid(UUID.randomUUID().toString());
    		praiseEntity.setTopId(infoId);
    		praiseEntity.setCreaterId(userId);
    		praiseEntity.setStatus((short) status);
    		praiseEntity.setCreateDate(new Date());
    		// 执行插入
            resultCount = praiseMapper.insertSelective(praiseEntity);
    	}else{
    		// 取消赞
    		// 实例化Map
    		Map<String,String> map = new HashMap<>();
    		// 将信息设置到map中
    		map.put("userId", userId);
    		map.put("infoId", infoId);
    		// 根据用户id、帖子id删除点赞记录
    		resultCount = praiseExpandMapper.deleteByTopId(map);
    	}
    	// 返回
    	return resultCount;
    }

	public List<FsGetTopicForksResultListVo> fsGetTopicForks(String infoId, int forkCount, int forkPage, String userId) {
		// 实例化Map
		Map<String,Object> map = new HashMap<>();
		// 将信息设置到map中
		map.put("infoId", infoId);
		map.put("forkCount", forkCount);
		map.put("forkPage", forkPage);
		map.put("userId", userId);
		return praiseExpandMapper.fsGetTopicForks(map);
	}
	
}
