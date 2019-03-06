package com.mgl7130.curve.helpers;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.mgl7130.curve.models.Cours;

public class FirestoreHelper {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    //Teacher
    //Class
    public void saveClass(Cours cours){
        db.collection("classes").document().set(cours, SetOptions.merge());
    }

    public void updateClass(Cours cours){
        db.collection("classes").document(cours.getId()).set(cours, SetOptions.merge());
    }

    public void getCoursById(String Id){
        db.collection("classes").document(Id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Cours cours = documentSnapshot.toObject(Cours.class);
                cours.setId(documentSnapshot.getId());
            }
        });
    }

    //Student



    public static FirestoreHelper newInstance(){
        return new FirestoreHelper();
    }

}
