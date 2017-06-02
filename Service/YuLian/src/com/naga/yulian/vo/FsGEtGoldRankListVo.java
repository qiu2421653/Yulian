package com.naga.yulian.vo;

import java.util.List;

public class FsGEtGoldRankListVo {

	private List<FsGEtGoldRankVo> fsGEtGoldRankVoList;

	private String token;

	private String rankDesc;

	private String gold;

	private String hPic;

	public List<FsGEtGoldRankVo> getFsGEtGoldRankVoList() {
		return fsGEtGoldRankVoList;
	}

	public void setFsGEtGoldRankVoList(List<FsGEtGoldRankVo> fsGEtGoldRankVoList) {
		this.fsGEtGoldRankVoList = fsGEtGoldRankVoList;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRankDesc() {
		return rankDesc;
	}

	public void setRankDesc(String rankDesc) {
		this.rankDesc = rankDesc;
	}

	public String getGold() {
		return gold;
	}

	public void setGold(String gold) {
		this.gold = gold;
	}

	public String gethPic() {
		return hPic;
	}

	public void sethPic(String hPic) {
		this.hPic = hPic;
	}

}