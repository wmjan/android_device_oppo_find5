package com.cyanogenmod.settings.device;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceManager;

public class DoubleTap2WakeBarrier implements OnPreferenceChangeListener {

    private static final String FILE = "/sys/android_touch/dt2w_barrier_y";

    public static boolean isSupported() {
        return Utils.fileWritable(FILE);
    }

	public static String getValue(Context context) {
		String value = Utils.getFileValue(FILE, "3690");
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPrefs.getString(DeviceSettings.KEY_DOUBLETAP2WAKE_BARRIER, value);
	}
	
    /**
     * Restore Sweep2Wake stroke setting from SharedPreferences. (Write to kernel.)
     * @param context       The context to read the SharedPreferences from
     */
    public static void restore(Context context) {
        if (!isSupported()) {
            return;
        }

		String value = getValue(context);
        Utils.writeValue(FILE, value);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        Utils.writeValue(FILE, (String) newValue);
        return true;
    }

}
