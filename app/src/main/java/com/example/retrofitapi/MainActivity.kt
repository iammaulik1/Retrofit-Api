package com.example.retrofitapi

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.retrofitapi.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()

        binding.buttonNewMeme.setOnClickListener {
            getData()
        }

    }

    private fun getData() {

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please Wait While Data Is Fetch")
        progressDialog.show()

        Retrofitinstance.apiInterface.getData().enqueue(object : Callback<responseDataClass?> {
            override fun onResponse(
                call: Call<responseDataClass?>,
                response: Response<responseDataClass?>
            ) {

                binding.memeTitle.text = response.body()?.title
                binding.memeAuthor.text = response.body()?.author

                Glide.with(this@MainActivity).load(response.body()?.url).into(binding.memeImage)


                progressDialog.dismiss()

            }

            override fun onFailure(call: Call<responseDataClass?>, t: Throwable) {

                Toast.makeText(this@MainActivity,"${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
            }
        })
    }
}