package com.example.contactapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class AddContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);


        // Inside your MainActivity or wherever you're adding the contact

        Button btnAddContact = findViewById(R.id.btnAddContact);
        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editName = findViewById(R.id.editName);
                EditText editJob = findViewById(R.id.editJob);
                EditText editEmail = findViewById(R.id.editEmail);
                EditText editPhone = findViewById(R.id.editPhone);

                String name = editName.getText().toString();
                String job = editJob.getText().toString();
                String email = editEmail.getText().toString();
                String phone = editPhone.getText().toString();

                // Call the addContact method in MainActivity to add the new contact to the list and update the adapter
                addContact(name, phone, email, job);

                // Clear the input fields

                // Optionally, you can finish the activity if you want to return to the MainActivity after adding the contact
                Intent it = new Intent(AddContactActivity.this, MainActivity.class);
                startActivity(it);
                finish();
            }
        });

    }
    private void addContact(String name, String phone, String email, String job) {
        String url = "http://192.168.56.1/contacts/insertContact.php"; // Remplacez avec l'URL de votre script PHP

        // Créez une StringRequest pour effectuer une requête GET avec les paramètres du nouveau contact
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url +
                "?name=" + name +
                "&email=" + email +
                "&job=" + job +
                "&phone=" + phone,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Traitement de la réponse du serveur (si nécessaire)
                        Toast.makeText(AddContactActivity.this, "Contact ajouté avec succès", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Gestion de l'erreur de réponse
                        Toast.makeText(AddContactActivity.this, "Erreur lors de l'ajout du contact : " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // Ajoutez la requête à la RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
