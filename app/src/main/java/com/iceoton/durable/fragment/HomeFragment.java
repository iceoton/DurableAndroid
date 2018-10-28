package com.iceoton.durable.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iceoton.durable.R;

/**
 * Fragment หน้าแรกของแอปพลิเคชัน หน้านี้ไม่ได้มีการสร้างฟังก์ชันการใช้งานใด ๆ ไว้
 * เป็นหน้าเปล่า ๆ สามารถมีฟังก์ชันอื่น ๆ เช่นการค้นหาครุภัณฑ์ด้วย QR code หรือ Bar code ได้
 */
public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        setupView(rootView);

        return rootView;
    }

    /**
     * ทำการสร้างตัวแปรที่เชื่อมต่อกับ view ต่างๆ และตั้งค่าว่าเมื่อกดที่ view ต่างๆ จะเกิดอะไรขึ้น
     * @param rootView view หลักของหน้านี้
     */
    private void setupView(View rootView){
    }
}
