package com.tks.wearosheartbeatsample.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FragMainViewModel extends ViewModel {
	private final MutableLiveData<Boolean>	mUnLock			= new MutableLiveData<>(true);
	public MutableLiveData<Boolean>			UnLock()		{ return mUnLock; }
	private final MutableLiveData<Integer>	mHeartBeat			= new MutableLiveData<>(-1);
	public MutableLiveData<Integer>			HeartBeat()		{ return mHeartBeat; }
}
