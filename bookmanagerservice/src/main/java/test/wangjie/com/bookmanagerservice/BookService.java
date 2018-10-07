package test.wangjie.com.bookmanagerservice;

import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import test.wangjie.com.booklibrary.Book;
import test.wangjie.com.booklibrary.IBookManager;
import test.wangjie.com.booklibrary.IOnNewBookArrivedListener;

public class BookService extends IBookManager.Stub {
    private static final String TAG = "BMS";

    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();
    private RemoteCallbackList<IOnNewBookArrivedListener> mListenerList = new RemoteCallbackList<>();
    private AtomicBoolean mIsServiceDestoryed = new AtomicBoolean(false);

    @Override
    public List<Book> getBookList() throws RemoteException {
        return mBookList;
    }

    @Override
    public void addBook(Book book) throws RemoteException {
        mBookList.add(book);
        if (!mIsServiceDestoryed.get()) {
            onNewBookArrived(book);
        }
    }

    @Override
    public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
        mListenerList.register(listener);
    }

    @Override
    public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
        mListenerList.unregister(listener);
    }

    private void onNewBookArrived(Book book) throws RemoteException {
        mBookList.add(book);
        final int N = mListenerList.beginBroadcast();
        Log.d(TAG, "onNewBookArrived, notify listener : " + N);
        for (int i = 0; i < N; i++) {
            IOnNewBookArrivedListener listener = mListenerList.getBroadcastItem(i);
            Log.d(TAG, "onNewBookArrived, notify listener : " + listener);
            if (listener != null) {
                listener.onNewBookArrived(book);
            }

        }
        mListenerList.finishBroadcast();
    }
}
