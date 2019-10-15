package com.bliss.chatmate.Utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.bliss.chatmate.Callbacks.OnImageUploadSuccess;
import com.bliss.chatmate.Callbacks.OnRegistrationCallback;
import com.bliss.chatmate.Models.RegistryParams;
import com.bliss.chatmate.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class FirebaseUtils extends MyUtils {
    public static FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
    public static CollectionReference usersRef = rootRef.collection("users");

    public static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public static FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    public static StorageReference rootStorageRef = firebaseStorage.getReference();

    //Paths
    public static String PROFILE_PICTURES = "profile_pictures";

    private static OnImageUploadSuccess onImageUploadSuccess;
    private static OnRegistrationCallback onRegistrationCallback;

    public FirebaseUtils() {

    }

    public static void uploadImageToFirebaseStorage(final RegistryParams registryParams) {
        String path = registryParams.getPath();
        Uri uri = registryParams.getUri();

        onRegistrationCallback = (OnRegistrationCallback) registryParams.getContext();

        final StorageReference uniqueRef = FirebaseStorage.getInstance().getReference().child(path).child(MyUtils.getUUID());
        uniqueRef.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    uniqueRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri onlineUri) {
                            User user = new User();
                            user.setEmail(registryParams.getEmail());
                            user.setImage(onlineUri.toString());
                            addUserDataToFirestore(registryParams.getContext(), user);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "Error on getting download URL: " + e.getMessage());
                            onRegistrationCallback.onRegistrationFailed(e);
                        }
                    });
                } else {
                    Log.e(TAG, "Error on upload: " + task.getException().getMessage());
                    onRegistrationCallback.onRegistrationFailed(task.getException());
                }
            }
        });
    }

    public static void registerUserInFirebaseAuth(final RegistryParams registryParams) {
        final Context context = registryParams.getContext();
        final String email = registryParams.getEmail();
        final String password = registryParams.getPassword();
        final String path = registryParams.getPath();
        final Uri uri = registryParams.getUri();

        onRegistrationCallback = (OnRegistrationCallback) context;

        firebaseAuth.createUserWithEmailAndPassword(registryParams.getEmail(), registryParams.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    signInUserInFirebaseAuth(registryParams);
                } else {
                    Log.e(TAG, "Error on creating user: " + task.getException().getMessage());
                    onRegistrationCallback.onRegistrationFailed(task.getException());
                }
            }
        });
    }

    public static void signInUserInFirebaseAuth(final RegistryParams registryParams) {
        onRegistrationCallback = (OnRegistrationCallback) registryParams.getContext();

        String email = registryParams.getEmail();
        String password = registryParams.getPassword();

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if (firebaseAuth.getCurrentUser() != null) {
                        uploadImageToFirebaseStorage(registryParams);
                    } else {
                        Log.d(TAG, "No current user");
                    }
                } else {
                    Log.e(TAG, "Error on signing in user: " + task.getException().getMessage());
                    onRegistrationCallback.onRegistrationFailed(task.getException());
                }
            }
        });
    }

    public static void addUserDataToFirestore(Context context, User user) {
        onRegistrationCallback = (OnRegistrationCallback) context;

        String documentID = usersRef.document().getId();
        user.setUser_id(documentID);
        usersRef.document(documentID).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    onRegistrationCallback.onRegistrationSuccess();
                } else {
                    Log.e(TAG, "Error on adding firestore data: " + task.getException().getMessage());
                }
            }
        });
    }
}
