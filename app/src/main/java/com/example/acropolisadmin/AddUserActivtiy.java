package com.example.acropolisadmin;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.acropolisadmin.Modelpack.AddStudentModel;
import com.example.acropolisadmin.databinding.ActivityAddUserActivtiyBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddUserActivtiy extends AppCompatActivity {
    ActivityAddUserActivtiyBinding binding;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    String selectedDepartment;
    ProgressDialog dialog;

    String[] deparments = {"CS", "IT", "CS-AIML", "CS-DS", "CSIT", "EEE", "CIVIL" , "MECHANICAL" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddUserActivtiyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        rootNode = FirebaseDatabase.getInstance();

        dialog = new ProgressDialog(this);
        dialog.setMessage("Uploading...");

        // SPINNER TOOL FOR DEPARTMENTS SECTION ! ! !
        populateSpinnerDeparmetns();

        binding.createProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.txtStudentName.getText().toString().isEmpty() && !binding.txtStudentEn.getText().toString().isEmpty() && !binding.txtStudentEmail.getText().toString().isEmpty()){
                    dialog.show();
                    createNewProfile();
                }
                else {
                    Toast.makeText(AddUserActivtiy.this, "please fill all details !", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void createNewProfile() {
        reference = rootNode.getReference("Students");
        String studentName = binding.txtStudentName.getText().toString();
        String studentEnroll  = binding.txtStudentEn.getText().toString();
        String studentEmail = binding.txtStudentEmail.getText().toString();
        String department = selectedDepartment;

        AddStudentModel model = new AddStudentModel(studentName , studentEnroll , studentEmail , department);
        dialog.dismiss();
        Toast.makeText(AddUserActivtiy.this, "done👍", Toast.LENGTH_SHORT).show();
        reference.child(studentEnroll).setValue(model);

        binding.txtStudentName.setText("");
        binding.txtStudentEmail.setText("");
        binding.txtStudentEn.setText("");
    }


    private void populateSpinnerDeparmetns() {
        ArrayAdapter departmentArrAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, deparments);
        departmentArrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(departmentArrAdapter);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedDepartment = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(AddUserActivtiy.this , "Empty!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}