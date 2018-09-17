package test.wangjie.com.aidltest.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import test.wangjie.com.aidltest.R;
import test.wangjie.com.aidltest.service.BookManagerService;
import test.wangjie.com.booklibrary.Book;
import test.wangjie.com.booklibrary.IBookManager;

public class LibraryManagerActivity extends AppCompatActivity {

    private Button addBookBtn;
    private EditText editText;
    private IBookManager mBookManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_manager);
        addBookBtn = findViewById(R.id.add_book);
        editText = findViewById(R.id.book_name_edit);
        addBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = editText.getText().toString();
                if (s != null && !s.equals("")) {
                    //todo 添加书籍
                    if (mBookManager != null) {
                        try {
                            int i = mBookManager.getBookList().size();
                            Book book = new Book(i + 1, s);
                            mBookManager.addBook(book);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        Intent intent = new Intent(this, BookManagerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IBookManager bookManager = IBookManager.Stub.asInterface(service);
            mBookManager = bookManager;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}
