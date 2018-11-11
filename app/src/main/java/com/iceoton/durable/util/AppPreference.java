package com.iceoton.durable.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * รวบรวมฟังก์ชันสำหรับจัดการข้อมูลใน Preference ของแอพ
 */
public class AppPreference {
    SharedPreferences sharedPref;

    public AppPreference(Context context) {
        sharedPref = context.getSharedPreferences("AppPref", Context.MODE_PRIVATE);
    }

    /**
     * ทำการบันทึกสถานะการ login ลง preference
     * @param isLoggedIn สถานะที่ต้องการบันทึก
     */
    public void saveLoginStatus(boolean isLoggedIn) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("loginStatus", isLoggedIn);
        editor.apply();
    }

    /**
     * ดึงค่าสถานะการ login จาก preference
     * @return สถานะการ login
     */
    public boolean getLoginStatus() {
        return sharedPref.getBoolean("loginStatus", false);
    }

    /**
     * ทำการบันทึก user id ลง preference
     * @param id ไอดีของผู้ใช้งานที่ต้องการบันทึก
     */
    public void saveUserId(String id) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("user_id", id);
        editor.apply();
    }

    /**
     * ดึงค่า user id จาก preference
     * @return user id
     */
    public String getUserId() {
        return sharedPref.getString("user_id", null);
    }

    /**
     * ทำการบันทึก user name ลง preference
     * @param name user name ของผู้ใช้งานที่ต้องการบันทึก
     */
    public void saveUserName(String name) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("user_name", name);
        editor.apply();
    }

    /**
     * ดึงชื่อผู้ใช้งานที่ทำการบันทึกไว้ จาก preference
     * @return ชื่อผู้ใช้งานที่ทำการบันทึกไว้
     */
    public String getUserName() {
        return sharedPref.getString("user_name", "");
    }

    /**
     * ทำการบันทึกอีเมลของผู้ใช้งาน ลง preference
     * @param email อีเมลของผู้ใช้งานที่ต้องการบันทึก
     */
    public void saveUserEmail(String email) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("user_email", email);
        editor.apply();
    }

    /**
     * ดึงอีเมลของผู้ใช้งานที่ทำการบันทึกไว้ จาก preference
     * @return  อีเมลของผู้ใช้งานที่ทำการบันทึกไว้
     */
    public String getUserEmail() {
        return sharedPref.getString("user_email", "");
    }

    /**
     * ทำการบันทึก url ของรูปภาพผู้ใช้งาน ลง preference
     * @param photoUrl url ของรูปภาพผู้ใช้งาน ที่ต้องการบันทึก
     */
    public void saveUserPhoto(String photoUrl) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("user_photo", photoUrl);
        editor.apply();
    }

    /**
     * ดึงค่า url ของรูปภาพผู้ใช้งาน ที่ทำการบันทึกไว้ จาก prference
     * @return url ของรูปภาพผู้ใช้งาน
     */
    public String getUserPhoto() {
        return sharedPref.getString("user_photo", "");
    }

    /**
     * ทำการบันทึกภาษาของแอพ ลง preference
     * @param language ค่าภาษาที่ต้องการบันทึก
     */
    public void saveAppLanguage(String language) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("appLanguage", language);
        editor.apply();
    }

    /**
     * ดึงค่าภาษาที่บันทึกไว้จาก preference
     * @return ค่าภาษาที่ทำการบันทึกไว้
     */
    public String getAppLanguage() {
        return sharedPref.getString("appLanguage", "en");
    }

}
