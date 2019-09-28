package com.example.networktracking;

import android.os.Build;
import android.telephony.SignalStrength;
import android.util.Log;

import androidx.annotation.RequiresApi;

public class PhoneStateListener extends android.telephony.PhoneStateListener {
    int mSignalStrength = 0;
    int mSiggnalCDM = 0;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onSignalStrengthsChanged(SignalStrength signalStrength) {
        super.onSignalStrengthsChanged(signalStrength);
        mSignalStrength = signalStrength.getGsmSignalStrength();
        mSiggnalCDM = signalStrength.getCdmaDbm();

        String gsmSignalStrength=null;
        String ssignal = signalStrength.toString();
        String[] parts = ssignal.split(" ");
        int currentStrength = signalStrength.getGsmSignalStrength();
        if (currentStrength <= 0) {
            gsmSignalStrength= String
                    .valueOf(Integer.parseInt(parts[3]));
        } else {
            gsmSignalStrength= String.valueOf(-113 + 2 * signalStrength.getGsmSignalStrength());
        }

        mSignalStrength = Integer.parseInt(parts[13]);
        Log.d(getClass().getCanonicalName(), "------ gsm signal --> " + mSignalStrength);

        if (mSignalStrength > 30) {
            Log.d(getClass().getCanonicalName(), "Signal GSM : Good");


        } else if (mSignalStrength > 20 && mSignalStrength < 30) {
            Log.d(getClass().getCanonicalName(), "Signal GSM : Avarage");


        } else if (mSignalStrength < 20 && mSignalStrength > 3) {
            Log.d(getClass().getCanonicalName(), "Signal GSM : Weak");


        } else if (mSignalStrength < 3) {
            Log.d(getClass().getCanonicalName(), "Signal GSM : Very weak");


        }
    }

    public  int GetCuretSingleStrngth(){
        return  mSignalStrength;
    }
}