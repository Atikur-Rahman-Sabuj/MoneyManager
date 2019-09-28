package com.tiringbring.moneymanager.Utility;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.tiringbring.moneymanager.R;

public class PlayAnimation {
    public void PlayFadeIn(Context context, View view){
        Animation a = AnimationUtils.loadAnimation(context, R.anim.fadein);
        a.reset();
        view.clearAnimation();
        view.startAnimation(a);
    }

    public void PlayFadeOut(Context context, View view){
        Animation a = AnimationUtils.loadAnimation(context, R.anim.fadeout);
        a.reset();
        view.clearAnimation();
        view.startAnimation(a);
    }
}
