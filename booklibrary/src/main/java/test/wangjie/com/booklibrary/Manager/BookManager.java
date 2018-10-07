package test.wangjie.com.booklibrary.Manager;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.List;

import test.wangjie.com.booklibrary.Book;
import test.wangjie.com.booklibrary.IBookManager;
import test.wangjie.com.booklibrary.IOnNewBookArrivedListener;
import test.wangjie.com.booklibrary.utils.ServiceName;

public class BookManager extends BaseManager {


    public IBookManager mBookService;

    public BookManager(Context context) {
        super(context);
        IBinder a = queryBinder(ServiceName.book_service);
        this.mBookService = IBookManager.Stub.asInterface(a);
    }

    public List<Book> getBookList() {
        try {
            return mBookService.getBookList();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addBook(Book book) {
        try {
            mBookService.addBook(book);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void registerListener(IOnNewBookArrivedListener listener) {
        try {
            mBookService.registerListener(listener);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void unregisterListener(IOnNewBookArrivedListener listener) {
        try {
            mBookService.unregisterListener(listener);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
