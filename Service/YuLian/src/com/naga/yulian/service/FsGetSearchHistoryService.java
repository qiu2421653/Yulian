package com.naga.yulian.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naga.yulian.dao.FsGetSearchHistoryMapper;
import com.naga.yulian.vo.FsGetSearchUserOutDTOVo;
import com.naga.yulian.vo.FsGetSearchUserRecommendVo;
import com.naga.yulian.vo.FsGetSearchUserUrlListVo;

/**
 * 获取对应标签集合
 * 
 * @author miaowei
 *
 */
@Service("FsGetSearchHistoryService")
public class FsGetSearchHistoryService {

	@Autowired
	private FsGetSearchHistoryMapper fsGetSearchHistoryMapper;

	/**
	 * tagList
	 * 
	 */
	public List<FsGetSearchUserOutDTOVo> FsGetSearchHistoryMapper(String userID, String content) {
		Map<String, String> a = new HashMap<String, String>();
		a.put("userID", userID);
		a.put("content", content);
		List<FsGetSearchUserOutDTOVo> listReturn = new ArrayList<FsGetSearchUserOutDTOVo>();
		FsGetSearchUserOutDTOVo fs = new FsGetSearchUserOutDTOVo();
		List<FsGetSearchUserRecommendVo> lista = fsGetSearchHistoryMapper.getSearchUserRecommendList(a);

		if (lista != null && !lista.isEmpty()) {
			for (int n = 0; n < lista.size(); n++) {

				FsGetSearchUserRecommendVo fsRecommend = lista.get(n);

				List<FsGetSearchUserUrlListVo> listb = fsGetSearchHistoryMapper.getSearchUserUrlList(a);

				fsRecommend.setUrlList(listb);

			}
		}

		fs.setRecommend(lista);
		listReturn.add(fs);
		return listReturn;
	}

}
