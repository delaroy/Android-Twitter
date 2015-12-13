package androidtwitter.delaroystudios.com.androidtwitter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class Utils {

	public static boolean isNetworkConnected(Context context) {
		ConnectivityManager mConnectManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (mConnectManager != null) {
			NetworkInfo[] mNetworkInfo = mConnectManager.getAllNetworkInfo();
			for (int i = 0; i < mNetworkInfo.length; i++) {
				if (mNetworkInfo[i].getState() == NetworkInfo.State.CONNECTED)
					return true;
			}
		}

		/*
		 * Toast toast = Toast.makeText(activity, activity.getResources()
		 * .getString(R.string.no_internet), Toast.LENGTH_SHORT);
		 * toast.setGravity(Gravity.CENTER, 0, 0); toast.show();
		 */

		return false;
	}

}
