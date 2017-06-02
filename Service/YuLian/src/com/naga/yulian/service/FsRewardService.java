package com.naga.yulian.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naga.yulian.dao.AccountingMapper;
import com.naga.yulian.dao.RewardMapper;
import com.naga.yulian.dao.TopicMapper;
import com.naga.yulian.dao.UserMapper;
import com.naga.yulian.entity.Accounting;
import com.naga.yulian.entity.Reward;
import com.naga.yulian.entity.Topic;
import com.naga.yulian.entity.User;
import com.naga.yulian.vo.FsRewardVo;

/**
 * 打赏
 * 
 * @author wangyan
 *
 */
@Service("FsRewardService")
public class FsRewardService {
	
	@Autowired
	private AccountingMapper accountingMapper;

    @Autowired
    private RewardMapper rewardMapper;
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private TopicMapper topicMapper;

    /**
     * 打赏
     * @param vo
     * @return
     */
	public int fsReward(FsRewardVo vo,User user) {
		int result = 0;
		// 根据帖子id查找对应用户id
		Topic topic = topicMapper.selectByPrimaryKey(vo.getInfoId());
		String toUuid = topic.getCreaterId();
		// 查询对象user
		User toUser = userMapper.selectByPrimaryKey(toUuid);
		// 实例化
		Accounting accounting = new Accounting();
		// 赏出
		// 设值
		accounting.setUuid(UUID.randomUUID().toString());
		accounting.setTopid(vo.getInfoId());
		accounting.setCreaterid(user.getUuid());
		accounting.setUserid(toUuid);
		accounting.setCreatedate(new Date());
		accounting.setType(1);
		accounting.setCurrency(0-vo.getReward().intValue());
		accounting.setCurrencybefore(user.getCurrency().intValue());
		// 插入
		accountingMapper.insertSelective(accounting);
		
		// 实例化
		accounting = new Accounting();
		// 赏入
		// 设值
		accounting.setUuid(UUID.randomUUID().toString());
		accounting.setTopid(vo.getInfoId());
		accounting.setUserid(vo.getUserId());
		accounting.setCreaterid(toUuid);
		accounting.setCreatedate(new Date());
		accounting.setType(2);
		accounting.setCurrency(vo.getReward().intValue());
		accounting.setCurrencybefore(toUser.getCurrency().intValue());
		// 插入
		accountingMapper.insertSelective(accounting);
				
		// 插入成功
		// 减掉改用户原有钱数
		user.setCurrency(user.getCurrency().subtract(vo.getReward()));
		if(userMapper.updateByPrimaryKeySelective(user)>0){
			// 减钱成功 加钱
			toUser.setCurrency(toUser.getCurrency().add(vo.getReward()));
			result = userMapper.updateByPrimaryKeySelective(toUser);
		}
		return result;
	}

   

}
