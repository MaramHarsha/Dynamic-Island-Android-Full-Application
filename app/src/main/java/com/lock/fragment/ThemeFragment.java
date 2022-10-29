package com.lock.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.google.android.gms.common.internal.ImagesContract;
import com.dynamic.island.harsha.notification.R;
public class ThemeFragment extends Fragment {

    Context mConxt;

    public static ThemeFragment newInstance(String str, int i) {
        ThemeFragment themeFragment = new ThemeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index", i);
        bundle.putString(ImagesContract.URL, str);
        themeFragment.setArguments(bundle);
        return themeFragment;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_theme, viewGroup, false);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.mConxt = getActivity();

    }

}
