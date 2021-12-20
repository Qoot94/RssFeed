package com.example.rssfeed


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var rvMain: RecyclerView
    private lateinit var rvAdapter: RVAdapter

    private lateinit var articles: List<Article>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        articles = listOf()

        rvMain = findViewById(R.id.rvMain)

        parseRSS()
    }

    private fun parseRSS(){
        CoroutineScope(IO).launch {
            val data = withContext(Dispatchers.Default) {
                val parser = XMLparser()
                parser.parse()
            }

            try{
                withContext(Main){
                    articles = data
                    Log.d("MAIN", "Unable to get data$articles")
                    Log.d("MAIN", "Unable to get data$data")

                    rvAdapter = RVAdapter(articles)
                    rvMain.adapter = rvAdapter
                    rvMain.layoutManager = LinearLayoutManager(MainActivity())                }
            }catch(e: java.lang.Exception){
                Log.d("MAIN", "Unable to get data")
            }
        }
    }
}