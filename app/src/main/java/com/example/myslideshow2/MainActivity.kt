package com.example.myslideshow2

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.timer

//MediaPlayerクラスのインスタンスを保持する変数を用意。初期化をonCreate内で行いたいので、lateinit修飾子をつけている
private lateinit var player: MediaPlayer

/////////////////////////////////////////
///アダプターを作成しViewPagerに関連付ける///
/////////////////////////////////////////

class MainActivity : AppCompatActivity() {

    class MyAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        private val resources = listOf(
            R.drawable.slide00, R.drawable.slide01,
            R.drawable.slide02, R.drawable.slide03,
            R.drawable.slide04, R.drawable.slide05,
            R.drawable.slide06, R.drawable.slide07,
            R.drawable.slide08, R.drawable.slide09
        )

        override fun getCount(): Int {
            return resources.size
        }

        override fun getItem(position: Int): Fragment {
            return ImageFragment.newInstance(resources[position])
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pager.adapter = MyAdapter(supportFragmentManager)


/////////////////////////////////////////
///タイマー関数を使ったスライドショー実装/////
/////////////////////////////////////////

        //Handlerクラスのインスタンスを取得して変数handlerに代入
        val handler = Handler()
        //kotlinのタイマー関数をつかって5秒ごとに実行するタイマーを用意
        timer(period = 5000) {
            //Handlerのpostメソッドで実行 //5秒たったら次のページを表示する処理を記述
            handler.post {
                //ViewPagerでは、setcurrentItemメソッドにページ数を渡すと、指定してページを表示することができる。
                //ここでは、次のページを表示したいので、getCurrentItemメソッドで現在表示されている
                //ページの番号を取得し、それに1を足して10で割った余りを渡している。10で割った余りを渡しているのは、
                //ページ番号が10までいったら0に戻すため。
                pager.currentItem = (pager.currentItem + 1) % 10
            }
        }
        //createメソッドでMediaPlayerのインスタンスを生成。第2引数にはrawフォルダにあるサウンドのリソースIDを指定
        player = MediaPlayer.create(this,R.raw.getdown)
        //サウンドファイルを繰り返し再生するように設定isLoopingプロパティを使っている
        player.isLooping = true
    }

    override fun onResume() {
        super.onResume()
        player.start()
    }

    override fun onPause() {
        super.onPause()
        player.pause()
    }
}
