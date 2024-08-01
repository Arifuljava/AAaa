package com.example.aaaa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.aaaa.timer.MyBroadcastReceiver;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DateActivity extends AppCompatActivity {
    Button dddd;
    private List<String> productnamelist;
    private List<String> detailslist;
    ImageView realimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
        dddd=findViewById(R.id.dddd);
        realimage=findViewById(R.id.realimage);
        productsnamelist();
        productdetaiilsadd();
        storedimage();
    }

    private void productdetaiilsadd() {
        detailslist.add("The Hydro Flask Standard Mouth Water Bottle is a sleek and compact hydration solution with a capacity of 21 oz (621 ml). Crafted from durable stainless steel, it features a vibrant Pacific Blue color and measures 9.4 inches in height with a diameter of 2.87 inches. The bottle is equipped with a Flex Cap that includes a convenient carrying handle, making it easy to transport. Designed with double-walled vacuum insulation, it effectively keeps beverages cold for up to 24 hours and hot for up to 12 hours. The Hydro Flask is both leak-proof and dishwasher safe, ensuring hassle-free maintenance. Weighing just 11.3 oz (320g), it is lightweight and portable, perfect for daily use or outdoor adventures. Priced at $34.95, it has garnered an impressive customer rating of 4.8 out of 5 stars, praised for its temperature retention and stylish design.");
        detailslist.add("The Canon EOS R6 is a versatile full-frame mirrorless camera designed for both professional photographers and enthusiasts. Featuring a 20.1 MP CMOS sensor and the powerful DIGIC X processor, the R6 delivers stunning image quality with remarkable detail and dynamic range. Its Dual Pixel CMOS AF II system offers lightning-fast and accurate autofocus, with 1,053 AF points covering nearly 100% of the frame. The camera excels in low light conditions, thanks to its ISO range of 100-102,400 (expandable to 204,800), and includes 5-axis in-body image stabilization, providing up to 8 stops of shake correction.\n" +
                "\n" +
                "The EOS R6 supports 4K video recording at up to 60fps and Full HD at 120fps, making it ideal for high-quality video production. It features a fully articulating 3.0-inch touchscreen LCD, which is perfect for vlogging, selfies, and composing shots from various angles. The electronic viewfinder (EVF) boasts 3.69 million dots, offering a bright and detailed view of your scene.");
        detailslist.add("The \"ProState Edge\" is a specialized dietary supplement designed to support prostate health and overall male wellness. Formulated with a blend of natural ingredients, this supplement aims to reduce common issues associated with an aging prostate, such as frequent urination, discomfort, and potential hormonal imbalances. Key ingredients typically include saw palmetto, beta-sitosterol, and pygeum, all known for their beneficial effects on prostate health. Additionally, the formula may contain antioxidants and anti-inflammatory agents to promote urinary function and enhance overall vitality.\n" +
                "\n" +
                "\"ProState Edge\" is often marketed towards men who are looking to maintain their prostate health as they age, providing them with a proactive approach to avoid more severe health concerns. It's recommended to be taken daily, with results generally noticeable after consistent use over several weeks. As with any supplement, it's important for users to consult with a healthcare professional before starting \"ProState Edge,\" especially if they have pre-existing conditions or are taking other medications.\n" +
                "\n" +
                "The supplement is typically available in capsule form, making it easy to incorporate into a daily routine. Many users appreciate its natural composition, which reduces the risk of side effects compared to synthetic medications. \"ProState Edge\" is widely available through online retailers and health stores, often praised in customer reviews for its effectiveness in improving prostate health and enhancing overall well-being.");
        detailslist.add("The \"ProState Edge\" is a specialized dietary supplement designed to support prostate health and overall male wellness. Formulated with a blend of natural ingredients, this supplement aims to reduce common issues associated with an aging prostate, such as frequent urination, discomfort, and potential hormonal imbalances. Key ingredients typically include saw palmetto, beta-sitosterol, and pygeum, all known for their beneficial effects on prostate health. Additionally, the formula may contain antioxidants and anti-inflammatory agents to promote urinary function and enhance overall vitality.\n" +
                "\n" +
                "\"ProState Edge\" is often marketed towards men who are looking to maintain their prostate health as they age, providing them with a proactive approach to avoid more severe health concerns. It's recommended to be taken daily, with results generally noticeable after consistent use over several weeks. As with any supplement, it's important for users to consult with a healthcare professional before starting \"ProState Edge,\" especially if they have pre-existing conditions or are taking other medications.\n" +
                "\n" +
                "The supplement is typically available in capsule form, making it easy to incorporate into a daily routine. Many users appreciate its natural composition, which reduces the risk of side effects compared to synthetic medications. \"ProState Edge\" is widely available through online retailers and health stores, often praised in customer reviews for its effectiveness in improving prostate health and enhancing overall well-being.");
        detailslist.add("The Hydro Flask Standard Mouth Water Bottle is a sleek and compact hydration solution with a capacity of 21 oz (621 ml). Crafted from durable stainless steel, it features a vibrant Pacific Blue color and measures 9.4 inches in height with a diameter of 2.87 inches. The bottle is equipped with a Flex Cap that includes a convenient carrying handle, making it easy to transport. Designed with double-walled vacuum insulation, it effectively keeps beverages cold for up to 24 hours and hot for up to 12 hours. The Hydro Flask is both leak-proof and dishwasher safe, ensuring hassle-free maintenance. Weighing just 11.3 oz (320g), it is lightweight and portable, perfect for daily use or outdoor adventures. Priced at $34.95, it has garnered an impressive customer rating of 4.8 out of 5 stars, praised for its temperature retention and stylish design.");
        detailslist.add("The Breville Juice Fountain Plus is a powerful and efficient juicer designed for health enthusiasts who want to enjoy fresh, homemade juice with ease. Equipped with a 850-watt motor, this juicer can handle both soft fruits and hard vegetables, delivering a glass of juice in just seconds. It features a dual-speed control, allowing you to switch between high speed (12,000 RPM) for denser fruits and vegetables like apples and beets, and low speed (6,500 RPM) for leafy greens and soft fruits like oranges and spinach.\n" +
                "\n" +
                "The wide 3-inch feed chute reduces the need for pre-cutting, making the juicing process quicker and more convenient. The stainless steel cutting disc is surrounded by an Italian-made micro mesh filter, which extracts up to 30% more juice and 40% more vitamins and minerals compared to other juicers. The Breville Juice Fountain Plus also includes a large capacity pulp container, allowing you to juice continuously without frequent interruptions to empty the pulp.\n" +
                "\n" +
                "This juicer is easy to assemble, use, and clean, with dishwasher-safe parts that simplify the cleanup process. The sleek design and compact size make it a great fit for any kitchen countertop. It also comes with a juice jug and a froth separator, ensuring that your juice is smooth and ready to drink right away. Priced affordably for its quality and performance, the Breville Juice Fountain Plus is an excellent choice for anyone looking to enhance their diet with fresh, nutrient-rich juices at home." +
                "");

        detailslist.add("The CeraVe Hydrating Face Mask is a deeply nourishing skincare product designed to hydrate and restore the skin's natural barrier. Formulated with key ingredients like hyaluronic acid, ceramides, and essential lipids, this face mask provides long-lasting moisture and helps to lock in hydration, leaving the skin feeling soft, smooth, and rejuvenated. It's particularly beneficial for those with dry, sensitive, or dehydrated skin, as it delivers a gentle yet effective boost of moisture without causing irritation.\n" +
                "\n" +
                "The mask is designed to be used either as a weekly treatment or as needed, providing an instant refresh for tired and dull-looking skin. Its non-comedogenic and fragrance-free formula ensures that it won't clog pores or cause breakouts, making it suitable for all skin types, including acne-prone skin. The CeraVe Hydrating Face Mask is easy to apply, with a rich, creamy texture that spreads smoothly over the face. After leaving it on for about 10-15 minutes, it can be rinsed off to reveal plump, hydrated skin, or it can be left on overnight for an intensive treatment.\n" +
                "\n" +
                "This face mask is part of CeraVe's broader skincare line, which is developed in collaboration with dermatologists and focuses on restoring the skin's natural protective barrier. Users appreciate its ability to provide immediate relief to dry skin and its role in maintaining a healthy, glowing complexion. The CeraVe Hydrating Face Mask is available in both individual packs and multi-use tubes, offering flexibility for at-home spa treatments or on-the-go skincare.");

detailslist.add("The Wallmate is an innovative and versatile wall storage solution designed to help you organize and maximize space in your home or office. Whether you're looking to declutter a room, display decorative items, or keep essential items within easy reach, the Wallmate offers a sleek and practical way to achieve a neat and organized environment.\n" +
        "\n" +
        "Constructed from durable, high-quality materials, the Wallmate is designed to support various items, from books and picture frames to kitchen utensils and office supplies. It typically features a modular design, allowing you to customize the layout to fit your specific needs. Some models come with adjustable shelves, hooks, and compartments, providing flexibility in how you arrange your items.\n" +
        "\n" +
        "The Wallmate is easy to install, usually requiring just a few screws and minimal effort. Its design is minimalist and modern, blending seamlessly with various interior styles. Available in multiple finishes, colors, and sizes, it can complement any room decor, whether in the living room, kitchen, bathroom, or workspace.\n" +
        "\n" +
        "One of the standout features of the Wallmate is its space-saving capability. By utilizing vertical wall space, it helps free up floor space and keeps your environment tidy. The Wallmate is particularly popular in small apartments, studios, and offices where space is at a premium.\n" +
        "\n" +
        "In addition to its practicality, the Wallmate is also aesthetically pleasing, making it not just a storage solution but also a decorative element. Whether you're organizing your home, setting up a workspace, or adding a touch of style to your walls, the Wallmate is a reliable and attractive choice for anyone looking to enhance their living or working space.");
        productnamelist.add("The Breville One-Touch Tea Maker is a sophisticated and user-friendly appliance designed for tea enthusiasts who appreciate precision and convenience in brewing their favorite teas. This tea maker takes the guesswork out of steeping, offering a perfect brew every time with minimal effort. It features a fully automated system that controls the water temperature and steeping time, ensuring that each type of tea—whether black, green, white, or oolong—is brewed at its ideal temperature and duration.\n" +
                "\n" +
                "At the heart of the Breville One-Touch Tea Maker is its programmable settings that allow you to select the tea type and desired strength (mild, medium, or strong). The tea basket, made of stainless steel, automatically lowers into the water when the optimal temperature is reached and then raises after the perfect steeping time, preventing over-extraction and bitterness.\n" +
                "\n" +
                "The tea maker includes a large 1.5-liter glass jug, which is durable and heat-resistant, allowing you to brew multiple cups at once. Its elegant design with a clear glass body and brushed stainless steel accents makes it not only functional but also a stylish addition to any kitchen. The keep-warm function maintains your tea at the ideal drinking temperature for up to an hour, so you can enjoy your tea at your own pace.\n" +
                "\n" +
                "For added convenience, the Breville One-Touch Tea Maker also has a removable tea basket and jug for easy cleaning. It can double as a hot water kettle, providing versatility for making other hot beverages or for culinary uses. Whether you're a casual tea drinker or a connoisseur, the Breville One-Touch Tea Maker delivers a perfect cup with just the press of a button, making it a must-have appliance for any tea lover.");
    }

    private void productsnamelist() {
        productnamelist.add("Water Bottle");
        productnamelist.add("Camera");
        productnamelist.add("ProState Edge");
        productnamelist.add("ProState Edge");
        productnamelist.add("Water Bottle");
        productnamelist.add("Juicer");
        productnamelist.add("Face Mask");
        productnamelist.add("Wallmate");
        productnamelist.add("Tea Maker");


    }

    private void storedimage() {

        Random random = new Random();
        int randomIndex = random.nextInt(9);
        if(randomIndex==0)
        {
            realimage.setImageResource(R.drawable.images);
            String name = productnamelist.get(0);
            String details  =  detailslist.get(0);
        }
        else if(randomIndex==1)
        {
            realimage.setImageResource(R.drawable.images2);
            String name = productnamelist.get(1);
            String details  =  detailslist.get(1);
        }
        else if(randomIndex==2)
        {
            realimage.setImageResource(R.drawable.images3);
            String name = productnamelist.get(2);
            String details  =  detailslist.get(2);
        }
        else if(randomIndex==3)
        {
            realimage.setImageResource(R.drawable.images4);
            String name = productnamelist.get(3);
            String details  =  detailslist.get(3);
        }
        else if(randomIndex==4)
        {
            realimage.setImageResource(R.drawable.images5);
            String name = productnamelist.get(4);
            String details  =  detailslist.get(4);
        }else if(randomIndex==5)
        {
            realimage.setImageResource(R.drawable.images6);
            String name = productnamelist.get(5);
            String details  =  detailslist.get(5);
        }
        else if(randomIndex==6)
        {
            realimage.setImageResource(R.drawable.images7);
            String name = productnamelist.get(6);
            String details  =  detailslist.get(6);
        }else if(randomIndex==7)
        {
            realimage.setImageResource(R.drawable.images8);
            String name = productnamelist.get(7);
            String details  =  detailslist.get(7);
        }
        else if(randomIndex==8)
        {
            realimage.setImageResource(R.drawable.images9);
            String name = productnamelist.get(8);
            String details  =  detailslist.get(8);
        }
        else {
            realimage.setImageResource(R.drawable.images);
            String name = productnamelist.get(0);
            String details  =  detailslist.get(0);
        }







    }

    public void enablebluetooth(View view) {
        Toast.makeText(this, "HHHHHHHHHHH", Toast.LENGTH_SHORT).show();
        fetchCurrentDateTime();
    }
    private CountDownTimer countDownTimer;
    private SharedPreferences prefs;
    private long endTime;
    private static final long DURATION = 24 * 60 * 60 * 1000;
    private void fetchCurrentDateTime() {
        Log.e("DateTime", "Current Date and Time: " );
        prefs = getSharedPreferences("TimerPrefs", MODE_PRIVATE);

        // Start or resume the timer
        startOrResumeTimer();
    }
    private void startOrResumeTimer() {
        long currentTime = System.currentTimeMillis();
        endTime = prefs.getLong("endTime", currentTime + DURATION);

        if (endTime > currentTime) {
            long remainingTime = endTime - currentTime;
            startCountdown(remainingTime);
            setAlarm(endTime);
        } else {
            // Timer has already finished
            dddd.setText("Done!");
        }
    }

    private void startCountdown(long millisUntilFinished) {
        countDownTimer = new CountDownTimer(millisUntilFinished, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long hours = (millisUntilFinished / 1000) / 3600;
                long minutes = ((millisUntilFinished / 1000) % 3600) / 60;
                long seconds = (millisUntilFinished / 1000) % 60;
                dddd.setText(hours + ":" + minutes + ":" + seconds);
            }

            @Override
            public void onFinish() {
                dddd.setText("Done!");
            }
        }.start();
    }

    private void setAlarm(long triggerAtMillis) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, MyBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong("endTime", triggerAtMillis);
        editor.apply();
    }
}