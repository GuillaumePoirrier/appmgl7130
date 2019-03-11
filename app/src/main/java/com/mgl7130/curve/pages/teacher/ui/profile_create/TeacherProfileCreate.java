package com.mgl7130.curve.pages.teacher.ui.profile_create;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mgl7130.curve.R;
import com.mgl7130.curve.models.User;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import static com.mgl7130.curve.pages.teacher.ui.classes.list.TeacherClassRecyclerFragment.LIMIT;

public class TeacherProfileCreate extends Fragment {

    public static final String TAG = "TeacherProfileActivity";
    static final int REQUEST_IMAGE_CAPTURE = 111;
    EditText teacherBirthDate;
    EditText teacherFirstName;
    EditText teacherFamilyName;
    Button button;
    CardView profilePictureCardView;
    ImageView imageView;
    private Calendar calendar;
    private int Year, Month, Day;
    private DatePickerDialog datePickerDialog;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private Query mQuery;

    public static Fragment newInstance() {
        return new TeacherProfileCreate();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teacher_profile_form, container, false);

        imageView = (ImageView) view.findViewById(R.id.teacher_profile_picture);
        teacherFirstName = (EditText) view.findViewById(R.id.teacher_first_name);
        teacherFamilyName = (EditText) view.findViewById(R.id.teacher_family_name);
        teacherBirthDate = (EditText) view.findViewById(R.id.teacher_birth_date);
        profilePictureCardView = (CardView) view.findViewById(R.id.profile_picture_cardView);
        button = (Button) view.findViewById(R.id.button_save_teacher);



        profilePictureCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }

            }
        });


        //init Calendar
        calendar = Calendar.getInstance();

        //Get Firebase auth instance
        mAuth = FirebaseAuth.getInstance();

        //Get Firebase database
        db = FirebaseFirestore.getInstance();

        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);


        teacherBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        String dateString = new SimpleDateFormat("dd/MM/yyyy").format(new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime());
                        teacherBirthDate.setText(dateString);
                    }
                };
                datePickerDialog = DatePickerDialog.newInstance(onDateSetListener, Year, Month, Day);
                datePickerDialog.setAccentColor(getResources().getColor(R.color.color_blue));
                datePickerDialog.setTitle(getString(R.string.set_class_date));
                datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verifyForm(teacherFirstName, teacherFamilyName, teacherBirthDate)) {
                    try {
                        createDbTeacher(teacherFirstName, teacherFamilyName, teacherBirthDate);
                    } catch (Exception e) {
                        System.out.print(e);
                    }
                }
            }
        });
        fillProfile();

        return view;

    }

    private void fillProfile() {
        DocumentReference teacherData  = db
                        .collection("users")
                        .document( mAuth.getCurrentUser()
                                .getUid());

        teacherData.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                teacherFirstName.setText(user.getFirstName());
                teacherFamilyName.setText(user.getLastName());
                try {
                    teacherBirthDate.setText((CharSequence) new SimpleDateFormat("dd/MM/yyyy", Locale.CANADA_FRENCH).parse(user.getBirthDate().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void createDbTeacher(EditText teacherFirstName, EditText teacherFamilyName, EditText teacherBirthDate) throws ParseException {
        //generate timestamp from time string
        Timestamp birthDate = new Timestamp(new SimpleDateFormat("dd/MM/yyyy", Locale.CANADA_FRENCH).parse(teacherBirthDate.getText().toString()));
        User user = new User(teacherFirstName.getText().toString(), teacherFamilyName.getText().toString(), birthDate);
        db.collection("users").document(mAuth.getCurrentUser().getUid())
                .set(user, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println(e);
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bitmap profilePicture = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(cropPicture(profilePicture));
        }
    }

    public Bitmap cropPicture(Bitmap picture) {
        Bitmap dstBmp;
        if (picture.getWidth() >= picture.getHeight()) {
            dstBmp = Bitmap.createBitmap(
                    picture,
                    picture.getWidth() / 2 - picture.getHeight() / 2,
                    0,
                    picture.getHeight(),
                    picture.getHeight()
            );
        } else {
            dstBmp = Bitmap.createBitmap(
                    picture,
                    0,
                    picture.getHeight() / 2 - picture.getWidth() / 2,
                    picture.getWidth(),
                    picture.getWidth()
            );
        }
        saveCroppedPictureInFirebase(dstBmp);
        return dstBmp;
    }

    private void saveCroppedPictureInFirebase(Bitmap picture) {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference imagesRef = storageRef.child("curve/" + mAuth.getCurrentUser().getUid() + ".jpg");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        picture.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // TODO
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // TODO
            }
        });
    }



    public boolean verifyForm(EditText firstname, EditText familyName, EditText dayOfBirth) {
        if (firstname.getText().toString().equals("")) {
            Toast.makeText(getActivity(), getString(R.string.teacher_no_firstname), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (familyName.getText().toString().equals("")) {
            Toast.makeText(getActivity(), getString(R.string.teacher_no_family_name), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (dayOfBirth.getText().toString().equals("")) {
            Toast.makeText(getActivity(), getString(R.string.teacher_no_date_birth), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
