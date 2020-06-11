package com.bilgeadam.mobilefoodapp.utililty;


import android.os.Handler;

import androidx.viewpager.widget.ViewPager;

import com.bilgeadam.mobilefoodapp.adapter.ImagePagerAdapter;

public final class AppUtils {

    public static void automaticSlide(ViewPager viewPager, ImagePagerAdapter imagePagerAdapter) {
        final int DELAY = 5000;
        final Handler handler = new Handler();

        Runnable runnable = new Runnable() {
            int page = 0;
            boolean first = true;

            @Override
            public void run() {
                if (!first) {
                    page = viewPager.getCurrentItem();
                    if ((imagePagerAdapter.getCount() - 1) == page)
                        page = 0;
                    else
                        page++;

                    viewPager.setCurrentItem(page, true);
                } else
                    first = false;

                handler.postDelayed(this, DELAY);
            }
        };

        runnable.run();
    }
}
