package com.mgl7130.curve.pages.student.ui.profile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentProfileCreate extends Fragment {

    public static final String TAG = "StudentProfileActivity";
    static final int REQUEST_IMAGE_CAPTURE = 111;

    @BindView(R.id.edit_profile_student_button)
    FloatingActionButton editProfileButton;

    @BindView(R.id.student_profile_picture)
    ImageView imageView;

    @BindView(R.id.student_first_name)
    EditText studentFirstName;

    @BindView(R.id.student_family_name)
    EditText studentFamilyName;

    @BindView(R.id.student_birth_date)
    EditText studentBirthDate;

    @BindView(R.id.student_description)
    EditText studentDescription;

    @BindView(R.id.profile_picture_cardView)
    CardView profilePictureCardView;

    @BindView(R.id.button_save_student)
    Button saveButton;

    private int Year, Month, Day;
    private DatePickerDialog datePickerDialog;
    private String userConnectionId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private FirebaseFirestore db;

    public static Fragment newInstance() {
        return new StudentProfileCreate();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student_profile_form, container, false);
        ButterKnife.bind(this, view);


        profilePictureCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }

            }
        });
        //Get Firebase database
        db = FirebaseFirestore.getInstance();
        Calendar calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);


        studentBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        String dateString = new SimpleDateFormat("dd/MM/yyyy").format(new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime());
                        studentBirthDate.setText(dateString);
                    }
                };
                datePickerDialog = DatePickerDialog.newInstance(onDateSetListener, Year, Month, Day);
                datePickerDialog.setAccentColor(getResources().getColor(R.color.color_blue));
                datePickerDialog.setTitle(getString(R.string.set_class_date));
                datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verifyForm(studentDescription, studentFirstName, studentFamilyName, studentBirthDate)) {
                    try {
                        createDbStudent(studentDescription, studentFirstName, studentFamilyName, studentBirthDate);
                        setFormProfileEditable(false);
                    } catch (Exception e) {
                        System.out.print(e);
                    }
                }
            }
        });

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFormProfileEditable(true);
            }
        });
        setFormProfileEditable(false);
        fillProfile();
        return view;
    }

    private void setFormProfileEditable(boolean editable) {
        studentFirstName.setEnabled(editable);
        studentFamilyName.setEnabled(editable);
        studentBirthDate.setEnabled(editable);
        studentDescription.setEnabled(editable);
        profilePictureCardView.setEnabled(editable);
        if (editable) {
            saveButton.setVisibility(View.VISIBLE);
            editProfileButton.setVisibility(View.GONE);
            studentFirstName.requestFocus();
        } else {
            saveButton.setVisibility(View.GONE);
            editProfileButton.setVisibility(View.VISIBLE);
        }
    }

    private void fillProfile() {
        DocumentReference studentData = db
                .collection("users")
                .document(userConnectionId);

        studentData.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                if (user.hasFirstName()) studentFirstName.setText(user.getFirstName());
                if (user.hasLastName()) studentFamilyName.setText(user.getLastName());
                if (user.hasDescription()) studentDescription.setText(user.getDescription());
                if (user.hasBirthDate())
                    studentBirthDate.setText((new SimpleDateFormat("dd/MM/yyyy", Locale.CANADA_FRENCH).format(user.getBirthDate().toDate())));
            }
        });


        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference pathReference = storageRef.child("curve/" + userConnectionId + ".jpg");
        pathReference.getBytes(100000).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/island.jpg" is returns, use this as needed
                Bitmap profilePicture = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageView.setImageBitmap(cropPicture(profilePicture));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getActivity(), getString(R.string.student_unable_get_profile_picture), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createDbStudent(EditText studentDescription, EditText studentFirstName, EditText studentFamilyName, EditText studentBirthDate) throws ParseException {
        //generate timestamp from time string
        Timestamp birthDate = new Timestamp(new SimpleDateFormat("dd/MM/yyyy", Locale.CANADA_FRENCH).parse(studentBirthDate.getText().toString()));
        User user = new User(studentFirstName.getText().toString(), studentFamilyName.getText().toString(), birthDate, studentDescription.getText().toString());
        db.collection("users").document(userConnectionId)
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
        StorageReference imagesRef = storageRef.child("curve/" + userConnectionId + ".jpg");
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

    public boolean verifyForm(EditText description, EditText firstname, EditText familyName, EditText dayOfBirth) {
        if (firstname.getText().toString().equals("")) {
            Toast.makeText(getActivity(), getString(R.string.student_no_firstname), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (familyName.getText().toString().equals("")) {
            Toast.makeText(getActivity(), getString(R.string.student_no_family_name), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (description.getText().toString().equals("")) {
            Toast.makeText(getActivity(), getString(R.string.student_no_description), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (dayOfBirth.getText().toString().equals("")) {
            Toast.makeText(getActivity(), getString(R.string.student_no_date_birth), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}