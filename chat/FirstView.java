package com.example.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.chat.Learners.SignInLearners;
import com.example.chat.databinding.ActivityFirstViewBinding;
import com.example.chat.databinding.ActivityMainBinding;
import com.example.chat.utilities.Constants;
import com.example.chat.utilities.PreferenceManager;

public class FirstView extends AppCompatActivity {



    private ActivityFirstViewBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceManager = new PreferenceManager(getApplicationContext());
        if(preferenceManager.getBoolean(Constants.KEY_SIGNED_IN)){
            Intent intent = new Intent(getApplicationContext(), TeachersView.class);
            startActivity(intent);
            finish();
        }
        binding = ActivityFirstViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());
        setListeners();
//        loadUserDetails();
//        getToken();

    }

    private void setListeners(){

//            binding.logoutbutton.setOnClickListener(v -> signOut());
        binding.goLearn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), SignInLearners.class)));
        binding.gochat.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), SignInActivity.class)));

    }

//    private void loadUserDetails(){
//        binding.TeacherName.setText(preferenceManager.getString(Constants.KEY_NAME));
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        byte[] bytes = Base64.decode(preferenceManager.getString(Constants.KEY_IMAGE), Base64.DEFAULT);
//        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//        binding.TeacherImage.setImageBitmap(bitmap);
//
//    }
//
//        private void showToast (String message){
//            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
//        }
//        private void  getToken(){
//            FirebaseMessaging.getInstance().getToken().addOnSuccessListener(this::updateToken);
//        }
//        private void updateToken (String token){
//            FirebaseFirestore database = FirebaseFirestore.getInstance();
//            DocumentReference documentReference = database.collection(Constants.KEY_COLLECTION_USERS)
//                    .document(preferenceManager.getString(Constants.KEY_USER_ID));
//            documentReference.update(Constants.KEY_FCM_TOKEN, token)
//                    .addOnSuccessListener(unused -> showToast("Token Updated Successfully"))
//                    .addOnFailureListener(e -> showToast("unable to update token"));
//        }
//            private void signOut(){
//                showToast("Signing out...");
//                FirebaseFirestore database = FirebaseFirestore.getInstance();
//                DocumentReference documentReference = database.collection(Constants.KEY_COLLECTION_USERS).document(
//                        preferenceManager.getString(Constants.KEY_USER_ID)
//                );
//                HashMap <String, Object> updates = new HashMap<>();
//                updates.put(Constants.KEY_FCM_TOKEN, FieldValue.delete());;
//                documentReference.update(updates).addOnSuccessListener(unused -> {
//                    preferenceManager.clear();
//                    startActivity(new Intent(getApplicationContext(), SignInActivity.class));
//                    finish();
//                }).addOnFailureListener(e -> showToast("unable to sign out"));
//            }


}
