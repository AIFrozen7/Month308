package com.bwei.Lizhuokai.frag;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bwei.Lizhuokai.R;

/**
 * @Auther: 李
 * @Date: 2019/3/8 14:15:07
 * @Description:
 */
public class MineFragment extends Fragment {
    private View view;
    private ImageView mine_img;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.mine_frag_layout, container, false);
        mine_img = view.findViewById(R.id.mine_img);
        mine_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mine_img,"translationY",0f,300f);
                objectAnimator.setDuration(2000);
                objectAnimator.start();
                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(mine_img,"alpha",1f,0.3f);
                objectAnimator2.setDuration(2000);
                objectAnimator2.start();
            }
        });
        return view;
    }
}
