package com.mgl7130.curve;

import android.app.Application;
import android.content.Intent;

import com.mgl7130.curve.notifications.TeacherNewStudentNotificationService;

public class CurveApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        startService(new Intent(this, TeacherNewStudentNotificationService.class));
    }

}
