package com.example.aaaa;

// MyMethodCallHandler.java
/*
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class MyMethodCallHandler implements MethodChannel.MethodCallHandler {
    @Override
    public void onMethodCall(MethodCall call, MethodChannel.Result result) {
        switch (call.method) {
            case "sayHello":
                String name = call.argument("name");
                String greeting = MyJavaClass.sayHello(name);
                result.success(greeting);
                break;
            default:
                result.notImplemented();
        }
    }
}
 */
/*
import android.os.Bundle;
import io.flutter.app.FlutterActivity;
import io.flutter.plugin.common.MethodChannel;

public class MainActivity extends FlutterActivity {
    private static final String CHANNEL = "com.example.app/myChannel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new MethodChannel(getFlutterView(), CHANNEL)
                .setMethodCallHandler(new MyMethodCallHandler());
    }
}
 */