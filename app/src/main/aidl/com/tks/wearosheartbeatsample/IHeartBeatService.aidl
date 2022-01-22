// IHeartBeatService.aidl
package com.tks.wearosheartbeatsample;
import com.tks.wearosheartbeatsample.IOnHeartBeatListner;

interface IHeartBeatService {
	void setOnHeartBeatListner(IOnHeartBeatListner listner);
}
