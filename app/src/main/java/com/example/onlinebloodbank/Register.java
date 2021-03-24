package com.example.onlinebloodbank;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import android.app.Activity;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class Register extends AppCompatActivity {

    public static final String TAG = "TAG";
    Spinner spbgreg, spdivreg, spdisreg, spunireg, spcpreg;
    Button vbtnsubreg, vbtnardreg,  vbtnnidpicreg;
    EditText vedtnamre, vedtnamunamreg, vedtmailreg, vedtphnreg, vedtpassreg;
    FirebaseAuth fauth;
    FirebaseFirestore fstore;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage storage;
    StorageReference storageReference;
    String userid;
    Uri filepath;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        vedtnamre = findViewById(R.id.edtnamre);
        vedtnamunamreg = findViewById(R.id.edtnamunamreg);
        vedtmailreg = findViewById(R.id.edtmailreg);
        vedtphnreg = findViewById(R.id.edtphnreg);
        vedtpassreg = findViewById(R.id.edtpassreg);
        vbtnsubreg = findViewById(R.id.btnsubreg);
        vbtnardreg = findViewById(R.id.btnardreg);

        vbtnnidpicreg = findViewById(R.id.btnnidpicreg);
        fauth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        fstore = FirebaseFirestore.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        if (fauth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();

        }




        vbtnnidpicreg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadnidimage();

            }
        });


        vbtnsubreg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                submitimage();
                String email = vedtmailreg.getText().toString().trim();
                String username = vedtnamunamreg.getText().toString().trim();
                String fullname = vedtnamre.getText().toString().trim();
                String phone = vedtphnreg.getText().toString().trim();
                String password = vedtpassreg.getText().toString().trim();
                String bloodgrp = spbgreg.getSelectedItem().toString().trim();
                String division = spdivreg.getSelectedItem().toString().trim();
                String district = spdisreg.getSelectedItem().toString().trim();
                String union = spunireg.getSelectedItem().toString().trim();
                String ctycorp = spcpreg.getSelectedItem().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    vedtmailreg.setError("Email is Required.");
                    return;
                }

                if (TextUtils.isEmpty(username)) {
                    vedtnamunamreg.setError("Username is Required.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    vedtpassreg.setError("Password is Required.");
                    return;
                }

                if (password.length() < 6) {
                    vedtpassreg.setError("Password must be more then six characters.");
                    return;
                }
                if (phone.length() < 11) {
                    vedtpassreg.setError("Password must be more then Eleven Number.");
                    return;
                }

                fauth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this, "User Created!", Toast.LENGTH_SHORT).show();
                            userid = fauth.getCurrentUser().getUid();
                            DocumentReference documentReference = fstore.collection("Users").document(userid);
                            Map<String, Object> user = new HashMap<>();
                            user.put("Full name", fullname);
                            user.put("User name", username);
                            user.put("Emai", email);
                            user.put("Phone No.", phone);
                            user.put("Blood Group", bloodgrp);
                            user.put("Division", division);
                            user.put("District", district);
                            user.put("Union", union);
                            user.put("City Corporation", ctycorp);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: User Profile is Created in firestore for" + userid);
                                }
                            });
                            users informations = new users(

                                    fullname,
                                    username,
                                    email,
                                    phone,
                                    bloodgrp,
                                    password,
                                    division,
                                    district,
                                    union,
                                    ctycorp


                            );

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(informations).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    Toast.makeText(Register.this, "Registration Complete!", Toast.LENGTH_SHORT).show();



                                }
                            });

                            startActivity(new Intent(getApplicationContext(), Login.class));

                        } else {
                            Toast.makeText(Register.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        spbgreg = (Spinner) findViewById(R.id.spinnerbgreg);

        List<String> bglist = new ArrayList<String>();
        bglist.add("Select Blood Group");
        bglist.add("O+ ( O  Positive )");
        bglist.add("O- ( O  Negative )");
        bglist.add("A+ ( A  Positive )");
        bglist.add("A- ( A  Negative )");
        bglist.add("B+ ( B  Positive )");
        bglist.add("B- ( B  Negative )");
        bglist.add("AB+( AB Positive )");
        bglist.add("AB-( AB Negative )");
        ArrayAdapter<String> arrayAdapterbg = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bglist);
        arrayAdapterbg.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spbgreg.setAdapter(arrayAdapterbg);
        spbgreg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spbgreg.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spdivreg = (Spinner) findViewById(R.id.spinnerdivreg);

        List<String> divilist = new ArrayList<String>();
        divilist.add("Select Division");
        divilist.add("Dhaka");
        divilist.add("Chittagong");
        divilist.add("Barisal");
        divilist.add("Khulna");
        divilist.add("Mymenshingh");
        divilist.add("Rajshahi");
        divilist.add("Rangpur");
        divilist.add("Sylhet");
        ArrayAdapter<String> arrayAdapterdiv = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, divilist);
        arrayAdapterdiv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spdivreg.setAdapter(arrayAdapterdiv);
        spdivreg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spdivreg.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spdisreg = (Spinner) findViewById(R.id.spinnerdisreg);

        List<String> dislist = new ArrayList<String>();
        dislist.add("Select District");
        dislist.add("Dhaka District");
        dislist.add("Faridpur District");
        dislist.add("Gazipur District");
        dislist.add("Gopalganj District");
        dislist.add("Kishoreganj District");
        dislist.add("Madaripur District");
        dislist.add("Manikganj District");
        dislist.add("Munshiganj District");
        dislist.add("Narayanganj District");
        dislist.add("Narsingdi District");
        dislist.add("Rajbari District");
        dislist.add("Shariatpur District");
        dislist.add("Tangail District");
        dislist.add("Bandarban District");
        dislist.add("Brahmanbaria District");
        dislist.add("Chandpur District");
        dislist.add("Chittagong District");
        dislist.add("Comilla District");
        dislist.add("Cox's Bazar District");
        dislist.add("Feni District");
        dislist.add("Khagrachhari District");
        dislist.add("Lakshmipur District");
        dislist.add("Noakhali District");
        dislist.add("Rangamati District");
        dislist.add("Barguna District");
        dislist.add("Barisal District");
        dislist.add("Bhola District");
        dislist.add("Jhalokati District");
        dislist.add("Patuakhali District");
        dislist.add("Pirojpur District");
        dislist.add("Bagerhat District");
        dislist.add("Chuadanga District");
        dislist.add("Jessore District");
        dislist.add("Jhenaidah District");
        dislist.add("Khulna District");
        dislist.add("Kushtia District");
        dislist.add("Magura District");
        dislist.add("Meherpur District");
        dislist.add("Narail District");
        dislist.add("Satkhira District");
        dislist.add("Jamalpur District");
        dislist.add("Mymensingh District");
        dislist.add("Netrokona District");
        dislist.add("Sherpur District");
        dislist.add("Bogra District");
        dislist.add("Joypurhat District");
        dislist.add("Naogaon District");
        dislist.add("Natore District");
        dislist.add("Chapainawabganj District");
        dislist.add("Pabna District");
        dislist.add("Rajshahi District");
        dislist.add("Sirajganj District");
        dislist.add("Dinajpur District");
        dislist.add("Gaibandha District");
        dislist.add("Kurigram District");
        dislist.add("Lalmonirhat District");
        dislist.add("Nilphamari District");
        dislist.add("Panchagarh District");
        dislist.add("Rangpur District");
        dislist.add("Thakurgaon District");
        dislist.add("Habiganj District");
        dislist.add("Moulvibazar District");
        dislist.add("Sunamganj District");
        dislist.add("Sylhet District");
        ArrayAdapter<String> arrayAdapterdis = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dislist);
        arrayAdapterdis.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spdisreg.setAdapter(arrayAdapterdis);
        spdisreg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position1, long id) {
                spdisreg.setSelection(position1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spunireg = (Spinner) findViewById(R.id.spinnerunireg);

        List<String> unilist = new ArrayList<String>();
        unilist.add("Select Union");
        unilist.add("Gajipur Cantonment");
        unilist.add("Gajipur Shodor");
        unilist.add("Kalikour");
        unilist.add("Kaliganj");
        unilist.add("Kapashiya");
        unilist.add("Sripur");
        unilist.add("Badda");
        unilist.add("Beraid");
        unilist.add("Vatara");
        unilist.add("Dokkhin Khan");
        unilist.add("Kaptai");
        unilist.add("Baghaichori");
        unilist.add("Borkol");
        unilist.add("Bilaichori");
        unilist.add("Jurachori");
        unilist.add("Kaukhali");
        unilist.add("Longodu");
        unilist.add("Naniyarchor");
        unilist.add("Rajostholi");
        unilist.add("Rangamati Shodor");
        unilist.add("Dumni");
        unilist.add("Horirampur");
        unilist.add("Sultanganj");
        unilist.add("Uttorkhan");
        unilist.add("Dokkhingau");
        unilist.add("Doniya");
        unilist.add("Demra");
        unilist.add("Manda");
        unilist.add("Matuyail");
        unilist.add("Nasirabad");
        unilist.add("Saruliya");
        unilist.add("Shampur");
        unilist.add("Anoyara");
        unilist.add("Basgkhali");
        unilist.add("BoalKhali");
        unilist.add("Chondnaish");
        unilist.add("Cantonment");
        unilist.add("FoticChori");
        unilist.add("Hathajari");
        unilist.add("Lohagara");
        unilist.add("Mirshirai");
        unilist.add("Ptiya");
        unilist.add("Rangyuniya");
        unilist.add("Raujan");
        unilist.add("Shondip");
        unilist.add("Shatkaniya");
        unilist.add("Shitakundo");
        unilist.add("Amtoli");
        unilist.add("Bamna");
        unilist.add("Borguna Shodir");
        unilist.add("Betagi");
        unilist.add("Pathorghata");
        unilist.add("Taltoli");
        unilist.add("AgoilJhora");
        unilist.add("Babuganj");
        unilist.add("Bakerganj");
        unilist.add("Banaripara");
        unilist.add("Barishal Shodor");
        unilist.add("Gouronodi");
        unilist.add("Hijla");
        unilist.add("Mehediganj");
        unilist.add("Muladi");
        unilist.add("Ujirpur");
        unilist.add("BorhanUddin");
        unilist.add("Chorfashon");
        unilist.add("Dulot Khan");
        unilist.add("Lalmohon");
        unilist.add("Monpura");
        unilist.add("Tojumuddin");
        unilist.add("Jholokathi");
        unilist.add("Kathaliya");
        unilist.add("Bokshiganj");
        unilist.add("Dewanganj");
        unilist.add("IslamPur");
        unilist.add("Jamalpur Shodor");
        unilist.add("Madargamj");
        unilist.add("Melandoho");
        unilist.add("Sorishabari");
        unilist.add("Balagonj");
        unilist.add("Biyanibajar");
        unilist.add("Bisshonath");
        unilist.add("Companihong");
        unilist.add("Dokkhin Surma");
        unilist.add("Fenchuganj");
        unilist.add("Golapganj");
        unilist.add("Goyain Ghat");
        unilist.add("Jointapur");
        unilist.add("jalalabad Shenanibash");
        unilist.add("Kanaighat");
        unilist.add("Sylhet Shodor");
        unilist.add("Jokigonj");
        unilist.add("Hbiganj Shodor");
        unilist.add("N/A");
        ArrayAdapter<String> arrayAdapteruni = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, unilist);
        arrayAdapteruni.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spunireg.setAdapter(arrayAdapteruni);
        spunireg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spunireg.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spcpreg = (Spinner) findViewById(R.id.spinnercpreg);

        List<String> cclist = new ArrayList<String>();
        cclist.add("Select City Corporation");
        cclist.add("Word No - 1");
        cclist.add("Word No - 2");
        cclist.add("Word No - 3");
        cclist.add("Word No - 4");
        cclist.add("Word No - 5");
        cclist.add("Word No - 6");
        cclist.add("Word No - 7");
        cclist.add("Word No - 8");
        cclist.add("Word No - 9");
        cclist.add("Word No - 10");
        cclist.add("Word No - 11");
        cclist.add("Word No - 12");
        cclist.add("Word No - 13");
        cclist.add("Word No - 14");
        cclist.add("Word No - 15");
        cclist.add("Word No - 16");
        cclist.add("Word No - 17");
        cclist.add("Word No - 18");
        cclist.add("Word No - 19");
        cclist.add("Word No - 20");
        cclist.add("Word No - 21");
        cclist.add("Word No - 22");
        cclist.add("Word No - 23");
        cclist.add("Word No - 24");
        cclist.add("Word No - 25");
        cclist.add("Word No - 26");
        cclist.add("Word No - 27");
        cclist.add("Word No - 28");
        cclist.add("Word No - 29");
        cclist.add("Word No - 30");
        cclist.add("Word No - 31");
        cclist.add("Word No - 32");
        cclist.add("Word No - 33");
        cclist.add("Word No - 34");
        cclist.add("Word No - 35");
        cclist.add("Word No - 36");
        cclist.add("Word No - 37");
        cclist.add("Word No - 38");
        cclist.add("Word No - 39");
        cclist.add("Word No - 40");
        cclist.add("Word No - 41");
        cclist.add("Word No - 42");
        cclist.add("N/A");

        ArrayAdapter<String> arrayAdaptercc = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cclist);
        arrayAdaptercc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spcpreg.setAdapter(arrayAdaptercc);
        spcpreg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spcpreg.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        vbtnardreg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });

    }

    private void submitimage() {

        if (filepath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference reference = storageReference.child("images/" + UUID.randomUUID().toString());
            reference.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    progressDialog.dismiss();
                    Toast.makeText(Register.this, "Image Uploaded!", Toast.LENGTH_SHORT).show();
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Images");
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("imageurl", String.valueOf(uri));
                            databaseReference.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Register.this, "Image added to Database!", Toast.LENGTH_SHORT).show();
                                }
                            });


                        }
                    });
                }
            })

                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progres = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded" + (int) progres + "%");
                        }
                    });
        }

    }

    private void uploadnidimage() {


        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filepath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    public void btnadmin(View view) {
        Intent intent = new Intent(Register.this, LoginForAdmin.class);
        startActivity(intent);
    }
}