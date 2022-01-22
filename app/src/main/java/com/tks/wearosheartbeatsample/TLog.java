package com.tks.wearosheartbeatsample;

import android.util.Log;
import java.text.MessageFormat;

public class TLog {
	public static void d(String logstr) {
		StackTraceElement throwableStackTraceElement = new Throwable().getStackTrace()[1];
		String head = MessageFormat.format("{0}::{1}({2})", throwableStackTraceElement.getClassName(), throwableStackTraceElement.getMethodName(), throwableStackTraceElement.getLineNumber());
		Log.d("aaaaa", MessageFormat.format("{0} {1}",head, logstr));
	}

	public static void d(String fmt, Object... args) {
		StackTraceElement throwableStackTraceElement = new Throwable().getStackTrace()[1];
		String head = MessageFormat.format("{0}::{1}({2})", throwableStackTraceElement.getClassName(), throwableStackTraceElement.getMethodName(), throwableStackTraceElement.getLineNumber());
		String arglogstr =  MessageFormat.format(fmt, (Object[])args);
		Log.d("aaaaa", MessageFormat.format("{0} {1}",head, arglogstr));
	}

	public static void i(String logstr) {
		StackTraceElement throwableStackTraceElement = new Throwable().getStackTrace()[1];
		String head = MessageFormat.format("{0}::{1}({2})", throwableStackTraceElement.getClassName(), throwableStackTraceElement.getMethodName(), throwableStackTraceElement.getLineNumber());
		Log.i("aaaaa", MessageFormat.format("{0} {1}",head, logstr));
	}

	public static void i(String fmt, Object... args) {
		StackTraceElement throwableStackTraceElement = new Throwable().getStackTrace()[1];
		String head = MessageFormat.format("{0}::{1}({2})", throwableStackTraceElement.getClassName(), throwableStackTraceElement.getMethodName(), throwableStackTraceElement.getLineNumber());
		String arglogstr =  MessageFormat.format(fmt, (Object[])args);
		Log.i("aaaaa", MessageFormat.format("{0} {1}",head, arglogstr));
	}

	public static void w(String logstr) {
		StackTraceElement throwableStackTraceElement = new Throwable().getStackTrace()[1];
		String head = MessageFormat.format("{0}::{1}({2})", throwableStackTraceElement.getClassName(), throwableStackTraceElement.getMethodName(), throwableStackTraceElement.getLineNumber());
		Log.w("aaaaa", MessageFormat.format("{0} {1}",head, logstr));
	}

	public static void w(String fmt, Object... args) {
		StackTraceElement throwableStackTraceElement = new Throwable().getStackTrace()[1];
		String head = MessageFormat.format("{0}::{1}({2})", throwableStackTraceElement.getClassName(), throwableStackTraceElement.getMethodName(), throwableStackTraceElement.getLineNumber());
		String arglogstr =  MessageFormat.format(fmt, (Object[])args);
		Log.w("aaaaa", MessageFormat.format("{0} {1}",head, arglogstr));
	}

	public static void e(String logstr) {
		StackTraceElement throwableStackTraceElement = new Throwable().getStackTrace()[1];
		String head = MessageFormat.format("{0}::{1}({2})", throwableStackTraceElement.getClassName(), throwableStackTraceElement.getMethodName(), throwableStackTraceElement.getLineNumber());
		Log.e("aaaaa", MessageFormat.format("{0} {1}",head, logstr));
	}

	public static void e(String fmt, Object... args) {
		StackTraceElement throwableStackTraceElement = new Throwable().getStackTrace()[1];
		String head = MessageFormat.format("{0}::{1}({2})", throwableStackTraceElement.getClassName(), throwableStackTraceElement.getMethodName(), throwableStackTraceElement.getLineNumber());
		String arglogstr =  MessageFormat.format(fmt, (Object[])args);
		Log.e("aaaaa", MessageFormat.format("{0} {1}",head, arglogstr));
	}
}
