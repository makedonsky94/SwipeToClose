# SwipeToClose
SwipeToClose is the library which using for create an Activity with swipe-to-close functionality.

# How to use?
You should add dependency:
<pre>
dependencies {
	...
	compile 'com.makedonsky.widget:swipe-to-close:0.3.9’
}
</pre>

Also you need to add items to style of Activity:

```xml
<style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
	<item name="android:windowIsTranslucent">true</item>
	<item name="android:windowBackground">@android:color/transparent</item>
</style>
```

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

## Advanced usage

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
			.withShadowAlpha(0.8f, 0.2f)
			.withDirection(SwipeLayout.DIRECTION_RIGHT)
			.withSensitivity(0.2f)
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
