package com.example.myslideshow2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_image.*

/////////////////////////////////
///クラスのインスタンスを作る処理///
////////////////////////////////

val IMG_RES_ID = "IMG_RES_ID"

class ImageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image, container, false)
    }

    companion object {
        fun newInstance(imageResourceId: Int): ImageFragment {
            val bundle = Bundle()
            bundle.putInt(IMG_RES_ID, imageResourceId)
            val imageFragment = ImageFragment()
            imageFragment.arguments = bundle
            return imageFragment
        }
    }

//////////////////////////////
///Bundleから値を取り出す処理///
//////////////////////////////
        //アーギュメンツから取り出した画像のリソースIDを保持する変数
        private var imgResId: Int? = null
        //フラグメントが作成された時、再生成された時onCreateが呼ばれる。
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            //letはkotlinのスコープ関数。アーギュメンツの中身はnullかもしれないので、安全呼び出し演算子?.とスコープ関数letを使って
            //nullではないことを確認している。ここで取り出したBundleオブジェクトは、let関数内で暗黙の変数itでアクセスできる。
            arguments?.let{
                //アーギュメンツに保存されていた画像のリソースIDをimgResIdに保存している。
                imgResId = it.getInt(IMG_RES_ID)
            }
        }

////////////////////////////////
///ImageViewに画像を設定する処理//
////////////////////////////////

    //フラグメントのライフサイクルメソッドで、アクティビティのonCreateが完了した後に呼び出される。
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //imgResIdに保持しておいたリソースIDを使って、フラグメント内のImageViewに画像を設定している。
        //imgResIdはnullの可能性があるので、?.とletを使ってnullではない場合のみ画像を表示するようにしている。
        imgResId?.let{
            imageView.setImageResource(it)
        }
    }
}
