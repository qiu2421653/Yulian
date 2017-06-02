package com.naga.yulian.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naga.yulian.dao.FsGetForkUsers;
import com.naga.yulian.vo.FsGetForksVoOutDto;

/**
 * 获得关注用户
 * 
 * @author Qiu
 *
 */
@Service("FsGetForkUsersService")
public class FsGetForkUsersService {

	@Autowired
	private FsGetForkUsers fsGetForkUsers;

	public List<FsGetForksVoOutDto> fsGetForkUsers(String userID) {
		Map<String, String> a = new HashMap<String, String>();
		a.put("userId", userID);
		return fsGetForkUsers.fsGetForkUsers(a);
	}

}
