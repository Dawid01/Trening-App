package com.szczepaniak.dawid.treningapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private View signInGoogle;
    private GoogleSignInOptions gso;
    private FirebaseAuth mAuth;

    private final int RC_SIGN_IN = 1;
    private GoogleApiClient googleApiClient;
    private FirebaseAuth.AuthStateListener authStateListener;
    boolean registred = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser() != null){

                    Intent mainAvivity = new Intent(LoginActivity.this, MainActivity.class);
                    LoginActivity.this.startActivity(mainAvivity);
                    LoginActivity.this.overridePendingTransition(R.anim.left_in, R.anim.left_out);
                    LoginActivity.this.finish();
                }
            }
        };

        googleApiClient =  new GoogleApiClient.Builder(getApplicationContext()).enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();


        signInGoogle = findViewById(R.id.SignInGoogle);

        signInGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signIn();

            }
        });
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);

                firebaseAuthWithGoogle(account);


            } catch (ApiException e) {
                Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
      //  String currentID = mAuth.getCurrentUser().getUid();
//        db.collection("Users").document(currentID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if(task.isSuccessful()){
//                    registred = true;
//                }else {
//                    registred = false;
//                }
//            }
//        });

        ProgressDialog pd = new ProgressDialog(LoginActivity.this);
        pd.setMessage("Login....");
        pd.show();

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull final Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            signInGoogle.setVisibility(View.INVISIBLE);

                            final FirebaseUser user = mAuth.getCurrentUser();
                            final FirebaseFirestore db;
                            db = FirebaseFirestore.getInstance();

                            DocumentReference documentReference = db.collection("Users").document(user.getUid());

                            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                    try {
//                                        registred = documentSnapshot.getBoolean("Registred");
//                                    }catch (Exception e){
//                                        registred = false;
//                                    }

                                    if(registred = documentSnapshot.getBoolean("Registred") == null) {

                                        Map<String, Object> musclesStatus = new HashMap<>();
                                        musclesStatus.put("Registred", true);
                                        musclesStatus.put("Abdonem", true);
                                        musclesStatus.put("Arms", true);
                                        musclesStatus.put("Back", true);
                                        musclesStatus.put("Chest", true);
                                        musclesStatus.put("Legs", true);
                                        musclesStatus.put("Shoulders", true);

                                        db.collection("Users").document(user.getUid()).set(musclesStatus, SetOptions.merge())
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(LoginActivity.this, "Register sucess", Toast.LENGTH_SHORT).show();
                                                        Intent mainAvivity = new Intent(LoginActivity.this, MainActivity.class);
                                                        LoginActivity.this.startActivity(mainAvivity);
                                                        LoginActivity.this.overridePendingTransition(R.anim.left_in, R.anim.left_out);
                                                        LoginActivity.this.finish();
                                                    }
                                                });
                                    }else {

                                        Intent mainAvivity = new Intent(LoginActivity.this, MainActivity.class);
                                        LoginActivity.this.startActivity(mainAvivity);
                                        LoginActivity.this.overridePendingTransition(R.anim.left_in, R.anim.left_out);
                                        LoginActivity.this.finish();
                                    }
                                }
                            });

                            //updateUI(user);
                        } else {
                            //updateUI(null);
                        }

                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
        //FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }
}
