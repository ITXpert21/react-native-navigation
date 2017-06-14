package com.reactnativenavigation;

import android.app.Application;
import android.content.Context;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.reactnativenavigation.react.ReactGateway;

public abstract class NavigationApplication extends Application implements ReactApplication {

	public static Context context;
	private ReactGateway reactGateway;

	@Override
	public void onCreate() {
		super.onCreate();
		context = this;
		reactGateway = new ReactGateway(this, isDebug());
	}

	public ReactGateway getReactGateway() {
		return reactGateway;
	}

	@Override
	public ReactNativeHost getReactNativeHost() {
		return getReactGateway().getReactNativeHost();
	}

	public abstract boolean isDebug();
}
