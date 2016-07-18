//package com.netizen.netizenitltd;
//
//import android.app.Activity;
//import android.content.Context;
//import android.os.Bundle;
//import android.support.annotation.IdRes;
//import android.support.v4.content.ContextCompat;
//import android.widget.Toast;
//
//import com.roughike.bottombar.BottomBar;
//import com.roughike.bottombar.OnMenuTabClickListener;
//
///**
// * Created by touhid on 7/18/2016.
// */
//public class MyBottomBar extends Activity{
//
//
//    public void myMethod(BottomBar mBottomBar, final Context context) {
//
//
//
//        // Disable the left bar on tablets and behave exactly the same on mobile and tablets instead.
//        mBottomBar.noTabletGoodness();
//
//        // Show all titles even when there's more than three tabs.
//        mBottomBar.useFixedMode();
//        mBottomBar.noTopOffset();
//
//
//
//        mBottomBar.setItems(R.menu.bottom_bar_menu);
//
//        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
//
//            @Override
//            public void onMenuTabSelected(@IdRes int menuItemId) {
//                if (menuItemId == R.id.bottomBarItem_rateUs) {
//                    Toast.makeText(context, "rate us", Toast.LENGTH_SHORT).show();
//                }
//
//                if (menuItemId == R.id.bottomBarItem_moreApps) {
//                    Toast.makeText(context, "more apps", Toast.LENGTH_SHORT).show();
//                }
//
//                if (menuItemId == R.id.bottomBarItem_aboutUs) {
//                    Toast.makeText(context, "contact us", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onMenuTabReSelected(@IdRes int menuItemId) {
//                if (menuItemId == R.id.bottomBarItem_rateUs) {
//                    // The user reselected item number one, scroll your content to top.
//                }
//            }
//        });
//
//        // Setting colors for different tabs when there's more than three of them.
//        // You can set colors for tabs in three different ways as shown below.
//        mBottomBar.mapColorForTab(0, ContextCompat.getColor(this, R.color.colorAccent));
//        mBottomBar.mapColorForTab(1, 0xFF5D4037);
//        mBottomBar.mapColorForTab(2, "#7B1FA2");
////        mBottomBar.mapColorForTab(3, "#FF5252");
////        mBottomBar.mapColorForTab(4, "#FF9800");
//        // Use the dark theme.
//        mBottomBar.useDarkTheme();
//        // Set the color for the active tab. Ignored on mobile when there are more than three tabs.
//        mBottomBar.setActiveTabColor("#009688");
//
//        // Use custom text appearance in tab titles.
//        mBottomBar.setTextAppearance(R.style.MyTextAppearance); // make style with the name of MyTextAppearance
//
//        // Use custom typeface that's located at the "/src/main/assets" directory. If using with
//        // custom text appearance, set the text appearance first.
////        mBottomBar.setTypeFace("MyFont.ttf");
//
//
//
//
//
//
//
//
//
//
//
//
//
//    }
//
//
//
//}
