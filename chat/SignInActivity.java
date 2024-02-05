package com.example.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.chat.databinding.ActivitySignInBinding;
import com.example.chat.utilities.Constants;
import com.example.chat.utilities.PreferenceManager;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;


public class SignInActivity extends AppCompatActivity {

    private ActivitySignInBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
    }

    private void setListeners(){
        binding.CreateAccbutton.setOnClickListener(v->
                startActivity(new Intent(getApplicationContext(), SignUp.class)));
        binding.SingUpButton.setOnClickListener(v -> {
            if (isValidSignInDetails()){
                signIn();
            }
        });
    }
    private void signIn(){
        Loading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS)
                .whereEqualTo(Constants.KEY_EMAIL, binding.EnterEmail.getText().toString())
                .whereEqualTo(Constants.KEY_PASSWORD, binding.enterpass.getText().toString())
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful() && task.getResult() != null
                            && task.getResult().getDocuments().size() > 0){
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                        preferenceManager.putBoolean(Constants.KEY_SIGNED_IN, true);
                        preferenceManager.putString(Constants.KEY_USER_ID, documentSnapshot.getId());
                        preferenceManager.putString(Constants.KEY_NAME, documentSnapshot.getString(Constants.KEY_NAME));
                        preferenceManager.putString(Constants.KEY_IMAGE, documentSnapshot.getString(Constants.KEY_IMAGE));
                        Intent intentTeach = new Intent(getApplicationContext(), TeachersView.class);
                       
                        startActivity(intentTeach);
                        finish();


                    } else {
                        Loading(false);
                        showToast("Unable to Sign In");
                    }
                });
    }

    private void Loading(Boolean isLoading){
        if (isLoading) {
            binding.SingUpButton.setVisibility(View.INVISIBLE);
            binding.signinloading.setVisibility(View.VISIBLE);
        } else {
            binding.signinloading.setVisibility(View.INVISIBLE);
            binding.SingUpButton.setVisibility(View.VISIBLE);
        }
    }

    private void addDataToFirestore(){
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        HashMap<String, Object> data = new HashMap<>();
        data.put("first_name", "Jonathan");
        data.put("Last_name", "Dionisio");
        database.collection("users")
                .add(data)
                .addOnSuccessListener(documentReference ->{
                    Toast.makeText(getApplicationContext(), "Data Inserted", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(exception ->{
                    Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();

                });

    }

    private void showToast(String message ){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

    }
    private Boolean isValidSignInDetails(){
        if(binding.EnterEmail.getText().toString().trim().isEmpty()){
            showToast("Enter Email");
            return  false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(binding.EnterEmail.getText().toString()).matches()){
            showToast("Enter Valid Email");
            return false;
        }
        else if (binding.enterpass.getText().toString().trim().isEmpty()){
            showToast("Enter Password");
            return  false;

        }else {
            return true;
        }
    }


}