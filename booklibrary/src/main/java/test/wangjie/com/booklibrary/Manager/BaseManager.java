package test.wangjie.com.booklibrary.Manager;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.concurrent.CountDownLatch;

import test.wangjie.com.booklibrary.IBinderPool;

public class BaseManager {
    private static final String TAG = "BaseManager";

    private Context mContext;
    private CountDownLatch mConnectBinderPoolCountDownLatch;
    private IBinderPool mBinderPool;

    public BaseManager(Context context) {
        mContext = context;
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("test.wangjie.com.bookmanagerservice","test.wangjie.com.bookmanagerservice.MainService"));
        mContext.startService(intent);
        connectBinderPool();
    }

    public  synchronized void connectBinderPool() {
        if (mBinderPool != null)return;
        mConnectBinderPoolCountDownLatch = new CountDownLatch(1);
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("test.wangjie.com.bookmanagerservice","test.wangjie.com.bookmanagerservice.MainService"));
        boolean ret = mContext.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        try {
            mConnectBinderPoolCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "链接服务结果：" + ret);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinderPool = IBinderPool.Stub.asInterface(service);
            try {
                /** binder对象设置死亡代理*/
                mBinderPool.asBinder().linkToDeath(mBinderPoolDeathRecipient,0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            mConnectBinderPoolCountDownLatch.countDown();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    /**
     *
     * Binder连接断裂后需要重新连接
     */
    private IBinder.DeathRecipient mBinderPoolDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.e(TAG, "binder died.");
            mBinderPool.asBinder().unlinkToDeath(mBinderPoolDeathRecipient, 0);
            mBinderPool = null;
            connectBinderPool();
        }
    };

    /**
     * 查询Binder
     * @param serviceName 服务名
     * @return
     */
    public IBinder queryBinder(String serviceName) {
        IBinder binder = null;
        try {
            getService();
            if (mBinderPool != null) {
                binder = mBinderPool.queryBinder(serviceName);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return binder;
    }

    /**
     * 拿到BinderPool
     * @return
     */
    protected IBinderPool getService() {
        if (mBinderPool == null) {
            connectBinderPool();
        }
        return mBinderPool;
    }
}
