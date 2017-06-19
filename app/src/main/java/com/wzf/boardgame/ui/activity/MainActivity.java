package com.wzf.boardgame.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.wzf.boardgame.R;
import com.wzf.boardgame.constant.UrlService;
import com.wzf.boardgame.function.http.ResponseSubscriber;
import com.wzf.boardgame.function.map.BaiDuMapManager;
import com.wzf.boardgame.ui.base.BaseActivity;
import com.wzf.boardgame.utils.AppDeviceInfo;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainActivity extends BaseActivity {

    @Bind(R.id.tv)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        showToast(AppDeviceInfo.getNetworkType());

        BaiDuMapManager.getInstance().getLocationMessage(new BaiDuMapManager.OnLocationMessageGetListener() {
            @Override
            public void onReceiveLocation(final String cityName, double lon, double lat) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showToast(cityName);
                    }
                });
            }
        });
        UrlService.SERVICE.login("123123132132")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ResponseSubscriber<Object>(this, true) {
                    @Override
                    public void onSuccess(Object loginResponseDto) throws Exception {
                        super.onSuccess(loginResponseDto);
                        tv.setText(loginResponseDto.toString());
                    }

                    @Override
                    public void onFailure(int code, String message) throws Exception {
                        super.onFailure(code, message);
                        tv.setText(message);
                    }
                });
    }
}
