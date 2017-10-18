package com.bartoszlipinski.constraint.sample.activity;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.transition.AutoTransition;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.bartoszlipinski.constraint.StaggeredAnimationGroup;
import com.bartoszlipinski.constraint.sample.R;
import com.bartoszlipinski.constraint.sample.databinding.ActivityMainBinding;

/**
 * Created by Bartosz Lipinski
 */
public class MainActivity extends Activity implements View.OnClickListener {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

       StaggeredAnimationGroup group = findViewById(R.id.group);
        binding.showButton.setOnClickListener(this);
        binding.showReversedButton.setOnClickListener(this);
        binding.hideButton.setOnClickListener(this);
        binding.hideReversedButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.showButton:
            case R.id.showReversedButton:
                setShowEnabled(false);
                setHideEnabled(true);
                break;
            case R.id.hideButton:
            case R.id.hideReversedButton:
                setShowEnabled(true);
                setHideEnabled(false);
                break;
        }
        switch (id) {
            case R.id.showButton:
                binding.group.show();
                break;
            case R.id.showReversedButton:
                binding.group.show(true);
                break;
            case R.id.hideButton:
                binding.group.hide();
                break;
            case R.id.hideReversedButton:
                binding.group.hide(true);
                break;
        }
    }

    private void setShowEnabled(boolean enabled) {
        binding.showButton.setEnabled(enabled);
        binding.showReversedButton.setEnabled(enabled);
    }

    private void setHideEnabled(boolean enabled) {
        binding.hideButton.setEnabled(enabled);
        binding.hideReversedButton.setEnabled(enabled);
    }
}
