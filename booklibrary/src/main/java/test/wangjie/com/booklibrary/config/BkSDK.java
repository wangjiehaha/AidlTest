package test.wangjie.com.booklibrary.config;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import test.wangjie.com.booklibrary.Manager.BookManager;

public class BkSDK {
    private final String TAG = "BkSDK";
    private static BkSDK bkSDK;
    private Context mContext;
    private BookManager mBookManager;
    private boolean hasInit = false;

    public static BkSDK getInstans(Context context) {
        if (bkSDK == null) {
            bkSDK = new BkSDK(context);
        }
        return bkSDK;
    }

    private BkSDK(Context context) {
        this.mContext = context;
        init();
    }

    private void init() {
        Log.i(TAG, "EtSDK init--begin");
        final long stime= SystemClock.elapsedRealtime();
        HandlerThread handlerThread = new HandlerThread("BkSDK");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                mBookManager = new BookManager(mContext);
                long etime=SystemClock.elapsedRealtime();
                Log.i(TAG, "EtSDK init--end;execute time:"+(etime-stime));
                hasInit = true;
                dispatch();
            }
        });
    }

    public BookManager getBookManager() {
        return mBookManager;
    }

    private void dispatch() {
        if (mListeners != null && mListeners.size() > 0) {
            for (SDKInitListener listener : mListeners) {
                listener.onConnectSuccess();
            }
        }
    }

    /**
     * 监听SDK是否初始化完毕
     */
    public interface SDKInitListener {
        void onConnectSuccess();
    }

    private List<SDKInitListener> mListeners=new ArrayList<SDKInitListener>();

    public void addListener(SDKInitListener listener){
        mListeners.add(listener);
        if(hasInit){
            listener.onConnectSuccess();
        }
    }

    public void remmoveListener(SDKInitListener listener){
        mListeners.remove(listener);
    }
}
