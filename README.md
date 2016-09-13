# SwipeToClose
SwipeToClose is the library which using for create an Activity with swipe-to-close functionality.

# How to use?
You should add maven repository to build.gradle:
<pre>
repositories {
	jcenter()
	maven {
		url 'https://dl.bintray.com/nevyantsev/widget/'
	}
}
</pre>
Also you should add dependency:
<pre>
dependencies {
	...
	compile 'com.makedonsky.widget:swipe-to-close:0.3'
}
</pre>

# Show me the code

```java
public class ClosingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.closing);

        SwipeToClose
                .with(this)
                .bind();
    }
}
```

##Advanced usage

```java
public class ClosingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.closing);

        SwipeToClose
                .with(this)
				.withShadow(true)
				.withShadowColor(android.R.color.black)
				.withDirection(SwipeLayout.DIRECTION_RIGHT)
				.withListener(new SwipeLayout.OnCloseListener() {
                    @Override
                    public boolean onCloseStart() {
						//if this method returns true, then onClose will be called
                        return true;
                    }

                    @Override
                    public void onClose() {
						finish();
                    }

                    @Override
                    public void onCancel() {
						Toast.makeText(ClosingActivity.this, "Cancel", Toast.LENGTH_LONG).show();
                    }
                })
                .bind();
    }
}
```
