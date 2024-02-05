package com.example.chat.Learners;

import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.chat.MainActivity;
import com.example.chat.databinding.ActivityMainBinding;
import com.example.chat.databinding.ActivitySignUpBinding;
import com.example.chat.databinding.ActivitySignUpLearnersBinding;
import com.example.chat.utilities.Constants;
import com.example.chat.utilities.PreferenceManager;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

public class SignUpLearners extends AppCompatActivity {

    private ActivitySignUpLearnersBinding binding;
    private PreferenceManager preferenceManager;
    private String encodedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpLearnersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());
        setListeners();
    }

    private void setListeners() {

//        binding.SingUpButton.setOnClickListener(v-> onBackPressed());
        binding.SingUpButton.setOnClickListener(v ->
        {
            if (isValidSignUpDetails()) {
//                SignUPprocessed();
            }

        });
        binding.profilelayoutSU.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            pickImage.launch(intent);
        });
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

    }

//    private void SignUPprocessed() {
//        loading(true);
//        FirebaseFirestore database = FirebaseFirestore.getInstance();
//        FirebaseFirestore databaseusers = FirebaseFirestore.getInstance();
//        HashMap <String, Object> Learners = new HashMap<>();
//        HashMap <String, Object> users = new HashMap<>();
//
//        Learners.put(Constants.KEY_NAME, binding.enternameSU.getText().toString());
//        Learners.put(Constants.KEY_EMAIL, binding.enteremailSU.getText().toString());
//        Learners.put(Constants.KEY_PASSWORD, binding.enterpassSU.getText().toString());
//        Learners.put(Constants.KEY_IMAGE, encodedImage);
//
//
//
//        database.collection(Constants.KEY_COLLECTION_LEARNERS)
//                .add(Learners)
//
//                .addOnSuccessListener(documentReference -> {
//                    loading(false);
//                    preferenceManager.putBoolean(Constants.KEY_SIGNED_IN, true);
//                    preferenceManager.putString(Constants.KEY_USER_ID, documentReference.getId());
//                    preferenceManager.putString(Constants.KEY_NAME, binding.enternameSU.getText().toString());
//                    preferenceManager.putString(Constants.KEY_IMAGE, encodedImage);
//                })
//                .addOnFailureListener(exception ->{
//                    loading(false);
//                    showToast(exception.getMessage());
//                });
//
//                 users.put(Constants.KEY_NAME, binding.enternameSU.getText().toString());
//                 users.put(Constants.KEY_EMAIL, binding.enteremailSU.getText().toString());
//                 users.put(Constants.KEY_PASSWORD, binding.enterpassSU.getText().toString());
//                    users.put(Constants.KEY_IMAGE, encodedImage);
//
//            databaseusers.collection(Constants.KEY_COLLECTION_USERS)
//
//
//                .add(users)
//                .addOnSuccessListener(documentReference -> {
//                    loading(false);
//                    preferenceManager.putBoolean(Constants.KEY_SIGNED_IN, true);
//                    preferenceManager.putString(Constants.KEY_USER_ID, documentReference.getId());
//                    preferenceManager.putString(Constants.KEY_NAME, binding.enternameSU.getText().toString());
//                    preferenceManager.putString(Constants.KEY_IMAGE, encodedImage);
//                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
//                })
//                .addOnFailureListener(exception ->{
//                    loading(false);
//                    showToast(exception.getMessage());
//                });
//
//    }

//    private String encodeImage(Bitmap bitmap) {
//        int previewWidth = 150;
//        int previewHeight = bitmap.getHeight() * previewWidth / bitmap.getWidth();
//        Bitmap previewBitmap = Bitmap.createScaledBitmap(bitmap, previewWidth, previewHeight, false);
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
//        byte[] bytes = byteArrayOutputStream.toByteArray();
//        return Base64.encodeToString(bytes, Base64.DEFAULT);
//    }
//
//    private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            result -> {
//                if (result.getResultCode() == RESULT_OK) {
//                    if (result.getData() != null) {
//                        Uri imageUri = result.getData().getData();
//                        try {
//                            InputStream inputStream = getContentResolver().openInputStream(imageUri);
//                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                            binding.imageSignUp.setImageBitmap(bitmap);
//                            encodedImage = encodeImage(bitmap);
//                        } catch (FileNotFoundException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//    );


    private boolean isValidSignUpDetails() {

        if (encodedImage == null) {
            showToast("Select Image Please");
            return false;

        } else if (binding.enternameSU.getText().toString().trim().isEmpty()) {
            showToast("Enter Name");
            return false;

        } else if (binding.enteremailSU.getText().toString().trim().isEmpty()) {
            showToast("Enter Email");
            return false;

        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.enteremailSU.getText().toString()).matches()) {
            showToast("Enter Valid EmailAdress");
            return false;
        } else if (binding.enterpassSU.getText().toString().trim().isEmpty()) {
            showToast("Enter Password");
            return false;

        } else if (binding.reenterSU.getText().toString().trim().isEmpty()) {
            showToast("Re-Enter Password");
            return false;

        } else if (!binding.enterpassSU.getText().toString().equals(binding.reenterSU.getText().toString())) {
            showToast("Password must match the Re-Entered Password");
            return false;
        } else {
            return true;
        }
    }
    private void loading(boolean isLoading){
        if (isLoading) {
            binding.SingUpButton.setVisibility(View.INVISIBLE);
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.INVISIBLE);
            binding.SingUpButton.setVisibility(View.VISIBLE);
        }

    }
}
