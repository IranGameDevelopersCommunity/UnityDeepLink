package com.irancommunity.unitydeeplink;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.unity3d.player.UnityPlayer;

public class ZarrinpalDeepLinkActivity extends AppCompatActivity {

    private final String UNITY_GAME_OBJECT = "ZarrinpalStoreHandler";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Returning from payment gateway
        String action = getIntent().getAction();
        Log.d("Zarinpal","Action : "+action);
        if(action!=null && action.equals("android.intent.action.VIEW"))
        {
            Uri data = getIntent().getData();
            if(data==null)
            {
                Log.d("Zarinpal","zarinpal purchase returned null");
            }
            else
            {
                Log.d("Zarinpal","zarinpal purchase returned : "+data.toString());
                String status = data.getQueryParameter("Status");
                if(status!=null && status.equals("OK"))
                {
                    Log.d("Zarinpal","Status OK trying to verify purchase...");
                    String authority = data.getQueryParameter("Authority");
                    UnityPlayer.UnitySendMessage(UNITY_GAME_OBJECT,"PurchaseSuccess",authority);
                    finish();//Finish purchase flow and return o unity
                }
                else
                {
                    Log.d("Zarinpal","purchase failed : "+status);
                    UnityPlayer.UnitySendMessage(UNITY_GAME_OBJECT,"PurchaseFailed","failed");
                    this.finish();
                }
            }
        }
    }
}