package test.wangjie.com.librarymanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import test.wangjie.com.booklibrary.Book;
import test.wangjie.com.booklibrary.Manager.BookManager;
import test.wangjie.com.booklibrary.config.BkSDK;

public class MainActivity extends AppCompatActivity {

    private Button addBookBtn;
    private EditText editText;
    private BookManager mBookManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BkSDK.getInstans(getApplicationContext()).addListener(new BkSDK.SDKInitListener() {
            @Override
            public void onConnectSuccess() {
                mBookManager = BkSDK.getInstans(getApplicationContext()).getBookManager();
            }
        });
        addBookBtn = findViewById(R.id.add_book);
        editText = findViewById(R.id.book_name_edit);
        addBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = editText.getText().toString();
                if (s != null && !s.equals("")) {
                    //todo 添加书籍
                    if (mBookManager != null) {
                        int i = mBookManager.getBookList().size();
                        Book book = new Book(i + 1, s);
                        mBookManager.addBook(book);
                    }
                }
            }
        });
    }
}
