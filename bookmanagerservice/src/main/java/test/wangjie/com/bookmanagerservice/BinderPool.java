package test.wangjie.com.bookmanagerservice;

import android.os.IBinder;
import android.os.RemoteException;

import java.util.HashMap;
import java.util.Map;

import test.wangjie.com.booklibrary.IBinderPool;
import test.wangjie.com.booklibrary.utils.ServiceName;

public class BinderPool extends IBinderPool.Stub {

    private Map<String, IBinder> mServiceMap = new HashMap<>();

    public BinderPool(){
        init();
    }

    private void init() {
        mServiceMap.put(ServiceName.book_service, new BookService());
    }

    @Override
    public IBinder queryBinder(String binderCode) throws RemoteException {
        return mServiceMap.get(binderCode);
    }
}
