package com.iceoton.durable.rest;

/**
 * รวม HTTP Code ต่างๆ ที่ได้จากการติดต่อไปที่ API ผ่าน Internet Network
 */
public class ResultCode {
    /**
     * ติดต่อไปที่ API สำเร็จ
     */
    public final static int OK = 200;
    /**
     * request ที่ส่งไปไม่ถูกต้อง
     */
    public final static int BAD_REQUEST = 400;
    /**
     * มีข้อผิดพลาดเกิดขึ้นที่ผั่ง API server
     */
    public final static int SERVER = 500;
}
