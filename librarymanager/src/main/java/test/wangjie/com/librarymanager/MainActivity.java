package test.wangjie.com.librarymanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import test.wangjie.com.booklibrary.Book;
import test.wangjie.com.booklibrary.Manager.BookManager;

public class MainActivity extends AppCompatActivity {

    private Button addBookBtn;
    private EditText editText;
    private BookManager mBookManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /** 注意是要在子线程中，否则会阻塞，绑定不了服务，具体原因还要分析？？？*/
        new Thread(new Runnable() {
            @Override
            public void run() {
                mBookManager = new BookManager(getApplicationContext());
            }
        }).start();
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
