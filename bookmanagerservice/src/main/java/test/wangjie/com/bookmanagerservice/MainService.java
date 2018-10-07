package test.wangjie.com.bookmanagerservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import test.wangjie.com.booklibrary.Book;
import test.wangjie.com.booklibrary.IBookManager;
import test.wangjie.com.booklibrary.IOnNewBookArrivedListener;

public class MainService extends Service {
    private IBinder mIBinder;

    @Override
    public void onCreate() {
        super.onCreate();
        mIBinder = new BinderPool();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }
}
