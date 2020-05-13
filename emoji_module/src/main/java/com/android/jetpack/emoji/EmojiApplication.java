package com.android.jetpack.emoji;

import android.app.Application;
import androidx.emoji.bundled.BundledEmojiCompatConfig;
import androidx.emoji.text.EmojiCompat;


public class EmojiApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        FontRequest fontRequest=new FontRequest(
//                "com.example.fontprovider",
//                "com.example",
//                "emoji compat Font Query",0);
//        EmojiCompat.Config config=new FontRequestEmojiCompatConfig(this,fontRequest)
//                .setReplaceAll(true)
//                .setEmojiSpanIndicatorEnabled(true)
//                .setEmojiSpanIndicatorColor(Color.GREEN)
//                .registerInitCallback(new EmojiCompat.InitCallback() {
//                    @Override
//                    public void onInitialized() {
//                        super.onInitialized();
//                    }
//
//                    @Override
//                    public void onFailed(@Nullable Throwable throwable) {
//                        super.onFailed(throwable);
//                    }
//                });

        EmojiCompat.Config config=new BundledEmojiCompatConfig(this);
        EmojiCompat.init(config);
        CharSequence sequence=EmojiCompat.get().process("neutral face \uD83D\uDE10");
    }
}
