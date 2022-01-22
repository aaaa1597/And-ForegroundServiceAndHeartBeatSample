package com.tks.wearosheartbeatsample;

import android.app.AlertDialog;
import android.content.Context;

public class ErrDialog {
	/* AlertDialog生成 */
	public static AlertDialog.Builder create(Context context, String ErrStr) {
		return new AlertDialog.Builder(context)
				.setMessage(ErrStr)
				.setNegativeButton(R.string.error_end, (dialog, id) -> {
					android.os.Process.killProcess(android.os.Process.myPid());
				});
	}

	/* AlertDialog生成 */
	public static AlertDialog.Builder create(Context context, int resid) {
		return new AlertDialog.Builder(context)
				.setMessage(context.getString(resid))
				.setNegativeButton(R.string.error_end, (dialog, id) -> {
					android.os.Process.killProcess(android.os.Process.myPid());
				});
	}
}
