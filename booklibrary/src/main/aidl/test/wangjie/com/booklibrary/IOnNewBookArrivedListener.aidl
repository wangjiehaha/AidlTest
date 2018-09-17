// IOnNewBookArrivedListener.aidl
package test.wangjie.com.booklibrary;

import test.wangjie.com.booklibrary.Book;

// Declare any non-default types here with import statements

interface IOnNewBookArrivedListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void onNewBookArrived(in Book book);
}
