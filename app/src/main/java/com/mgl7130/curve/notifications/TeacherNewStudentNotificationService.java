package com.mgl7130.curve.notifications;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.mgl7130.curve.R;
import com.mgl7130.curve.models.Cours;
import com.mgl7130.curve.models.Student;
import com.mgl7130.curve.util.CoursUtil;
import com.mgl7130.curve.util.StudentUtil;

public class TeacherNewStudentNotificationService extends Service {

    public static final String CHANNEL_ID = "Chanel_Teacher_notification";
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mDb = FirebaseFirestore.getInstance();
    private NotificationCompat.Builder mBuilder;
    private int notificationCounter = 0;

    @Override
    public void onCreate() {
        createNotificationChannel();

        mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.curve_logo)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        initNewStudentListener();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("Service stoped !!");
    }

    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("Service started !!");
        return null;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void initNewStudentListener() {
        if (mAuth.getCurrentUser() != null) {
            mDb.collection("classes").whereEqualTo("teacher_id", mAuth.getCurrentUser().getUid())
                    .whereEqualTo("hasStudent", true)
                    .orderBy("date", Query.Direction.ASCENDING).limit(50)
                    .addSnapshotListener((queryDocumentSnapshots, e) -> {
                        if (queryDocumentSnapshots.getDocumentChanges().size() == 1 && queryDocumentSnapshots.getDocumentChanges().get(0).getType().equals(DocumentChange.Type.ADDED)) {
                            getStudent(queryDocumentSnapshots.getDocumentChanges().get(0).getDocument().toObject(Cours.class).withId(queryDocumentSnapshots.getDocumentChanges().get(0).getDocument().getId()));
                        }
                    });
        }
    }

    private void getStudent(Cours cours) {
        if (cours.hasStudent) {
            FirebaseFirestore.getInstance().collection("users").document(cours.getStudent_id())
                    .addSnapshotListener((snapshot, e) -> {
                        if (e != null) {
                            Log.w("TeacherStudentAdapter", e);
                            return;
                        }
                        createNotification(cours, snapshot.toObject(Student.class).withId(snapshot.getId()));
                    });
        }
    }

    private void createNotification(Cours cours, Student student) {
        mBuilder.setContentTitle(this.getResources().getString(R.string.new_student))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(StudentUtil.getFullname(student)
                        + " s'est inscrit a votre cours de " + CoursUtil.getSubject(cours.subject)
                        + " niveau " + CoursUtil.getLevel(cours.level)
                        + " du " + CoursUtil.getDateString(cours.date)
                        + " de " + CoursUtil.getTime(cours.startDate)
                        + " a " + CoursUtil.getTime(cours.endDate)));

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notificationCounter, mBuilder.build());
        notificationCounter++;
    }

}
