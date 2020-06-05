package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener(object : OnCompleteListener<InstanceIdResult>{
            override fun onComplete(task: Task<InstanceIdResult>) {
                if(!task.isSuccessful) {
                    Log.e("ljy", "getInstance fail", task.exception)
                    return
                }
                var token = task.result?.token
                Log.e("ljy", "fcm 토큰 "+ token)
                Toast.makeText(this@MainActivity, token, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
