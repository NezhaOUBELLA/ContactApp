package com.example.contactapp;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    List<Contact> contactList = new ArrayList<>(); // Create an empty list to hold contacts
    ContactAdapter contactAdapter = new ContactAdapter(contactList);
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(contactAdapter);

        //Log.d(TAG, "Open fetch Contacts");
        fetchContacts();
        //Log.d(TAG, "Close fetch Contacts");

        btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, AddContactActivity.class);
                startActivity(it);
            }
        });
    }

    private void fetchContacts() {
        String url = "http://192.168.1.6/contacts/getAllContacts.php"; // Replace with your PHP script URL

        // Create a StringRequest to make a GET request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Parse the response JSON using Gson
                        Gson gson = new Gson();
                        Contact[] contacts = gson.fromJson(response, Contact[].class);

                        // Clear the existing contact list
                        contactList.clear();

                        // Add the fetched contacts to the contact list
                        contactList.addAll(Arrays.asList(contacts));

                        // Notify the adapter that the data has changed
                        contactAdapter.notifyDataSetChanged();

                        for (Contact c : contactList) {
                            Log.d(TAG, c.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error response
                        Toast.makeText(MainActivity.this, "Error fetching contacts: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // Add the request to the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void addContact(String name, String phone, String email, String job) {
        String url = "http://192.168.1.6/contacts/insertContact.php"; // Replace with your PHP script URL

        // Create a StringRequest to make a POST request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the response from the server
                        Toast.makeText(MainActivity.this, "Contact added successfully", Toast.LENGTH_SHORT).show();
                        // Refresh the contact list
                        fetchContacts();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error response
                        Toast.makeText(MainActivity.this, "Error adding contact: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Set the POST parameters with the contact details
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("job", job);
                params.put("phone", phone);
                return params;
            }
        };

        // Add the request to the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
