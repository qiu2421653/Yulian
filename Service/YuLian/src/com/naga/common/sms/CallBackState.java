package com.naga.common.sms;

/**
 * 
 * @author sks
 *
 */
public enum CallBackState{
	SUCCESS("SUCCESS")
	,ERROR_01("ERROR_01")
	,ERROR_02("ERROR_02")
	,ERROR_003("ERROR_003")
	,ERROR_004("ERROR_004")
	,ERROR_005("ERROR_005")
	,ERROR_006("ERROR_006")
	,ERROR_007("ERROR_007")
	,ERROR_008("ERROR_008")
	,ERROR_009("ERROR_009")
	,ERROR_010("ERROR_010")
	,ERROR_011("ERROR_011")
	,ERROR_012("ERROR_012");
	
	private String state;
	
	private CallBackState(String state){
		this.state = state;
	}
	
	public String getState(){
		return this.state;
	}
}
